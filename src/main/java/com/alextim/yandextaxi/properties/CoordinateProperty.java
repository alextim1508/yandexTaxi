package com.alextim.yandextaxi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "coordinate")
public class CoordinateProperty {
    private double startLatitude;
    private Double startLongitude;
    private Double finishLatitude;
    private Double finishLongitude;
}
