package com.temp.sample.service;

import com.temp.sample.config.auth.AuthUser;
import com.temp.sample.config.auth.JwtProvider;
import com.temp.sample.controller.request.LoginReq;
import com.temp.sample.dao.UserRepository;
import com.temp.sample.entity.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;

  @Override
  public String login(LoginReq req) {
    User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("잘못된 정보입니다."));

    if(!StringUtils.equals(req.getPassword(), user.getPassword())) throw new RuntimeException("잘못된 정보입니다.");
    ;
    AuthUser authUser = new AuthUser(user.getId(),
        new ArrayList<>(List.of("bronze", "silver", "gold")));

    String loginToken = jwtProvider.createLoginToken(authUser);

    return loginToken;
  }
}
