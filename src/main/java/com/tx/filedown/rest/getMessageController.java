package com.tx.filedown.rest;

import com.tx.filedown.common.utils.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class getMessageController {
    @GetMapping(value = "/asrmsg")
    public String selectExceptionById(@RequestParam(value="time") String timestamp) {
        if("ERROR".equals(Constant.asrmap.get(timestamp))){
            return "未录上录音请重试！";
        }
        return Constant.asrmap.get(timestamp);
    }

}
