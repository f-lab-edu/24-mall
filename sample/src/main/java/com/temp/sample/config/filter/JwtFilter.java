package com.temp.sample.config.filter;

import com.temp.sample.config.auth.AuthUser;
import com.temp.sample.config.auth.JwtProvider;
import com.temp.sample.dao.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;
  private final Environment environment;
  @Value("${spring.profiles.active:}")
  private String activeProfile;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String remoteAddr = request.getRemoteAddr();

    if ("dev".equals(activeProfile)) { // 개발환경 통과

      // 로그인 요청이 아니면 authorization 검사
      if (!request.getRequestURL().toString().endsWith("login")) {

        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token)) {
          throw new RuntimeException("Token is empty");
        }

        AuthUser authUser = jwtProvider.readLoginToken(token);

        request.setAttribute("userId", authUser.getUserId());
      }
    }


    filterChain.doFilter(request, response);

  }

}
