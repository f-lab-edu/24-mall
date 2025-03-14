package com.temp.sample.external;

import com.temp.sample.entity.OrderInfo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import org.yaml.snakeyaml.constructor.Construct;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class PaymentClient {

    private final RestClient restClient;


    public PaymentClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.pgserver.com")
                .build();
    }

    public PaymentResponse callPayment(OrderInfo orderInfo) {

        try{
             return restClient.post()
                    .uri("/payments")
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) ->{
                        log.warn("실패 : {}" , res.getStatusCode());
                            }
                    )
                    .body(PaymentResponse.class);

        } catch (HttpClientErrorException e) {
            // 4xx 에러 처리 (클라이언트 요청 오류)
            System.err.println("PG API Client Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        } catch (HttpServerErrorException e) {
            // 5xx 에러 처리 (서버 내부 오류)
            System.err.println("PG API Server Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;

        } catch (UnknownHttpStatusCodeException e) {
            // 알 수 없는 HTTP 상태 코드 응답 처리
            System.err.println("PG API Unknown Error: " + e.getMessage());
            throw e;

        } catch (RestClientException e) {
            // RestClientException: 네트워크 장애, 타임아웃 등 예외 처리
            if (e.getCause() instanceof ConnectException || e.getCause() instanceof UnknownHostException) {
                // PG 서버가 다운되었거나 DNS 문제로 인해 연결할 수 없는 경우
                System.err.println("PG 서버 연결 실패: " + e.getMessage());
            } else if (e.getCause() instanceof TimeoutException) {
                // PG API 응답이 5초 이상 걸려 타임아웃 발생한 경우
                System.err.println("PG API 응답 시간 초과");
            } else {
                // 기타 알 수 없는 오류 발생
                System.err.println("PG API 호출 중 알 수 없는 오류 발생: " + e.getMessage());
            }
            throw e;
        }
    }


}
