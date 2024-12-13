package com.yael.springcloud.app.gateway.mvs_gateway.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/authorized")
    public Map<String, Object> auth( @RequestParam String code ){
        Map<String, Object> res = new HashMap<>();

        res.put("code", code);

        return res;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(){
        return Collections.singletonMap("logout", "Ok");
    }

}
