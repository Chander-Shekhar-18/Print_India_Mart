package com.zaptas.printindiamart;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zaptas.printindiamart.util.Methods;

public class SellerAbout_Us extends AppCompatActivity {
TextView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_about__us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        about=(TextView) findViewById(R.id.about);
        about.setText(Methods.getabout(this));
    }
    public void update(View arg){

        Intent i = new Intent(getApplicationContext(), AboutUpdate.class);
        i.putExtra("pidd", Methods.getabout(this));
   startActivity(i);
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
