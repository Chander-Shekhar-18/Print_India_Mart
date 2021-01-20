package com.zaptas.printindiamart.models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class SingleItemModel {


    private String st_subcategory_id;
    private String st_name;
    private String st_cname;
    private String st_decription;
    private String st_imgfile;
    private String st_status;
    private String st_subcategory_type;
    private String product_user_n;




    private String st_cat_data;
    private String st_cat_id;



    public SingleItemModel() {
    }

    public SingleItemModel(String st_subcategory_id, String st_name, String st_cname, String st_decription, String st_imgfile, String st_status, String st_subcategory_type, String st_cat_data, String st_cat_id,String product_user_n) {

        this.st_subcategory_id = st_subcategory_id;
        this.st_name = st_name;
        this.st_cname= st_cname;
        this.product_user_n= product_user_n;
        this.st_decription = st_decription;
        this.st_imgfile=st_imgfile;
        this.st_status=st_status;
        this.st_subcategory_type=st_subcategory_type;


        this.st_cat_data=st_cat_data;
        this.st_cat_id=st_cat_id;


    }
    public String getproduct_user_n() {
        return product_user_n;
    }
    public void setproduct_user_n(String product_user_n) {
        this.product_user_n = product_user_n;
    }

    public String getSt_subcategory_id() {
        return st_subcategory_id;
    }
    public void setSt_subcategory_id(String st_subcategory_id) {
        this.st_subcategory_id = st_subcategory_id;
    }

    public String getSt_name() {
        return st_name;
    }
    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }


    public String getSt_cname() {
        return st_cname;
    }
    public void setSt_cname(String st_cname) {
        this.st_cname = st_cname;
    }


    public String getSt_decription() {
        return st_decription;
    }

    public void setSt_decription(String st_decription) {
        this.st_decription = st_decription;
    }

    public String getSt_imgfile() {
        return st_imgfile;
    }

    public void setSt_imgfile(String st_imgfile) {
        this.st_imgfile = st_imgfile;
    }

    public String getSt_status() {
        return st_status;
    }

    public void setSt_status(String st_status) {
        this.st_status = st_status;
    }


    public String getSt_subcategory_type() {
        return st_subcategory_type;
    }

    public void setSt_subcategory_type(String st_subcategory_type) {
        this.st_subcategory_type = st_subcategory_type;
    }







    public String getSt_cat_data() {
        return st_cat_data;
    }

    public void setSt_cat_data(String st_cat_data) {
        this.st_cat_data = st_cat_data;
    }

    public String getSt_cat_id() {
        return st_cat_id;
    }

    public void setSt_cat_id(String st_cat_id) {
        this.st_cat_id = st_cat_id;
    }

}
