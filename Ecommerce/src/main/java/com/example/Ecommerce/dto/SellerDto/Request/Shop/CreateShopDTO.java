package com.example.Ecommerce.dto.SellerDto.Request.Shop;


import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CreateShopDTO implements Serializable {

    private String shopName;

    private String shopAddress;

    private String shopPhone;

}
