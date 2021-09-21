package com.sapient.inventory.reactive.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private static final String LOGGER_MESSAGE = "Request Came for :: URL {} Method {}";
    private final WebClient webClient;

    public <T> T getResponseEntityMono(String uri, HttpMethod method, HttpEntity<?> entity, Class<T> responseType) {
        log.info(LOGGER_MESSAGE, uri, method.name());
        try {
            return (T) webClient.method(method)
                    .uri(uri)
                    .headers(headers -> headers.addAll(entity.getHeaders()))
                    .bodyValue(entity.getBody())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {

        }
        return null;
    }
}