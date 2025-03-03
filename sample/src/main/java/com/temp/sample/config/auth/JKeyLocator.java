package com.temp.sample.config.auth;

import com.temp.sample.dao.SystemKeyRepository;
import com.temp.sample.entity.SystemKey;
import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JKeyLocator extends LocatorAdapter<Key> {

  private final SystemKeyRepository systemKeyRepository;

  @Override
  protected Key locate(ProtectedHeader header) {

    // lookupKey(keyId) 는 키를 데이터베이스(DB), 키 저장소(Keystore), HSM(Hardware Security Module)
    // 등에서 조회하는 메서드 로 직접 구현해야 한다.
    //JWS 서명 검증 시,  대칭키[HMAC (HS256, HS384, HS512)]는 SecretKey를 리턴해야한다.
    // HSM은 provider 방식이 있음.

    String keyId = header.getKeyId();

    SystemKey systemKey = systemKeyRepository.findById(Long.valueOf(keyId)).orElseThrow();

    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(systemKey.getEncKey()));

  }


}
