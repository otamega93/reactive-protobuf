package com.example.reactiveprotobuf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class ReactiveProtobufConfig {

    @Bean
    public WebFluxConfigurer configurer() {

        return new WebFluxConfigurer() {
            @Override
            public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {

                configurer.registerDefaults(true);
                configurer.customCodecs().register(new ProtobufDecoder());
                configurer.customCodecs().register(new ProtobufEncoder());

            }
        };
    }
}
