package com.iflytek.vcp.voice.engine.tts.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author irun
 */
public class TtsSessionParam {

    /**
     * 会话id，这个参数一定要传，方便以后检索日志
     */
    private String sid;

    /**
     * 8000或16000,采样率，默认16000
     */
    private String sample_rate;

    /**
     * 整数,固定每次编码块的大小（640的倍数，如：640、1280...），默认为0，即不固定每次编码块的大小,和 audio_coding 一起使用
     */
    private String frame_size;

    /**
     * raw/speex/speex-wb/opus,音频编码格式，默认raw,如果是 raw 可以不设置, speex/speex-wb 才有效果
     */
    private String audio_coding;

    /**
     * xiaoyi/catherine/...(可增加),发音人
     */
    private String voice_name;

    /**
     * vid
     */
    private Integer vid;

    /**
     * -500~500,语速，默认是0
     */
    private String speed;

    /**
     * int 音量
     */
    private String volume;

    /**
     * int 音高
     */
    private String pitch;

    /**
     * 0~1 是否读出所有的标点符号
     */
    private String read_all_marks;

    /**
     * 0~3 读数字的方式
     */
    /* enum
     {
         TTS_RN_AUTO_VALUE               =    0,     Auto, read as value if not sure
         TTS_RN_VALUE                    =    1,     Read as value
         TTS_RN_DIGIT                    =    2,     Read as string
         TTS_RN_AUTO_DIGIT               =    3,     Auto, read as string if not sure
     };*/
    private String read_number;

    /**
     * 0~4 文本类型
     */
    /* enum
     {
         TTS_TT_AUTO                     =    0,     auto, read as plain text if not sure
         TTS_TT_PLAINTEXT                =    1,     read as plain text
         TTS_TT_CSSMLTEXT                =    2,     read as CSSML text
         TTS_TT_SSMLTEXT                 =    3,     read as SSML text
         TTS_TT_EMAILTEXT                =    4,     read as EMail text
     };*/
    private String text_type;

    /**
     * 0~2 读取英文的方式
     */
    /* enum
     {
         TTS_RE_AUTO_WORD                =    0,     Auto, read as word if not sure
         TTS_RE_LETTER                   =    1,     Read as letter
         TTS_RE_AUTO_LETTER              =    2,     Auto, read as letter if not sure
     };*/
    private String read_english;

    /**
     * 0~4 设置停顿风格
     */
    /* enum
     {
         TTS_SSL_NORMAL                  =    0,     Normal
         TTS_SSL_STALL                   =    1,     A little stall
         TTS_SSL_SNATCHY                 =    2,     Evident snatchy
         TTS_SSL_UNCEASING               =    3,     Unceasing
         TTS_SSL_VERBOSE                 =    4,     Vobose
     };*/
    private String stall_style;

    /**
     * irf_path
     * .irf资源文件的路径（绝对路径或相对于启动目录的路径）
     */
    private String irf_path;

    /**
     * 扩展字段存入 map 中
     */
    private Map<String, String> extend = new HashMap<String, String>();

    /**
     * default constructor
     */
    public TtsSessionParam() {

    }

    /**
     * constructor
     * explicitly specified sid, voice_name
     *
     * @param sid        sid
     * @param voice_name voice_name
     */
    public TtsSessionParam(String sid, String voice_name) {
        this.sid = sid;
        this.voice_name = voice_name;
    }

    /**
     * constructor
     * explicitly specified sid, vid
     *
     * @param sid
     * @param vid
     */
    public TtsSessionParam(String sid, int vid) {
        this.sid = sid;
        this.vid = vid;
    }

    public TtsSessionParam(String sid, String sample_rate, String frame_size, String audio_coding,
                           String voice_name, Integer vid, String speed, String volume, String pitch,
                           String read_all_marks, String read_number, String text_type,
                           String read_english, String stall_style, String irf_path) {
        this.sid = sid;
        this.sample_rate = sample_rate;
        this.frame_size = frame_size;
        this.audio_coding = audio_coding;
        this.voice_name = voice_name;
        this.vid = vid;
        this.speed = speed;
        this.volume = volume;
        this.pitch = pitch;
        this.read_all_marks = read_all_marks;
        this.read_number = read_number;
        this.text_type = text_type;
        this.read_english = read_english;
        this.stall_style = stall_style;
        this.irf_path = irf_path;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSample_rate() {
        return sample_rate;
    }

    public void setSample_rate(String sample_rate) {
        this.sample_rate = sample_rate;
    }

    public String getFrame_size() {
        return frame_size;
    }

    public void setFrame_size(String frame_size) {
        this.frame_size = frame_size;
    }

    public String getAudio_coding() {
        return audio_coding;
    }

    public void setAudio_coding(String audio_coding) {
        this.audio_coding = audio_coding;
    }

    public String getvoice_name() {
        return voice_name;
    }

    public void setvoice_name(String voice_name) {
        this.voice_name = voice_name;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getRead_all_marks() {
        return read_all_marks;
    }

    public void setRead_all_marks(String read_all_marks) {
        this.read_all_marks = read_all_marks;
    }

    public String getRead_number() {
        return read_number;
    }

    public void setRead_number(String read_number) {
        this.read_number = read_number;
    }

    public String getText_type() {
        return text_type;
    }

    public void setText_type(String text_type) {
        this.text_type = text_type;
    }

    public String getRead_english() {
        return read_english;
    }

    public void setRead_english(String read_english) {
        this.read_english = read_english;
    }

    public String getStall_style() {
        return stall_style;
    }

    public void setStall_style(String stall_style) {
        this.stall_style = stall_style;
    }

    public String getIrf_path() {
        return irf_path;
    }

    public void setIrf_path(String irf_path) {
        this.irf_path = irf_path;
    }

    public Map<String, String> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, String> extend) {
        this.extend = extend;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }
}
