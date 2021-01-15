import com.google.gson.Gson;
import com.iflytek.vcp.voice.engine.tts.domain.TtsParams;
import com.iflytek.vcp.voice.engine.tts.utils.WavUtils;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {
    private static final String URL = "http://172.16.12.39:10011/createRec";

    @Test
    public void testHttpClient() {
        final TtsParams params = new TtsParams("147258369", "yifeng");
        params.setSpeed("300");
        params.setVolume("20");
        //params.setSampleRate("8000");
        final RequestBodyJson bodyJson = new RequestBodyJson();
        bodyJson.sessionParam = convertParams(params);
        bodyJson.text = "666666666666666666666666";
        try {
            final String resp = new OkHttpClient().newCall(new Request.Builder()
                    .url(URL)
                    .post(RequestBody.create(
                            MediaType.parse("application/json; charset=utf-8"),
                            new Gson().toJson(bodyJson)))
                    .build())
                    .execute()
                    .body()
                    .string();
            System.out.println(resp);
            saveResp(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveResp(String body) throws IOException {
        FileOutputStream out = new FileOutputStream("D:\\xf\\oo166.wav");
        String[] resultList = body.split("\n");
        out.write(WavUtils.createAudio(16, body.length()).createWAVHeader());
        Gson gson = new Gson();
        for (String json : resultList) {
            ResponseResult resp = gson.fromJson(json, ResponseResult.class);
            out.write(Base64.getDecoder().decode(resp.result.data));
        }
        out.close();
    }

    private static class ResponseResult {
        Result result;

        private static class Result {
            String errStr;
            int errCode;
            String data;
            DataInfo dataInfo;
        }

        private static class DataInfo {
            String text;
            String currStart;
            String curEnd;
        }
    }

    private static class RequestBodyJson {
        Map<String, String> sessionParam;
        String text;
        boolean endFlag = true;
    }

    private static Map<String, String> convertParams(TtsParams ttsParams) {
        Map<String, String> params = new HashMap<>();
        params.put("sid", ttsParams.getSid());
        params.put("voice_name", ttsParams.getVoiceName());
        if (!StringUtil.isNullOrEmpty(ttsParams.getSampleRate())) {
            params.put("sample_rate", ttsParams.getSampleRate());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getFrameSize())) {
            params.put("frame_size", ttsParams.getFrameSize());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getAudioCoding())) {
            params.put("audio_coding", ttsParams.getAudioCoding());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getSpeed())) {
            params.put("speed", ttsParams.getSpeed());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getVolume())) {
            params.put("volume", ttsParams.getVolume());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getPitch())) {
            params.put("pitch", ttsParams.getPitch());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getReadAllMarks())) {
            params.put("read_all_marks", ttsParams.getReadAllMarks());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getReadNumber())) {
            params.put("read_number", ttsParams.getReadNumber());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getTextType())) {
            params.put("text_type", ttsParams.getTextType());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getReadEnglish())) {
            params.put("read_english", ttsParams.getReadEnglish());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getStallStyle())) {
            params.put("stall_style", ttsParams.getStallStyle());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getPhrase_style())) {
            params.put("phrase_style", ttsParams.getPhrase_style());
        }
        if (!StringUtil.isNullOrEmpty(ttsParams.getEnter_treat())) {
            params.put("enter_treat", ttsParams.getEnter_treat());
        }
        return params;
    }
}
