package com.iflytek.vcp.voice.engine.tts.domain;

/**
 * @author Created by jianwu6 on 2020/3/17 11:05
 */
public class TesRequest {

    /**
     * 请求的类型, 如发音人：voiceInfo
     */
    private String type;

    /**
     * 请求的参数, 保留字段
     */
    private String sParam;

    private byte[] bParam;

    public TesRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getsParam() {
        return sParam;
    }

    public void setsParam(String sParam) {
        this.sParam = sParam;
    }

    public byte[] getbParam() {
        return bParam;
    }

    public void setbParam(byte[] bParam) {
        this.bParam = bParam;
    }
}
