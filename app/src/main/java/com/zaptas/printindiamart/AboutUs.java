package com.zaptas.printindiamart;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        final Drawable upArrow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);
        ;
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.printindiafulllogo)
                .setDescription("PrintIndiaMart is online B2B marketplace, connecting buyers with suppliers.You can sell your printing related product such as  UV PRINTING, LETTER PRESS PRINTING, FLEX PRINTING and etc. PrintIndiaMart promote only printing business and provide Wider marketplace with a range of products and suppliers.  PrintIndiaMart is a platform where buyers can connect with sellers anytime through mail and messages and can share their requirements.PrintIndiaMart helps you to grow your business. This platform will not only increase your credibility but also help you to increase your Brand credibility.")
                .addFacebook("https://www.facebook.com/ohmanykart.web/")
                .addYoutube("https://www.youtube.com/channel/UCwxqagn3J5Zs1sfIyLvN9_g")
                .addInstagram("https://www.instagram.com/ohmanykart/")
                .create();
        setContentView(aboutPage);

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
