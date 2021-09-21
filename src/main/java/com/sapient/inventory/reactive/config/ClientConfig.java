package com.sapient.inventory.reactive.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ReactorResourceFactory resourceFactory() {
        return getResourceFactory("WEBCLIENT-CONNECTION-POOL", 20, 20000);
    }

    @Bean
    public WebClient getWebClient() {
        return getWebClient(resourceFactory(), 5000, 5000);
    }

    public WebClient getWebClient(ReactorResourceFactory resourceFactory, int connectTimeout, int readTimeout) {

        log.info("getWebClient():: Connect Timeout : {} Read Timeout : {} ", connectTimeout, readTimeout);
        HttpClient httpClient = HttpClient.create(resourceFactory.getConnectionProvider())
                .runOn(resourceFactory.getLoopResources())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(readTimeout / 1000)));
        ClientHttpConnector connector =
                new ReactorClientHttpConnector(httpClient);
        return WebClient.builder().clientConnector(connector).build();
    }

    public ReactorResourceFactory getResourceFactory(String poolName, int maxConnection, int maxIdleTime) {
        log.info("GetResourceFactory():: Pool Name : {} Max Connection : {} Max Idle Time : {}", poolName, maxConnection, maxIdleTime);
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setConnectionProvider(ConnectionProvider.builder(poolName).maxConnections(maxConnection)
                .maxIdleTime(Duration.ofSeconds(maxIdleTime / 1000))
                .build());
        factory.setUseGlobalResources(false);
        return factory;
    }

}
