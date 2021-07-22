package com.yqz.redisdemo.controller;

import com.yqz.redisdemo.service.RedisBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private RedisBatch redisBatch;
    @GetMapping("/login/{username}/{password}")
    public List<Object> login(HttpSession session, @PathVariable("username") String username, @PathVariable("password") String password){
        System.out.println("im coming");
        String sessionId = session.getId();
        System.out.println(sessionId+"----------------------"+username+"----------------------"+password+"----------------------");
        HashMap<String, Object> map = new HashMap<>();
        map.put(sessionId,1);
        map.put("username",username);
        map.put("password",password);
        redisBatch.barchSet(map);
        List<String> list =new ArrayList<>();
        list.add(sessionId);
        list.add("username");
        list.add("password");
        List<Object> allList = redisBatch.batchGet(list);
        return allList;
    }
}
