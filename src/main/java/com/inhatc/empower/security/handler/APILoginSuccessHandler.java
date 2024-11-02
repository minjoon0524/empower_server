package com.inhatc.empower.security.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inhatc.empower.dto.MemberDTO;
import com.inhatc.empower.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,Authentication authentication
    ) throws IOException, ServletException {
        log.info("-------------------------------------");
        log.info(authentication);
        log.info("-------------------------------------");

        MemberDTO memberDTO=(MemberDTO)authentication.getPrincipal();
        log.info(memberDTO);
        Map<String, Object> claims=memberDTO.getClaims();


        String accessToken= JWTUtil.generateToken(claims, 130);
        String refreshToken= JWTUtil.generateToken(claims,60*24);


        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        Gson gson = new GsonBuilder().serializeNulls().create();

        String jsonStr=gson.toJson(claims);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");  // UTF-8로 인코딩 설정
        PrintWriter printWriter=response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}

