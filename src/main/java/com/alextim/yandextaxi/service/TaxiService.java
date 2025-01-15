package com.alextim.yandextaxi.service;

import com.alextim.yandextaxi.client.TaxiApiClient;
import com.alextim.yandextaxi.model.Coordinate;
import com.alextim.yandextaxi.model.MomentPrice;
import com.alextim.yandextaxi.model.Price;
import com.alextim.yandextaxi.properties.YandexProperty;
import com.alextim.yandextaxi.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaxiService {

    private final YandexProperty yandexProperty;
    private final TaxiApiClient taxiApiClient;
    private final PriceRepository priceRepository;
    private AtomicInteger price;

    public TaxiService(YandexProperty yandexProperty,
                       TaxiApiClient taxiApiClient,
                       PriceRepository priceRepository,
                       MeterRegistry meterRegistry) {
        this.yandexProperty = yandexProperty;
        this.taxiApiClient = taxiApiClient;
        this.priceRepository = priceRepository;

        price = new AtomicInteger();
        meterRegistry.gauge("price", price);
    }

    public MomentPrice getPrice(Coordinate startPoint, Coordinate finishPoint) {
        Price curPrice = taxiApiClient.getPrice(
                yandexProperty.getClid(),
                yandexProperty.getApiKey(),
                startPoint + "~" + finishPoint);

        if (curPrice.getOptions().isEmpty())
            throw new RuntimeException("Options are empty");

        double price = curPrice.getOptions().get(0).getPrice();
        this.price.set((int) price);

        return new MomentPrice(
                LocalDateTime.now(ZoneId.of("Europe/Moscow")),
                price);
    }

    public void savePrice(MomentPrice momentPrice) {
        priceRepository.save(momentPrice);
    }

    public List<MomentPrice> getAllPrices() {
        return priceRepository.findAll();
    }

}
