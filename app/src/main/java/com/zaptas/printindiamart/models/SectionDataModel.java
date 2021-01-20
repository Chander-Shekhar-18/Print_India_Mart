package com.zaptas.printindiamart.models;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionDataModel {



    private String headerTitle;
    private String headerId;
    private String headerType;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, String headerId, String headerType, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.headerId = headerId;
        this.headerType = headerType;
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


}
