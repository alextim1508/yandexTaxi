package com.alextim.yandextaxi.service;

import com.alextim.yandextaxi.client.TaxiApiClient;
import com.alextim.yandextaxi.model.Coordinate;
import com.alextim.yandextaxi.model.MomentPrice;
import com.alextim.yandextaxi.model.Price;
import com.alextim.yandextaxi.properties.YandexProperty;
import com.alextim.yandextaxi.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TaxiService {

    private final YandexProperty yandexProperty;
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;
    private AtomicInteger mectricValue;

    public TaxiService(YandexProperty yandexProperty,
                       TaxiApiClient taxiApiClient,
                       PriceRepository priceRepository,
                       MeterRegistry meterRegistry) {
        this.yandexProperty = yandexProperty;
        this.taxiApiClient = taxiApiClient;
        this.priceRepository = priceRepository;

        mectricValue = new AtomicInteger();
        meterRegistry.gauge("priceTaxi", mectricValue);
    }

    public MomentPrice getPrice(Coordinate startPoint, Coordinate finishPoint) {
        Price curPrice = taxiApiClient.getPrice(
                yandexProperty.getClid(),
                yandexProperty.getApiKey(),
                startPoint + "~" + finishPoint);

        log.info("Current price: {}", curPrice);

        if (curPrice.getOptions().isEmpty())
            throw new RuntimeException("Options are empty");

        double price = curPrice.getOptions().get(0).getPrice();
        log.info("price: {}", price);

        mectricValue.set((int) price);

        return new MomentPrice(
                LocalDateTime.now(ZoneId.of("Europe/Moscow")),
                price);
    }

    public MomentPrice savePrice(MomentPrice momentPrice) {
        return priceRepository.save(momentPrice);
    }

    public List<MomentPrice> getAllPrices() {
        return priceRepository.findAll();
    }

}
