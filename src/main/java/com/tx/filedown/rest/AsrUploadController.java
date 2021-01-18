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

    @GetMapping("/")
    public String uploladPage(Model model){
        if(!"".equals(Constant.LASTRESULT)){
            model.addAttribute("path",Constant.LASTRESULT);
            model.addAttribute("result",Constant.STRRESULT);
        }

        return "index";
    }

}
