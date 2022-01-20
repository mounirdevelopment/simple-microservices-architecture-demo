package com.vv.cloud.productservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PriceController {

    private static List<Price> priceList = new ArrayList<Price>();

    static {
        priceList.add(new Price(201L, 101L, 999f, 499f));
        priceList.add(new Price(202L, 102L, 99f, 49f));
        priceList.add(new Price(203L, 103L, 699f, 299f));
    }

    @GetMapping("/price/{productId}")
    public Price getPriceDetails(@PathVariable Long productId) {
        Price price = getPriceInfo(productId);
        return new Price(price.getPriceId(),
                price.getProductId(),
                price.getOriginalPrice(),
                price.getDicountedPrice());
    }

    private Price getPriceInfo(Long productId) {
        return priceList.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
