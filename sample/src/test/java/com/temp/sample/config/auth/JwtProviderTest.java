package com.temp.sample.config.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

  @Mock
  SystemKeyRepository systemKeyRepository;

  @Mock
  private ProtectedHeader protectedHeader;

  @InjectMocks
  JwtProvider jwtProvider;

  @InjectMocks
  JKeyLocator jKeyLocator;


  @Test
  @DisplayName("토큰 읽기 예외 테스트")
  void exceptionTest() {

    given(systemKeyRepository.findLastSecretKey())
        .willReturn(createMockSystemKey());

    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", 1L);
    claims.put("roles", "admin");

    SecretKey secretKey = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode("MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK"));

    String accessToken = jwtProvider.createAccessToken(new AuthUser(1L,
            Arrays.asList("gold")));

    System.out.println("accessToken = " + accessToken);


    // subject 오류
    RuntimeException subjectException = assertThrows(RuntimeException.class, () -> {
      Jwts.parser()
          .clockSkewSeconds(180)
          .requireSubject("wrongSubject")
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(accessToken);
    });
    System.out.println("subject 오류 통과 = " + subjectException);

    // issuer 오류
    RuntimeException issuerException = assertThrows(RuntimeException.class, () -> {
      Jwts.parser()
          .clockSkewSeconds(180)
          .requireSubject("1")
          .requireIssuer("wrongIssuer")
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(accessToken);
    });
    System.out.println("issuer 오류 통과 = " + issuerException);

    // audience 오류
    RuntimeException audienceException = assertThrows(RuntimeException.class, () -> {
      Jwts.parser()
          .clockSkewSeconds(180)
          .requireSubject("1")
          .requireIssuer("24-mall.com")
          .requireAudience("wrongAudience")
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(accessToken);
    });
    System.out.println("audience 오류 통과 = " + audienceException);
  }


  @Test
  @DisplayName("automatic Reuse Detection")
  void automaticReuseDetection() {
    // 동일한 리프래쉬 토킁이 두번이상 사용되면 token family 무효화.
    // 공격자가 탈취한 토큰을 사용하려 할 경우 모든 관련 토큰 무효화 접근 차단.
    // localStorage x -> XSS 공격에 취약
    // replay attack 최소화
    // use https -> 네트워크 가로채기 방지
    // 쿠키기반 세션 유지 -> silent authentication 사용 -> ux 향상
    // 만료시간 적절히 설정 보안성과 사용자 경험 균형 유지
  }


  private String createRefreshToken() {
    return "refresh token";
  }

  private SystemKey createMockSystemKey() {
    return SystemKey.create(1L, "MWE5ZjZiOGMzZDdlNGYyYTVjNmQ5ZTBiN2E0ZjNjMmUK",
        LocalDateTime.now(), true);
  }

}