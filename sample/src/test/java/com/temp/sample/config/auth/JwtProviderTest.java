package com.temp.sample.config.auth;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.ProtectedHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class JwtProviderTest {

    @InjectMocks
    JwtProvider jwtProvider;

    @Mock
    JKeyLocator jKeyLocator;

    @Mock
    SystemKeyRepository systemKeyRepository;

    @Test
    @DisplayName("토큰 유효성 검사 테스트")
    void createLoginToken() {

        given(systemKeyRepository.findLastSecretKey())
                .willReturn(SystemKey.createMock(1L,"MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK", LocalDateTime.now(),true));

        // 토큰 생성
        String loginToken = jwtProvider.createLoginToken(new AuthUser(1L, Arrays.asList("a", "b", "c")));

        System.out.println("loginToken = " + loginToken);

        // 토큰 읽기
        AuthUser authUser = jwtProvider.readLoginToken(loginToken);

        System.out.println("authUser.getUserId = " + authUser.getUserId());
        System.out.println("authUser = " + authUser.getRoles());



    }


}