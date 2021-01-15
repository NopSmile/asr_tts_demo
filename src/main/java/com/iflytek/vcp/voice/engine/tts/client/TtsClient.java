package com.iflytek.vcp.voice.engine.tts.client;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.iflytek.vcp.voice.engine.tts.core.TtsGrpc;
import com.iflytek.vcp.voice.engine.tts.core.TtsOuterClass;
import com.iflytek.vcp.voice.engine.tts.ttsException.TtsException;
import io.grpc.Context;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author irun
 */
public class TtsClient implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TtsClient.class);

    /**
     * tts server 端 ip:port, 例如：127.0.0.1:1234
     */
    private String url;

    /**
     * 请求 tts 的会话参数
     */
    private TtsSessionParam ttsSessionParam;

    /**
     * client 回调
     */
    private TtsSessionResponse ttsSessionResponse;

    /**
     * grpc client 端向 server 请求类
     */
    private TtsGrpc.TtsStub stub;

    /**
     * channel
     */
    private ManagedChannel channel;

    /**
     * grpc client 端请求流
     */
    private StreamObserver<TtsOuterClass.TtsRequest> requestStreamObserver;

    /**
     * grpc 结果返回流
     */
    private StreamObserver<TtsOuterClass.TtsResult> resultStreamObserver;

    /**
     * TtsClient 带参构造函数
     *
     * @param url             服务端 ip:port 例如:192.168.0.1:1234
     * @param ttsSessionParam 连接服务端参数
     */
    public TtsClient(String url, TtsSessionParam ttsSessionParam) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("url: {}, ttsSessionParam: {}", url, JSON.toJSONString(ttsSessionParam));
        }
        if (StringUtils.isBlank(url)) {
            LOGGER.error("url is blank, url format : ip1:port1");
            throw new TtsException("url is blank, url format : ip1:port1");
        }
        this.url = url;
        this.ttsSessionParam = ttsSessionParam;
    }


    /**
     * 连接引擎方法
     *
     * @param ttsSessionResponse 引擎返回结果回调
     * @return 是否连接成功
     */
    public boolean connect(TtsSessionResponse ttsSessionResponse) {
        this.ttsSessionResponse = ttsSessionResponse;

        this.resultStreamObserver = new StreamObserver<TtsOuterClass.TtsResult>() {
            @Override
            public void onNext(TtsOuterClass.TtsResult ttsResult) {
                if (ttsResult != null && ttsSessionResponse != null) {
                    try {
                        TtsSessionResult ttsSessionResult = new TtsSessionResult();
                        ttsSessionResult.setData(ttsResult.getData().toByteArray());
                        ttsSessionResult.setDataInfo(ttsResult.getDataInfoMap());
                        ttsSessionResult.setEndFlag(ttsResult.getEndFlag());
                        ttsSessionResult.setErrCode(ttsResult.getErrCode());
                        ttsSessionResult.setErrStr(ttsResult.getErrStr());
                        ttsSessionResponse.onCallback(ttsSessionResult);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("ttsSessionResult: {}, sid={}", JSON.toJSONString(ttsSessionResult),
                                    ttsSessionParam.getSid());
                        }
                    } catch (Exception e) {
                        /* 回调出现异常后,主动断开 gRPC 连接, 并把异常用 ttsSessionResponse.onError 传给回调者 */
                        LOGGER.error("call ttsSessionResponse.onCallback exception", e);

                        LOGGER.info("since call onCallBack exception, interrupt connection between gRPC client and " +
                                "server.");
                        LOGGER.info("call onError");
                        onError(e);

                        try {
                            LOGGER.info("call end()");
                            end();
                            LOGGER.info("call close()");
                            close();
                        } catch (Exception ex) {
                            LOGGER.error(String.format("sid=%s", ttsSessionParam.getSid()), ex);
                        }
                    }
                } else {
                    LOGGER.error(String.format("ttsResult or ttsSessionResponse is null, error. sid=%s",
                            ttsSessionParam.getSid()));
                }

            }

            @Override
            public void onError(Throwable throwable) {
                if (ttsSessionResponse != null) {
                    try {
                        LOGGER.debug(String.format("call ttsSessionResponse.onError(throwable). sid=%s",
                                ttsSessionParam.getSid()));
                        ttsSessionResponse.onError(throwable);
                    } catch (Exception e) {
                        LOGGER.error(String.format("call ttsSessionResponse.onError exception. sid=%s",
                                ttsSessionParam.getSid()), e);
                    }
                } else {
                    LOGGER.error(String.format("ttsSessionResponse is null, error. sid=%s", ttsSessionParam.getSid()));
                }
            }

            @Override
            public void onCompleted() {
                if (ttsSessionResponse != null) {
                    try {
                        ttsSessionResponse.onCompleted();
                    } catch (Exception e) {
                        LOGGER.error(String.format("call ttsSessionResponse.onCompleted exception. sid=%s",
                                ttsSessionParam.getSid()), e);
                    }

                } else {
                    LOGGER.error(String.format("ttsSessionResponse is null, error. sid=%s", ttsSessionParam.getSid()));
                }
            }
        };

        boolean ret = false;
        try {
            channel = ManagedChannelBuilder.forTarget(url).usePlaintext().build();
            stub = TtsGrpc.newStub(channel);

            LOGGER.debug("connect was called, sid={}", ttsSessionParam.getSid());
            // 第一次连接 server 端,传递会话参数
            postConnect();
            ret = true;
        } catch (Exception e) {
            LOGGER.error(String.format("connect error. sid=%s", ttsSessionParam.getSid()), e);
        }
        return ret;
    }

    /**
     * connect 连接 server 私有方法
     *
     * @throws TtsException 连接异常
     */
    private void postConnect() throws TtsException {
        /* check required fields */
        ttsSessionParamRequiredFieldsCheck(ttsSessionParam);

        Context.current().fork().run(() -> {
            requestStreamObserver = this.stub.createRec(resultStreamObserver);
            LOGGER.debug("created Rec/requestStreamObserver.");
        });

        try {
            Map<String, String> sessionParam = new HashMap<>();
            putNotNullFields(sessionParam, "sid", ttsSessionParam.getSid());
            putNotNullFields(sessionParam, "voice_name", ttsSessionParam.getvoice_name());
            putNotNullFields(sessionParam, "vid", ttsSessionParam.getVid());
            putNotNullFields(sessionParam, "sample_rate", ttsSessionParam.getSample_rate());
            putNotNullFields(sessionParam, "frame_size", ttsSessionParam.getFrame_size());
            putNotNullFields(sessionParam, "audio_coding", ttsSessionParam.getAudio_coding());
            putNotNullFields(sessionParam, "speed", ttsSessionParam.getSpeed());
            putNotNullFields(sessionParam, "volume", ttsSessionParam.getVolume());
            putNotNullFields(sessionParam, "pitch", ttsSessionParam.getPitch());

            putNotNullFields(sessionParam, "read_all_marks", ttsSessionParam.getRead_all_marks());
            putNotNullFields(sessionParam, "read_number", ttsSessionParam.getRead_number());
            putNotNullFields(sessionParam, "text_type", ttsSessionParam.getText_type());
            putNotNullFields(sessionParam, "read_english", ttsSessionParam.getRead_english());
            putNotNullFields(sessionParam, "stall_style", ttsSessionParam.getStall_style());
            putNotNullFields(sessionParam, "irf_path", ttsSessionParam.getIrf_path());

            for (Map.Entry<String, String> item : ttsSessionParam.getExtend().entrySet()) {
                if (null != item.getValue()) {
                    sessionParam.put(item.getKey(), item.getValue());
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("postConnect sessionParam: {}, sid={}", JSON.toJSONString(sessionParam),
                        ttsSessionParam.getSid());
            }

            TtsOuterClass.TtsRequest request = TtsOuterClass.TtsRequest.newBuilder()
                    .putAllSessionParam(sessionParam).setEndFlag(false).build();
            Context.current().fork().run(() -> {
                requestStreamObserver.onNext(request);
            });

        } catch (Exception e) {
            LOGGER.error(String.format("postConnect error, sid=%s", ttsSessionParam.getSid()), e);
            Context.current().fork().run(() -> {
                requestStreamObserver.onError(e);
            });
            throw new TtsException(String.format("requestStreamObserver.onNext() error. sid=%s",
                    ttsSessionParam.getSid()));
        }
    }

    /**
     * 向引擎发送合成文本
     *
     * @param text 待合成文本
     */
    public synchronized void post(String text) {
        LOGGER.debug("post text: {}", text);
        if (requestStreamObserver == null) {
            throw new TtsException(String.format("requestStreamObserver is null, call connect() method first. sid=%s",
                    ttsSessionParam.getSid()));
        }

        try {
            TtsOuterClass.TtsRequest request = TtsOuterClass.TtsRequest.newBuilder().setEndFlag(false).
                    setText(text).build();
            Context.current().fork().run(() -> {
                requestStreamObserver.onNext(request);
            });

        } catch (Exception e) {
            LOGGER.error(String.format("post error. sid=%s", ttsSessionParam.getSid()), e);
            LOGGER.error(String.format("post error. sid=%s", ttsSessionParam.getSid()), e.getMessage());
            Context.current().fork().run(() -> {
                requestStreamObserver.onError(e);
            });
        }
    }

    /**
     * 向引擎发送文本结束,断开和引擎会话
     */
    public void end() {
        if (requestStreamObserver == null) {
            throw new TtsException(String.format("requestStreamObserver is null, call connect() method first. sid=%s",
                    ttsSessionParam.getSid()));
        }

        try {
            TtsOuterClass.TtsRequest request = TtsOuterClass.TtsRequest.newBuilder().setEndFlag(true).build();
            Context.current().fork().run(() -> {
                LOGGER.info("end was called, send endFlag=true. sid={}", ttsSessionParam.getSid());
                requestStreamObserver.onNext(request);
            });
        } catch (Exception e) {
            LOGGER.error(String.format("end error. sid=%s", ttsSessionParam.getSid()), e);
            Context.current().fork().run(() -> {
                requestStreamObserver.onError(e);
            });

        }
        Context.current().fork().run(() -> {
            requestStreamObserver.onCompleted();
            LOGGER.debug("requestStreamObserver.onCompleted was called. sid={}", ttsSessionParam.getSid());
        });
        requestStreamObserver = null;
        LOGGER.debug("end set requestStreamObserver=null. sid={}", ttsSessionParam.getSid());
    }

    public String getUrl() {
        return url;
    }

    public TtsSessionParam getTtsSessionParam() {
        return ttsSessionParam;
    }

    private static void ttsSessionParamRequiredFieldsCheck(TtsSessionParam ttsSessionParam) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ttsSessionParamRequiredFieldsCheck ttsSessionParam: {}", JSON.toJSONString(ttsSessionParam));
        }
        Preconditions.checkNotNull(ttsSessionParam, "ttsSessionParam is null");
        Preconditions.checkArgument(null != ttsSessionParam.getVid()
                        || null != ttsSessionParam.getvoice_name(),
                "vid and native_voice_name can't be null at same time");
    }


    public static void putNotNullFields(Map<String, String> sessionParam, String key, Object value) {
        Preconditions.checkNotNull(sessionParam, "sessionParam is null");
        Preconditions.checkNotNull(key, "key is null");

        if (null != value) {
            sessionParam.put(key, String.valueOf(value));
        }
    }

    public TtsSessionResponse getTtsSessionResponse() {
        return ttsSessionResponse;
    }

    @Override
    public void close() throws Exception {
        try {
            if (resultStreamObserver != null) {
                resultStreamObserver.onCompleted();
                resultStreamObserver = null;
            }
        } catch (Exception e) {
            LOGGER.debug("resultStreamObserver.onCompleted() exception:{}, sid={}", e.getMessage(),
                    ttsSessionParam.getSid());
        }

        try {
            if (requestStreamObserver != null) {
                requestStreamObserver.onCompleted();
                requestStreamObserver = null;
            }
        } catch (Exception e) {
            LOGGER.debug("requestStreamObserver.onCompleted() exception:{}, sid={}", e.getMessage(),
                    ttsSessionParam.getSid());
        }

        try {
            if (channel != null) {
                channel.shutdown();
                channel = null;
            }
        } catch (Exception e) {
            LOGGER.error("channel.shutdown() exception:{}, sid={}", e, ttsSessionParam.getSid());
            throw e;
        }
    }
}
