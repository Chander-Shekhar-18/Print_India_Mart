package com.zaptas.printindiamart.seller;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.util.Methods;

public class Seller_Add extends AppCompatActivity {
    private Handler mHandler = new Handler();
    static  String meth_cate_idd,  meth_cate_name;
    private Context mContext;
    ImageView img;
    static  String imgpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        img=(ImageView) findViewById(R.id.logo);

        setTitle("Logo");

        System.out.println("jggjhfgjh"+meth_cate_idd+meth_cate_name);


        imgpath=Methods.getLogo(this);
       Picasso.get().load("https://printindiamart.com/public/subdomain/schon/photo/"+imgpath).into(img);



    }
    public void update(View arg){
        Intent intent = new Intent(Seller_Add.this, Update_Logo.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


}
