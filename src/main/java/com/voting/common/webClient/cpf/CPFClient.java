package com.voting.common.webClient.cpf;

import com.voting.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CPFClient{

    private final CPFClientConfig cpfClientConfig;

    private <T> Mono<T> sendGetRequest( String cpf, Class<T> clazz) {
        try {
            return this.cpfClientConfig.getApiBaseClient().get()
                    .uri(uriBuilder -> uriBuilder
                            .path(cpf)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, clientError -> Mono.error(new InfrastructureException("CPF Inválido")))
                    .onStatus(HttpStatus::isError, clientError -> Mono.error(new InfrastructureException(clientError.logPrefix())))
                    .bodyToMono(clazz)
                    .retry(this.cpfClientConfig.getMaxRetries());
        } catch (Exception ex) {
            log.error("Error calling GET service! URI: {}", cpfClientConfig.getUrl(), ex);
            throw ex;
        }
    }

    public boolean isAbleToVote(String cpf){
        CPFResponse response = this.sendGetRequest(cpf, CPFResponse.class).block();
        return "ABLE_TO_VOTE".equalsIgnoreCase(Objects.requireNonNull(response).getStatus());
    }

}
