package com.tx.filedown.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tx.filedown.common.utils.Constant;
import com.tx.filedown.common.utils.FileUtil;
import com.tx.filedown.common.utils.Tools;
import com.tx.filedown.vo.CallbackVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zzc
 * @Param: null
 * @return:
 * @Description:
 * @Date: 2020/2/21 17:36
 */

@Slf4j
@RestController
@RequestMapping(value = "/ist")
public class CallbackController {

    @Value("${file.upload.path}")
    private String filePath;

    private Map<String, String> results = new HashMap<>();

    @RequestMapping(value = "/callback", method = {RequestMethod.POST, RequestMethod.GET})
    public void orderCallback(HttpServletRequest request) {
        //这里要读取request的body！！！
        StringBuilder buffer = new StringBuilder();
        //读取Body
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
        }
        String body = buffer.toString();
        CallbackVo resp = JSON.parseObject(body, CallbackVo.class);  //解析数据也变成了body，而不是原来的result！！！
        StringBuffer resultBuff = new StringBuffer("");


        if (resp.getBody().getLattices() == null) {
            log.info("转写错误：{}", resp.getId());
        }

        for (CallbackVo.Body.Lattice l : resp.getBody().getLattices()) {
            Tools.istWithoutRole(resultBuff, l.getJsonCn1best());
        }

        System.out.println(">>>>>>>>>>>>>>>>"+resultBuff.toString());

        log.info("解析转写结果 aid:{},result:{}", resp.getId(), resultBuff.toString());
        //这里面的aid我直接用calli代替

        String fileName = filePath+resp.getId()+".txt";
        File fileCur = new File(fileName);
        FileUtil.createFile(fileCur);
        FileUtil.filechaseWrite(resultBuff.toString() + "\n", fileName);

        try {
            WebSocketServer.sendInfo(resultBuff.toString(),"111");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
