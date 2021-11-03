package com.roa.easyhealth.security;

import generator.entity.User;
import generator.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class TokenAuthFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public TokenAuthFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        if(authenticationToken!=null){
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
//        try{
        chain.doFilter(request,response);
//        }catch (Exception e){
//            util.responseReturnJson(response, request.getRequestURI()+"    请求失败，请确保参数无误", HttpStatus.BAD_REQUEST);
//            logger.error("请求失败  "+request.getRequestURI());
//            util.LogException(this,e);
//
//        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token !=null){
            final User user = userService.getUserByToken(token);
            if(user == null){
                return null;
            }
//            List<String> value = (List<String>)redisUtil.getValue(username);
            Collection<GrantedAuthority> authorities = new ArrayList<>();

//            for(String permission: user.getPermissions()){
//                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permission);
//                authorities.add(auth);
//            }
            request.setAttribute("user",user);
            return new UsernamePasswordAuthenticationToken(user.getName(),token,authorities);
        }
        return null;
    }
}
