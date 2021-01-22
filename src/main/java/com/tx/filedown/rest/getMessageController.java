package com.tx.filedown.rest;

import com.tx.filedown.common.utils.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class getMessageController {
    @GetMapping(value = "/asrmsg/{timestamp}")
    @ResponseBody
    public String selectExceptionById( @PathVariable("timestamp") String timestamp) {
        return Constant.asrmap.get(timestamp);
    }

}
