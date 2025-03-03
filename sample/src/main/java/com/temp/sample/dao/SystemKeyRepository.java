package com.temp.sample.dao;

import com.temp.sample.entity.SystemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SystemKeyRepository extends JpaRepository<SystemKey, Long> {

  @Query(value = "select *"
      + "from secret_key"
      + "order by created_at deac"
      + "limit 1"
      , nativeQuery = true)
  SystemKey findLastSecretKey();
}
