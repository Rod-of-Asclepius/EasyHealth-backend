package com.roa.easyhealth.controller;

import com.roa.easyhealth.entity.result.MyResult;
import com.roa.easyhealth.exception.MyException;
import generator.service.UserService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/register")
    public MyResult register(String username, String password) throws MyException {
        //TODO: 添加验证码
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);
        userService.addUser(username,encodePassword);
        return MyResult.OK().setDataMessage("注册成功！");
    }
}
