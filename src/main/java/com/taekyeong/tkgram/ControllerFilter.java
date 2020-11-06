package com.taekyeong.tkgram;

import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@RequiredArgsConstructor
@WebFilter(urlPatterns = "/api/v1")
public class ControllerFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filer!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                if(header.equals("Authorization")) {
                    String token = httpRequest.getHeader(header).substring("Bearer ".length());
                    if(token.length() > 0) {
                        Long userIdx = jwtTokenProvider.getUserindex(token);
                        break;
                    }
                }
            }
        }
    }
}
