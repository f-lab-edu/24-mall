package com.temp.sample.config.auth;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    @Mock
    SystemKeyRepository systemKeyRepository;

    @InjectMocks
    JwtProvider jwtProvider;

    @InjectMocks
    JKeyLocator jKeyLocator;



    @Test
//    @DisplayName("토큰 유효성 검사 테스트")
    void createLoginToken() {

        given(systemKeyRepository.findLastSecretKey())
                .willReturn(SystemKey.createMock(1L,"MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK", LocalDateTime.now(),true));

        given(systemKeyRepository.findById(1L))
                .willReturn(Optional.of(SystemKey.createMock(1L,"MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK", LocalDateTime.now(),true)));
        // 토큰 생성

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1L);
        claims.put("roles", "admin");

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK"));

        String loginToken = Jwts.builder()
                .issuer("24-mall")
                .subject("login")       // sub (Subject) 클레임 설정
                .header().keyId("24-mall").and() // alg, frm, zip 은 생략 가능
                .claims(claims) // 주제
                .audience().add("24-mall").and()
                .issuedAt(new Date())   // iat (Issued At) 클레임 설정
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey)          // 서명 설정 (기본 HS256 사용)
//          .id(String.valueOf(lastSecretKey.getId()))
                .compact();// 최종 JWT 생성

        System.out.println("loginToken = " + loginToken);


        Jws<Claims> jws = Jwts.parser()
                .clockSkewSeconds(180) // 시계오차 오차 허용 시간 설정 3분, 5분이상이면 심각한 문제 -> 생성서버와 검증서버 시간차
                .requireSubject("login") // 추가 검증.
                .requireIssuer("24-mall")
                .requireAudience("24-mall")
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(loginToken);  // JWT 파싱

        System.out.println("jws.getPayload() = " + jws.getPayload());



        Jws<Claims> dJws = Jwts.parser()
                .clockSkewSeconds(180) // 시계오차 오차 허용 시간 설정 3분, 5분이상이면 심각한 문제 -> 생성서버와 검증서버 시간차
                .requireSubject("login") // 추가 검증.
                .requireIssuer("24-mall")
                .requireAudience("24-mall")
                .keyLocator(jKeyLocator) // 동적키
                .build()
                .parseSignedClaims(loginToken);  // JWT 파싱


    }


}