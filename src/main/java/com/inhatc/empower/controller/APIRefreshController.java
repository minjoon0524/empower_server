package com.inhatc.empower.controller;

import com.inhatc.empower.util.CustomJWTException;
import com.inhatc.empower.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    public Map<String, Object> refresh(@RequestHeader("Authorization") java.lang.String authHeader,
                                      java.lang.String refreshToken) {
        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }
        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_STRING");
        }
        java.lang.String accessToken = authHeader.substring(7);

        // Access 토큰이 만료되지 않았다면
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // Refresh 토큰 검증
        Map<java.lang.String, java.lang.Object> claims = JWTUtil.validateToken(refreshToken);
        System.out.println("refresh ... claims: " + claims);

        java.lang.String newAccessToken = JWTUtil.generateToken(claims, 10);
        java.lang.String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ?
                JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    // 시간이 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {
        // JWT exp를 날짜로 변환
        java.util.Date expDate = new java.util.Date((long) exp * 1000);

        // 현재 시간과의 차이 계산 - 밀리세컨즈
        long gap = expDate.getTime() - System.currentTimeMillis();

        // 분 단위 계산
        long leftMin = gap / (1000 * 60);

        // 1시간도 안 남았는지..
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch (CustomJWTException ex) {
            if (ex.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }


}

