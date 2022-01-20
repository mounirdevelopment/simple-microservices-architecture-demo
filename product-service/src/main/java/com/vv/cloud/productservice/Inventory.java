package com.vv.cloud.productservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Long inventoryId;
    private Long productId;
    private boolean productStock;

}
