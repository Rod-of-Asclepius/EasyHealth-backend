package com.roa.easyhealth.security;

import com.roa.easyhealth.entity.result.MyResult;
import com.roa.easyhealth.entity.result.MyResultData;
import com.roa.easyhealth.util.ResponseUtil;
import generator.entity.User;
import generator.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    public TokenLoginFilter(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService=userService;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = new User();
        String username = ResponseUtil.getAllAttribute(request ,"username");
        String password = ResponseUtil.getAllAttribute(request ,"password");
        String verify_token = ResponseUtil.getAllAttribute(request ,"verifyToken");
        String verify_code = ResponseUtil.getAllAttribute(request ,"verifyCode");

//        TODO: 验证码
//        Object value = redisUtil.getValue(Verify_Code_Controller.VERIFY_CODE + verify_token);
//        if(value==null|| verify_code==null || !value.toString().equalsIgnoreCase(verify_code)){
//            throw new _Exception("验证码错误","验证码错误");
//        }else{
//            redisUtil.delValue(Verify_Code_Controller.VERIFY_CODE + verify_token);
//        }

        user.setName(username);
        user.setPassword(password);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword(),
                new ArrayList<>()));

    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        final String token = userService.genToken(user.getCurrentUserInfo());
        userService.setToken(token,user.getCurrentUserInfo());
//        redisUtil.setValue(user.getCurrentUserInfo().getUser_name(),user.getPermissionValueList());
//        user.getCurrentUserInfo().setUser_password(null);
        final User currentUserInfo = user.getCurrentUserInfo();
        final MyResultData myResultData = new MyResultData(currentUserInfo);
        myResultData.addProp("token",token);
        ResponseUtil.responseReturnJson(response, MyResult.OK().setResultData(myResultData));
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        ResponseUtil.responseReturnJson(response, MyResult.UNAUTHORIZED().setDataMessage("登录失败"));
    }
}
