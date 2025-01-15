package com.alextim.yandextaxi.scheduler;

import com.alextim.yandextaxi.model.Coordinate;
import com.alextim.yandextaxi.model.MomentPrice;
import com.alextim.yandextaxi.properties.CoordinateProperty;
import com.alextim.yandextaxi.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class YandexScheduler {

    private final CoordinateProperty coordinateProperty;

    private final TaxiService taxiService;

    @Scheduled(fixedDelay = 10_000)
    public void updatePrice() {
        Coordinate startPoint = new Coordinate(
                coordinateProperty.getStartLatitude(), coordinateProperty.getStartLongitude());

        Coordinate finishPoint = new Coordinate(
                coordinateProperty.getFinishLatitude(), coordinateProperty.getFinishLongitude());

        MomentPrice price = taxiService.getPrice(startPoint, finishPoint);

        taxiService.savePrice(price);
    }
}
