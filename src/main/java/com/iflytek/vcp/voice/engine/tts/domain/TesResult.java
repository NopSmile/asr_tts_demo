package com.iflytek.vcp.voice.engine.tts.domain;

/**
 * @author Created by jianwu6 on 2020/3/17 11:01
 */
public class TesResult {

    /**
     * 返回的错误信息
     */
    private String errStr;

    /**
     * 返回的错误码
     */
    private int errCode;

    /**
     * 结果
     */
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
