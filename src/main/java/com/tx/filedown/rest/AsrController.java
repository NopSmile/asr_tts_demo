package com.tx.filedown.rest;

import com.tx.filedown.common.utils.Constant;
import com.tx.filedown.common.utils.MapRestResponse;
import com.tx.filedown.common.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@RestController
@RequestMapping("/asr")
public class AsrController {
    @Value("${file.upload.path}")
    private String filePath;

    @Value("${asr}")
    private int asr;

    @PostMapping("/upload")
    public MapRestResponse uploading(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws UnknownHostException {

        String filename= UUID.randomUUID().toString().replaceAll("-","")+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
        String afterType=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
        try {
            uploadFile(file.getBytes(), filePath, filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败!");
            return MapRestResponse.error().put("msg","文件上传失败");
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
            String result="http://127.0.0.1:52220/asr/"+wavcid+".txt";
            Constant.LASTRESULT=result;
            return MapRestResponse.ok().put("path",result).put("filename",wavcid+".txt");
        }else{
            return MapRestResponse.ok().put("path",filePath);
        }
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
