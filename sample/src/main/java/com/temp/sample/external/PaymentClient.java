package com.temp.sample.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class PaymentClient {

    private final RestClient restClient;


    public PaymentClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.pgserver.com")
                .build();
    }

    public PaymentResponse callPayment(PaymentRequest paymentRequest) {
        return restClient.post()
            .uri("/payments")
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) ->{
                    log.warn("실패 : {}" , res.getStatusCode());
                }
            )
            .body(PaymentResponse.class);
    }


}
