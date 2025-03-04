package com.temp.sample.config.auth;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final SystemKeyRepository systemKeyRepository;
    private final JKeyLocator jKeyLocator;


    public String createLoginToken(AuthUser authUser) {

      Map<String, Object> claims = new HashMap<>();
      claims.put("userId", authUser.getUserId());
      claims.put("roles", authUser.getRoles());

      
      SystemKey lastSecretKey = systemKeyRepository.findLastSecretKey();
      SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(lastSecretKey.getEncKey()));


      return Jwts.builder()
          .issuer("24-mall")
          .subject("login")       // sub (Subject) 클레임 설정
          .header().keyId("24-mall").and() // alg, frm, zip 은 생략 가능
          .claims(claims) // 주제
          .audience().add("24-mall").and()
          .issuedAt(new Date())   // iat (Issued At) 클레임 설정
          .expiration(new Date(System.currentTimeMillis() + 3600000))
          .signWith(secretKey)          // 서명 설정 (기본 HS256 사용)
          .id(String.valueOf(lastSecretKey.getId()))
          .compact();             // 최종 JWT 생성
    }

    // https://web.archive.org/web/20230428094039/https://stormpath.com/blog/where-to-store-your-jwts-cookies-vs-html5-web-storage

  public AuthUser readLoginToken(String token) {
    // Dynamic Key Lookup(keyId) 는 키를 데이터베이스(DB), 키 저장소(Keystore), HSM(Hardware Security Module)
    // 등에서 조회하는 메서드로 직접 구현할 수 있다.

    try {
      Jws<Claims> jws = Jwts.parser()
          .clockSkewSeconds(180) // 시계오차 오차 허용 시간 설정 3분, 5분이상이면 심각한 문제 -> 생성서버와 검증서버 시간차
          .requireSubject("login") // 추가 검증.
          .requireIssuer("24-mall")
          .requireAudience("24-mall")
          .keyLocator(jKeyLocator) // 동적키
          .build()
          .parseSignedClaims(token);  // JWT 파싱

      Claims claims = jws.getPayload();
      log.info("성공");

      return  new AuthUser(claims.get("userId", Long.class), claims.get("roles", ArrayList.class) );

    } catch (Exception e){
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

  }

}
