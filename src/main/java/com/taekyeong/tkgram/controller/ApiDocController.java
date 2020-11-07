package com.taekyeong.tkgram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiDocController {

    @GetMapping("/tkgram/apidoc")
    public String index() {
        return "index";
    }

    @GetMapping("tkgram/apidoc/user")
    public String user() {
        return "user";
    }

    @GetMapping("tkgram/apidoc/post")
    public String post() {
        return "post";
    }
}
