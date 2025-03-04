package com.temp.sample.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthUser {
  private final Long userId;

  private final List<String> roles;

}
