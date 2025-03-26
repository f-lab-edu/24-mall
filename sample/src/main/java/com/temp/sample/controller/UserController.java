package com.temp.sample.controller;

import com.temp.sample.controller.request.LoginReq;
import com.temp.sample.controller.response.ApiResponse;
import com.temp.sample.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(@RequestBody LoginReq req) {
    String token = loginService.login(req);

    return ResponseEntity.ok()
        .header("Authorization", token)
        .body(ApiResponse.OK);
  }

  @PostMapping("/")
  public ApiResponse root() {

    return ApiResponse.OK;
  }


}
