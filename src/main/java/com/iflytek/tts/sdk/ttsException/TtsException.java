package com.iflytek.tts.sdk.ttsException;

import java.io.Serializable;

public class TtsException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -59122384297585619L;

    private int errorCode = 1000;

    public TtsException() {
    }

    public TtsException(int errorCode, String arg0) {
        super(arg0);
        this.errorCode = errorCode;
    }

    public TtsException(String arg0) {
        super(arg0);
    }

    public TtsException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public TtsException(int errorCode, String arg0, Throwable arg1) {
        super(arg0, arg1);
        this.errorCode = errorCode;
    }

    public TtsException(Throwable arg0) {
        super(arg0);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
