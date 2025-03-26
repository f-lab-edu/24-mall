package com.temp.sample.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class ImageClient {

  private final RestClient restClient;

  public ImageClient() {
    this.restClient = RestClient.builder()
        .baseUrl("https://api.image-server.com")
        .build();
  }

  public ImageResponse uploadImage(ImageRequest paymentRequest) {
    return restClient.post()
        .uri("/images")
        .retrieve()
        .onStatus(HttpStatusCode::isError, (req, res) ->{
              log.warn("실패 : {}" , res.getStatusCode());
            }
        )
        .body(ImageResponse.class);
  }

  public ImageResponse modifyImage(ImageRequest paymentRequest) {
    return restClient.patch()
        .uri("/images")
        .retrieve()
        .onStatus(HttpStatusCode::isError, (req, res) ->{
              log.warn("실패 : {}" , res.getStatusCode());
            }
        )
        .body(ImageResponse.class);
  }

  public ImageResponse deleteImage(ImageRequest paymentRequest) {
    return restClient.delete()
        .uri("/images")
        .retrieve()
        .onStatus(HttpStatusCode::isError, (req, res) ->{
              log.warn("실패 : {}" , res.getStatusCode());
            }
        )
        .body(ImageResponse.class);
  }
}
