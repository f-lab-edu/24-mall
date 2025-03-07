package com.temp.sample.config.filter;

import jakarta.servlet.*;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class BFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("B필터 초기화!!!");
    }

    @Override
    public void destroy() {
        System.out.println("B필터 파괴!!!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Request >>> 필터 B !!!!!!!!!!!");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Response <<< 필터 B !!!!!!!!!!!");

    }
}