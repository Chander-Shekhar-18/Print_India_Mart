package com.zaptas.printindiamart;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zaptas.printindiamart.util.Methods;

public class Search_Activity extends AppCompatActivity {
    EditText serch_value;
    ImageView iv_back;
    static String value, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        serch_value = (EditText) findViewById(R.id.search);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        user_id = Methods.getUSERID(this);
        if (user_id == null) {
            Intent intent = new Intent(Search_Activity.this, User_Mobile.class);
            startActivity(intent);
        }
    }

    public void submit(View arg) {
        value = serch_value.getText().toString();
        if (value.equals("")) {
            Toast.makeText(getApplicationContext(), "All feild mandatory. ", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(Search_Activity.this, Search_Product.class);
            intent.putExtra("pidd", value);
            startActivity(intent);
            serch_value.setText("");
        }

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
