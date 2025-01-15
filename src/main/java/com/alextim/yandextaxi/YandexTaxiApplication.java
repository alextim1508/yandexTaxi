package com.alextim.yandextaxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@EnableJpaRepositories
@EnableConfigurationProperties
@SpringBootApplication
public class YandexTaxiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YandexTaxiApplication.class, args);
    }

}
