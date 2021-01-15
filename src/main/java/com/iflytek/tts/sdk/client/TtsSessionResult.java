package com.iflytek.tts.sdk.client;

import java.util.Map;

public class TtsSessionResult {

    /**
     * 错误描述
     */
    private String errStr;

    /**
     * 错误码
     */
    private int errCode;

    /**
     * 返回的音频数据（没有音频头）
     */
    private byte data[];

    /**
     * 音频数据的信息
     */
    private Map<String, String> dataInfo;

    /**
     * 最后一次音频标识符
     */
    private boolean endFlag;

    public String getErrStr() {
        return errStr;
    }

    public void setErrStr(String errStr) {
        this.errStr = errStr;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte data[]) {
        this.data = data;
    }

    public Map<String, String> getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(Map<String, String> dataInfo) {
        this.dataInfo = dataInfo;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }
}
