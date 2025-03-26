package com.temp.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "image_info")
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String url;


  public static ImageInfo create(String name, String url) {
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.name = name;
    imageInfo.url = url;
    return imageInfo;
  }

  public static ImageInfo createMock(Long id, String name, String url) {
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.id = id;
    imageInfo.name = name;
    imageInfo.url = url;
    return imageInfo;
  }
}
