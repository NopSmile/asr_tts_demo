package com.iflytek.vcp.voice.engine.tts.client;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.iflytek.vcp.voice.engine.tts.core.Tes;
import com.iflytek.vcp.voice.engine.tts.core.TESGrpc;
import com.iflytek.vcp.voice.engine.tts.domain.TesRequest;
import com.iflytek.vcp.voice.engine.tts.domain.TesResult;
import com.iflytek.vcp.voice.engine.tts.ttsException.TtsException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Created by jianwu6 on 2020/3/17 10:51
 */
public class TesClient implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesClient.class);

    /**
     * 服务地址 如: 127.0.0.1:8080
     */
    private String url;

    /**
     * channel
     */
    private ManagedChannel channel;

    private TESGrpc.TESBlockingStub tesBlockingStub;


    /**
     * 创建 获取发音人 客户端
     *
     * @param url 加载资源服务的 url 如: 127.0.0.1:8080
     * @throws TtsException 异常
     */
    public TesClient(String url) throws TtsException {
        LOGGER.debug("new TesClient url: {}", url);

        if (StringUtils.isBlank(url)) {
            throw new TtsException("invalid url, url is blank!");
        }

        this.url = url;
        this.channel = ManagedChannelBuilder.forTarget(this.url).usePlaintext().build();
        LOGGER.debug("created channel ...");
        tesBlockingStub = TESGrpc.newBlockingStub(channel);
        LOGGER.debug("created tesBlockingStub");
    }

    /**
     * 扩展接口，获取发音人
     *
     * @param tesRequest 参数
     * @return TesResult 结果
     * @throws TtsException 异常
     */
    public TesResult notify(TesRequest tesRequest) throws TtsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("notify params: {}", JSON.toJSONString(tesRequest));
        }

        tesRequestChecker(tesRequest);

        Tes.TESRequest request = Tes.TESRequest.newBuilder()
                .setType(tesRequest.getType())
                .build();

        try {
            Tes.TESResult result = tesBlockingStub.onNotify(request);
            TesResult tesResult = new TesResult();
            tesResult.setErrCode(result.getErrCode());
            tesResult.setErrStr(result.getErrStr());
            tesResult.setResult(result.getSResult());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("return value TesResult: {}", JSON.toJSONString(tesResult));
            }
            return tesResult;
        } catch (StatusRuntimeException e) {
            LOGGER.error("notify gRPC StatusRuntimeException", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("notify exception", e);
            throw e;
        }
    }

    private void tesRequestChecker(TesRequest tesRequest) {
        Preconditions.checkNotNull(tesRequest, "tesRequest is null");
        Preconditions.checkArgument(StringUtils.isNoneBlank(tesRequest.getType()), "tesRequest type is empty");
    }

    public String getUrl() {
        return url;
    }


    @Override
    public void close() {
        try {
            if (channel != null) {
                channel.shutdown();
                channel = null;
            }
        } catch (Exception e) {
            LOGGER.error("shutdown channel exception", e);
            throw e;
        }
    }
}
