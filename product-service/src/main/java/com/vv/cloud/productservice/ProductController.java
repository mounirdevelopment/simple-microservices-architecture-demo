package com.vv.cloud.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private static List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

    @Autowired
    private RestTemplate restTemplate;

    static {
        productInfoList.add(new ProductInfo(101L, "Iphone", "Iphone is great"));
        productInfoList.add(new ProductInfo(102L, "Book", "Books is great"));
        productInfoList.add(new ProductInfo(103L, "Watch", "Watch is great"));
    }

    @GetMapping("/product/details/{id}")
    public Product getProductDetails(@PathVariable Long id) {
        //get product price
        Price price = restTemplate.getForObject("http://localhost:9002/price/" + id, Price.class);
        //get product stock
        Inventory inventory = restTemplate.getForObject("http://localhost:9003/inventory/" + id, Inventory.class);
        //get product info
        ProductInfo productInfo = getProductInfo(id);
        return new Product(productInfo.getProductId(),
                productInfo.getProductName(),
                productInfo.getProductDesc(),
                price.getDicountedPrice(),
                inventory.isProductStock());
    }

    private ProductInfo getProductInfo(Long id) {
        return productInfoList.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
