package com.iflytek.tts.sdk.client;

public interface TtsSessionResponse {

    /**
     * server 返回结果回调方法
     *
     * @param ttsSessionResult
     */
    void onCallback(TtsSessionResult ttsSessionResult);


    /**
     * server 返回出错回调方法
     *
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * server 完成回调方法
     */
    void onCompleted();
}
