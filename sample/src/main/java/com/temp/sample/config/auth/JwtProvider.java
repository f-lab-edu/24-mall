package com.temp.sample.config.auth;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Calendar;
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

  public static final String ISSUER = "24-mall.com";


  public String createAccessToken(AuthUser authUser) {

    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", authUser.getRoles());

    SystemKey lastSecretKey = systemKeyRepository.findLastSecretKey();
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(lastSecretKey.getEncKey()));

    Date now = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now); // 현재 시간 기준
    Date expiration = calendar.getTime();
    calendar.add(Calendar.MINUTE, 30);

    return Jwts.builder()
        .issuer(ISSUER)
        .subject(String.valueOf(authUser.getUserId()))
        .header().keyId("24-mall").and()
        .claims(claims)
//        .audience().add(audience).and()
        .issuedAt(now)
        .expiration(expiration)
        .signWith(secretKey)
        .compact();
  }


  public AuthUser readLoginToken(String token) {

    Jws<Claims> jws = Jwts.parser()
        .clockSkewSeconds(180)
        .requireSubject("login")
        .requireIssuer("24-mall")
        .requireAudience("24-mall")
        .keyLocator(jKeyLocator)
        .build()
        .parseSignedClaims(token);

    log.info("복호화 성공");
    Claims claims = jws.getPayload();

    Date expiration = claims.getExpiration();
    Date now = new Date();
    if(expiration.before(now)) {
      throw new RuntimeException("만료된 토큰입니다.");
    }

    return new AuthUser(claims.get("userId", Long.class), claims.get("roles", ArrayList.class));

  }


  public String generateSecretKey() {
    SecretKey secretKey = SIG.HS256.key().build();

    return Encoders.BASE64.encode(secretKey.getEncoded());
  }


}
