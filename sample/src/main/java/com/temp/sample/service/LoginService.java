package com.temp.sample.service;

import com.temp.sample.controller.request.LoginReq;
import com.temp.sample.service.response.UserResponse;

public interface LoginService {

  String login(LoginReq req);

}
