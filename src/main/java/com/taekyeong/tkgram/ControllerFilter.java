package com.taekyeong.tkgram;

import com.taekyeong.tkgram.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/api/v1/*")
public class ControllerFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String token = request.getHeader("Authorization").substring("Bearer ".length());
        if(token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        }
        else {
            Long userIdx = jwtTokenProvider.getUserIndex(token);
            if(userIdx > 0) {
               filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            }

        }
    }
}
