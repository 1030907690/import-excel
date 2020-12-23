package com.springboot.sample.cross;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class CrossFilter implements Filter {

    @Value("${vue.cross.config}")
    private String vueCrossConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String originHeader = request.getHeader("Origin"); // 来源-域
        log.info("originHeader [ {} ] vueCrossConfig [ {} ] ",originHeader , vueCrossConfig);
        if (null != originHeader) {
            if (vueCrossConfig.indexOf(originHeader) > -1) {
                response.setHeader("Access-Control-Allow-Origin", originHeader);
            }
        }

        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-User-Login, content-type, Access-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        if (!Objects.equals(request.getMethod(), "OPTIONS")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
