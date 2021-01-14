package com.tx.filedown.rest;

import com.alibaba.fastjson.JSONObject;
import com.tx.filedown.common.utils.Constant;
import com.tx.filedown.common.utils.Tools;
import com.tx.filedown.utils.HttpUtil;
import com.tx.filedown.utils.Readword;
import com.tx.filedown.utils.ocrMethod;
import com.tx.filedown.utils.pdftopng;
import org.apache.pdfbox.cos.COSArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class AsrUploadController {
    @Value("${file.upload.path}")
    private String filePath;

    @Value("${asr}")
    private int asr;

    @GetMapping("/")
    public String uploladPage(Model model){
        if(!"".equals(Constant.LASTRESULT)){
            model.addAttribute("path",Constant.LASTRESULT);
            model.addAttribute("result",Constant.STRRESULT);
        }

        return "index";
    }


    @PostMapping("/")
    public String uploading(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws UnknownHostException {

        String filename=UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
        String afterType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
        try {
                uploadFile(file.getBytes(), filePath, filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败!");
            return "redirect:/";
        }
        System.out.println("文件名 "+filename+" 文件上传 "+filePath+" 成功!");
        if(1==asr){
            String wavcid = filename;
            //生成唯一的消息通知地址
            String task_notyfy_url= Constant.NOTISTIFYURL;
            String waitingUrl=Constant.asrurl+filename;
            //添加任务
            Tools.addIstTask(wavcid, Constant.ISTURL,waitingUrl,task_notyfy_url);
            System.out.println("回调地址为:"+task_notyfy_url + "-----> 录音地址为:"+waitingUrl);
            String result="http://172.31.202.41:52220/asr/"+wavcid+".txt";
            Constant.LASTRESULT=result;
            model.addAttribute("path",result);
            model.addAttribute("filename",wavcid+".txt");
        }else{
            model.addAttribute("path",filePath);
        }
        System.out.println(filePath+filename+InetAddress.getLocalHost());
        return "index";
    }



    public void  uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
