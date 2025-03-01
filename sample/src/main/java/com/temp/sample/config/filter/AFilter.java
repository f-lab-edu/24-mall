package com.temp.sample.config.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class AFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("A필터 초기화!!!!");
    }

    @Override
    public void destroy() {
        System.out.println("A필터 파괴!!!!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Request >>> 필터 A !!!!!!!!!!!");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Response <<< 필터 A !!!!!!!!!!!");

    }
}