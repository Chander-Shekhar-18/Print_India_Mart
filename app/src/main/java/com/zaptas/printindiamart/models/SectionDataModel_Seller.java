package com.zaptas.printindiamart.models;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionDataModel_Seller {



    private String headerTitle;
    private String headerId;
    private String headerType;
    private String quantity;
    private String ammount;
    private String description;
    private String category;
    private String subcategory;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SectionDataModel_Seller() {

    }
    public SectionDataModel_Seller(String headerTitle, String headerId, String headerType,String quantity,String ammount,String description,String category,String subcategory, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.headerId = headerId;
        this.headerType = headerType;
        this.quantity = quantity;
        this.ammount = ammount;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderId() {
        return headerId;
    }
    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getheaderType() {
        return headerType;
    }
    public void setheaderType(String headerType) {
        this.headerType = headerType;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


    public String getquantity() {
        return quantity;
    }
    public void setquantity(String quantity) {
        this.quantity = quantity;
    }
    public String getammount() {
        return ammount;
    }
    public void setammount(String ammount) {
        this.ammount = ammount;
    }
    public String getdescription() {
        return description;
    }
    public void setdescription(String description) {
        this.description = description;
    }

    public String getcategory() {
        return category;
    }
    public void setcategory(String category) {
        this.category = category;
    }

    public String getsubcategory() {
        return subcategory;
    }
    public void setsubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

}
