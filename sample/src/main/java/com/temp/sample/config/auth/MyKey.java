package com.temp.sample.config.auth;

import java.security.Key;

public class MyKey implements Key {

  @Override
  public String getAlgorithm() {
    return "";
  }

  @Override
  public String getFormat() {
    return "";
  }

  @Override
  public byte[] getEncoded() {
    return new byte[0];
  }
}
