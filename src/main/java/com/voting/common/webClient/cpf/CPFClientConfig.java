package com.voting.common.webClient.cpf;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Getter
public class CPFClientConfig {

    @Value("${cpfEndpoint.url}")
    private String url;

    @Value("${cpfEndpoint.timeout}")
    private Long timeout;

    @Value("${cpfEndpoint.retries}")
    private Long maxRetries;

    @Bean
    public WebClient getApiBaseClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(this.url)
                .filter(this.logRequest())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{} = {}", name, value)));
            return next.exchange(clientRequest);
        };
    }

}
