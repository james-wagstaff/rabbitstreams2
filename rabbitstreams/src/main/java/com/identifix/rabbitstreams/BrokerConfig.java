package com.identifix.rabbitstreams;

import java.io.InputStream;
import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class BrokerConfig {

    @Bean
    public Supplier<Flux<String>> pongProducer() {
        return () -> Flux.interval(Duration.ofSeconds(5)).map(value -> "Pong").log();
    }

    @Bean
    public Consumer<String> pingConsumer() {
        return (value) -> log.info("Consumer Received : " + value);
    }

    @Bean
    public Function<Flux<byte[]>, Flux<String>> pingProcessor(){
        return longFlux -> longFlux
            .map(i -> readFile(i))
            .log();
    }

    @SneakyThrows
    private String readFile(byte[] i) {
        return new String(i);
    }

    private String sendResponse(String str) {
        return "My pong was %sed".formatted(str);
    }

}
