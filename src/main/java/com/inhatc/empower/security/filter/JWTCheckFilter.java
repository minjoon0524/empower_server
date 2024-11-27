package com.inhatc.empower.security.filter;

import com.google.gson.Gson;
import com.inhatc.empower.dto.MemberDTO;
import com.inhatc.empower.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    // "/login"경로로 들어왔을 경우 당연히 JWT Token이 없기에 체크할 필요가 없음
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path=request.getRequestURI();

        log.info("check url -------- "+path);
        // /login 경로의 호출은 체크하지 않음
        if (path.startsWith("/login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs")  ){
            return true;
        }

        //false==check
        return super.shouldNotFilter(request);
    }


    //OncePerRequestFilter - 모든 경우의 체크
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        log.info("------------------JWTCheckFilter.................");
        String authHeaderStr = request.getHeader("Authorization");

        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // JWT가 없으면 다음 필터로 진행
        }



        try {
            // Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            log.info("Extracted access token: {}", accessToken);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info("JWT claims: " + claims);


            String eid=(String)claims.get("eid");
            String name = (String) claims.get("name");
            String pw = (String) claims.get("pw");
            String department=(String)claims.get("department");
            String email = (String) claims.get("email");
            String phone = (String) claims.get("phone");
            String address = (String) claims.get("address");
            String position = (String) claims.get("position");
            LocalDate hireDate = (LocalDate) claims.get("hireDate");
            Boolean memberCheck = (Boolean) claims.get("memberCheck");
            String profileName = (String) claims.get("profileName");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(eid, name, pw, department,
                    email, phone, address, position,
                    hireDate, memberCheck, profileName,roleNames);

            log.info("-----------------------------------");
            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.info("JWT Check Error..............");
            System.err.println(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }


}
