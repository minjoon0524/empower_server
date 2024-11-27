package com.inhatc.empower.interceptor;


import com.inhatc.empower.domain.WhiteIp;
import com.inhatc.empower.repository.WhiteIpRepository;
import com.inhatc.empower.util.ClientIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j2
public class IpAccessInterceptor implements HandlerInterceptor {
    private final WhiteIpRepository whiteIpRepository;

    // 컨트롤러가 실행 이전에 처리해야 할 작업이 있는경우 혹은 요청정보를 가공하거나 추가하는경우 사용
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String clientIp = ClientIpUtil.getClientIP(request);
        log.info(clientIp);
        if (clientIp.equals("127.0.0.１")) {
            // 로컬 접속이면 당연히 true
            return true;
        }

        if (!whiteIpRepository.findByAccessIp(clientIp).isPresent()) {
            // 새로운 화이트리스트 IP 테스트 진행
            // 화이트 리스트에 추가된 ip만 허용
//            WhiteIp newWhiteIp = WhiteIp.builder()
//                    .accessIp(clientIp)
//                    .accessDate(LocalDateTime.now())
//                    .build();
//            whiteIpRepository.save(newWhiteIp); // DB에 저장

            log.warn("Forbidden access, URI: {}, IP: {}", request.getRequestURI(), clientIp);
            response.sendError(403, "IP Forbidden");
            return false;
        }

        return true;
    }
}
