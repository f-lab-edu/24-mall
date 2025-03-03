package com.temp.sample.config.auth;

import java.util.ArrayList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthUser {
  private final Long userId;

  private final ArrayList<String> roles;

}
