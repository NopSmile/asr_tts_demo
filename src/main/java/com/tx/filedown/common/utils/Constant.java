package com.tx.filedown.common.utils;

/**
 * 根据github项目中，您必须将lockAtMostFor设置为比正常执行时间长得多的值。这个时间小于轮训时间
 * 否则会出问题
 * @author whoami
 */
public class Constant {
    /**
     * 科大新接口
     */
    public static final String KEY_ISTIFLY_ID = "id";
    public static final String KEY_ISTIFLY_AUDIO = "audio";
    public static final String KEY_ISTIFLY_CALLBACK = "callback";
    public static final String KEY_ISTIFLY_RESID = "resId";
    public static final String KEY_ISTIFLY_TYPE = "type";
    public static final String KEY_ISTIFLY_TAGS = "tags";

    public static final int HTTP_TIMEOUT=60000;//默认超时时间
    //科大离线转写引擎服务器地址
     public static String ISTURL = "http://172.16.12.38:5583/tuling/asr/v21/ist/async/process";
    //  public static String ISTURL = "http://192.168.60.76:33721/tuling/asr/v21/ist/async/process";
    //转科大写音频回调地址
     public static String NOTISTIFYURL="http://192.168.102.215:52220/ist/callback";
    //  public static String NOTISTIFYURL="http://192.168.60.79:52220/ist/callback";


    public static final String asrurl="http://192.168.102.215:52220/asr/";
    public static final String ttsurl="http://192.168.102.215:52220/tts/";


    public static String LASTRESULT="";
    public static String STRRESULT="";


}
