package com.tx.filedown.rest;

import com.google.gson.Gson;
import com.iflytek.vcp.voice.engine.tts.domain.TtsParams;
import com.iflytek.vcp.voice.engine.tts.utils.WavUtils;
import com.tx.filedown.common.utils.Constant;
import com.tx.filedown.common.utils.KeyGenerator;
import com.tx.filedown.common.utils.MapRestResponse;
import com.tx.filedown.common.utils.Tools;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/tts")
public class TtsController {
    @Value("${url}")
    private String URL;

    @Resource
    private ExecutorService executorService;


    @PostMapping("/go")
    public MapRestResponse save(@RequestBody Map<String,Object> map){

        String voiceName=(String)map.get("voiceName")==null?"xiaoxue":(String)map.get("voiceName");
        String speed=(Integer)map.get("speed")==null?"0":String.valueOf((Integer)map.get("speed"));
        String volume=(Integer)map.get("volume")==null?"0":String.valueOf((Integer)map.get("volume"));
        String text=(String)map.get("text")==null?"我是测试语音。":(String)map.get("text");
        String sid=(String)map.get("sid")==null? KeyGenerator.getUniqueID() :(String)map.get("sid");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final TtsParams params = new TtsParams("147258369", voiceName);
                params.setSpeed(speed);
                params.setVolume(volume);

                final RequestBodyJson bodyJson = new RequestBodyJson();
                bodyJson.sessionParam = convertParams(params);
                bodyJson.text = text;
                try {
                    final String resp = new OkHttpClient().newCall(new Request.Builder()
                            .url(URL)
                            .post(okhttp3.RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"),
                                    new Gson().toJson(bodyJson)))
                            .build())
                            .execute()
                            .body()
                            .string();
                    //System.out.println(resp);
                    saveResp(resp,sid);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        String audioUrl=Constant.ttsurl+sid+".wav";
        System.out.println(audioUrl);
        //model.addAttribute("audioUrl",audioUrl);
        return MapRestResponse.ok().put("audioUrl", audioUrl);
    }

    private static void saveResp(String body,String sid) throws IOException {
        FileOutputStream out = new FileOutputStream("D:\\data\\ttsdata\\"+sid+".wav");
        //FileOutputStream out = new FileOutputStream("/root/asrttsdemo/ttsdata/"+sid+".wav");
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
