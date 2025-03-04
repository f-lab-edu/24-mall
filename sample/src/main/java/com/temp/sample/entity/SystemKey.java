package com.temp.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.*;

@Table(name = "system_key")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemKey {

  @Id
  private Long id;

  private String encKey;

  private LocalDateTime createdAt;

  private Boolean isActive;


  public static SystemKey createMock(Long id, String encKey, LocalDateTime createdAt, Boolean isActive) {
    SystemKey systemKey = new SystemKey();
    systemKey.id = id;
    systemKey.encKey = encKey;
    systemKey.createdAt = createdAt;
    systemKey.isActive = isActive;
    return systemKey;
  }

}
