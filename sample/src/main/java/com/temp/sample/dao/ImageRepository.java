package com.temp.sample.dao;

import com.temp.sample.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageInfo, Long> {

}
