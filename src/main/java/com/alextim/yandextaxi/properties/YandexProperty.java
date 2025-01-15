package com.alextim.yandextaxi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yandex")
public class YandexProperty {
    private String clid;
    private String apiKey;
}
