package com.vv.cloud.productservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InventoryController {

    private static List<Inventory> inventoryList = new ArrayList<Inventory>();

    static {
        inventoryList.add(new Inventory(301L, 101L, true));
        inventoryList.add(new Inventory(302L, 102L, true));
        inventoryList.add(new Inventory(303L, 103L, false));
    }

    @GetMapping("/inventory/{productId}")
    public Inventory getProductDetails(@PathVariable Long productId) {
        Inventory inventory = getInventoryInfo(productId);
        return new Inventory(inventory.getProductId(),
                inventory.getInventoryId(),
                inventory.isProductStock());
    }

    private Inventory getInventoryInfo(Long productId) {
        return inventoryList.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
