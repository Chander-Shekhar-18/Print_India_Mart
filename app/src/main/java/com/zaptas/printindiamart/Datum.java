package com.zaptas.printindiamart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


    @SerializedName("product_id")
    @Expose
    private int productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_qunatity")
    @Expose
    private String productQunatity;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("product_amount")
    @Expose
    private String productAmount;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("seller_phone")
    @Expose
    private String sellerPhone;
    @SerializedName("product_image")
    @Expose
    private String productImage;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQunatity() {
        return productQunatity;
    }

    public void setProductQunatity(String productQunatity) {
        this.productQunatity = productQunatity;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
