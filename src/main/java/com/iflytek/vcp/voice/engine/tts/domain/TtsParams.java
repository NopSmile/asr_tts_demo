package com.iflytek.vcp.voice.engine.tts.domain;

import lombok.Data;

@Data
public class TtsParams {
    /**
     * 请求id,这个参数一定要传,方便以后检索日志
     */
    private String sid;

    /**
     * 发音人名称
     */
    private String voiceName;

    /**
     * 采样率，默认16000
     */
    private String sampleRate;

    /**
     * 固定每次编码块的大小（640的倍数，如：640、1280...)，默
     * 认为0，即不固定每次编码块的大小。
     */
    private String frameSize;

    /**
     * 音频编码格式，默认raw
     */
    private String audioCoding;

    /**
     * 语速，默认是0，范围[-500,500]
     */
    private String speed;

    /**
     * 音量
     */
    private String volume;

    /**
     * 音高
     */
    private String pitch;

    /**
     * 是否读出所有的标点符号，0或1
     */
    private String readAllMarks;

    /**
     * 读数字的方式
     */
    private String readNumber;

    /**
     * 文本类型
     */
    private String textType;

    /**
     * 读取英文的方式
     */
    private String readEnglish;

    /**
     * 设置停顿风格
     */
    private String stallStyle;

    /**
     * 设置短语库风格
     */
    private String phrase_style;

    /**
     * 分句时对回车符的处理
     */
    private String enter_treat;

    public TtsParams() {
    }

    public TtsParams(String sid, String voiceName) {
        this.sid = sid;
        this.voiceName = voiceName;
    }

    public TtsParams(String sid) {
        this.sid = sid;
    }
}
