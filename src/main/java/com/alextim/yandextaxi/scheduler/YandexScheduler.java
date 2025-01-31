package com.alextim.yandextaxi.scheduler;

import com.alextim.yandextaxi.model.Coordinate;
import com.alextim.yandextaxi.model.MomentPrice;
import com.alextim.yandextaxi.properties.CoordinateProperty;
import com.alextim.yandextaxi.service.TaxiService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class YandexScheduler {

    private final CoordinateProperty coordinateProperty;

    private final TaxiService taxiService;

    @Timed("schedulerTaxi")
    @Scheduled(fixedDelay = 30_000)
    public void updatePrice() {
        log.info("updatePrice");
        Coordinate startPoint = new Coordinate(
                coordinateProperty.getStartLatitude(), coordinateProperty.getStartLongitude());

        Coordinate finishPoint = new Coordinate(
                coordinateProperty.getFinishLatitude(), coordinateProperty.getFinishLongitude());

        MomentPrice price = taxiService.getPrice(startPoint, finishPoint);

        MomentPrice savedMomentPrice = taxiService.savePrice(price);
        log.info("savedMomentPrice {}", savedMomentPrice);
    }
}
