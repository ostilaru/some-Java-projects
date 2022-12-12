package com.example.demo;

import java.sql.Date;

public class productData {

    private String productName;
    private String productType;
    private int productQuantity;
    private String produceDate;
    private int guaranteePeriod;
    private int productPrice;

    public productData(String productName, String productType, int productQuantity, String produceDate, int guaranteePeriod, int productPrice){
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.produceDate = produceDate;
        this.guaranteePeriod = guaranteePeriod;
        this.productPrice = productPrice;
    }


    public productData() {
    }


    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductQuantity() {
        // return this.productQuantity;
        return Integer.toString(this.productQuantity);
    }

    public String getProduceDate() {
        return produceDate.toString();
    }

    public String getGuaranteePeriod() {
        return Integer.toString(this.guaranteePeriod);
    }

    public String getProductPrice() {
        return Integer.toString(this.productPrice);
    }


}
