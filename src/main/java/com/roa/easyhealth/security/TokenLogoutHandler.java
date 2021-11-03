package com.roa.easyhealth.security;

import com.roa.easyhealth.entity.result.MyResult;
import com.roa.easyhealth.util.ResponseUtil;
import generator.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLogoutHandler implements LogoutHandler {

    private UserService userService;
    public TokenLogoutHandler(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token");
        if(token != null){
            userService.delToken(token);
            ResponseUtil.responseReturnJson(httpServletResponse, MyResult.OK().setDataMessage("退出成功"));
        }
    }
}
