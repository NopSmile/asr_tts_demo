package com.tx.filedown.rest;

import com.google.gson.Gson;
import com.iflytek.vcp.voice.engine.tts.domain.TtsParams;
import com.iflytek.vcp.voice.engine.tts.utils.WavUtils;
import com.tx.filedown.common.utils.Constant;
import com.tx.filedown.common.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
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

@Controller
public class TtsController {
    @Value("${url}")
    private String URL;

    @GetMapping("/")
    public String uploladPage(Model model){
        if(!"".equals(Constant.LASTRESULT)){
            model.addAttribute("path",Constant.LASTRESULT);
            model.addAttribute("result",Constant.STRRESULT);
        }

        return "index";
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
