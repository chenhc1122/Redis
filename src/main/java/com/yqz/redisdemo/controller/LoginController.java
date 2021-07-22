package com.yqz.redisdemo.controller;

import com.yqz.redisdemo.service.RedisBase;
import com.yqz.redisdemo.service.RedisBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisBase redisBase;
    @GetMapping("/login/{username}/{password}")
    public List<Object> login(HttpSession session, @PathVariable("username") String username, @PathVariable("password") String password){
        System.out.println("im coming");
        String sessionId = session.getId();
        System.out.println(sessionId+"----------------------"+username+"----------------------"+password+"----------------------");
        HashMap<String, Object> map = new HashMap<>();
        map.put(sessionId,1);
        map.put(sessionId+"##"+"username",username);
        map.put(sessionId+"##"+"password",password);
        redisBatch.barchSet(map);
        redisBase.expire(sessionId,60);
        redisBase.expire(sessionId+"##"+"username",60);
        redisBase.expire(sessionId+"##"+"password",60);
        List<String> list =new ArrayList<>();
        list.add(sessionId);
        list.add(sessionId+"##"+"username");
        list.add(sessionId+"##"+"password");
        List<Object> allList = redisBatch.batchGet(list);
        return allList;

    }


    @GetMapping("/logout")
    public String  logout(HttpSession httpSession){
        String sessionId = httpSession.getId();
        if (redisBase.exists(sessionId)) {
            redisBase.del(sessionId);
            redisBase.del(sessionId+"##"+"username");
            redisBase.del(sessionId+"##"+"password");
            return "已退出";
        }else return "未登录";
    }

}
