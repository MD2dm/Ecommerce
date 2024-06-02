package com.example.Ecommerce.dto.SellerDto.Response.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter@Setter
public class ProductWithShopDTO implements Serializable {

    private Long id;

    private String shopName;

    private String productName;

    private List<String> fileUrls;

    private Double price;

    private Set<String> colors;

    private Set<String> sizes;

    private Integer stock;

    private String category;

    private String description;
}
