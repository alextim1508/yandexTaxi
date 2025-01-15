package com.alextim.yandextaxi.client;

import com.alextim.yandextaxi.model.Price;
import io.micrometer.core.annotation.Timed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "yandexclient", url = "${yandex.url}")
public interface TaxiApiClient {

    @Timed("gettingPriceFromYandex")
    @GetMapping
    Price getPrice(@RequestParam String clid, @RequestParam String apikey, @RequestParam String rll);
}
