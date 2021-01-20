package com.zaptas.printindiamart.seller;


import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zaptas.printindiamart.R;

public class AboutUpdate extends AppCompatActivity {
String aboutt;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_update);
        edt=(EditText) findViewById(R.id.about);
        Bundle bundle = getIntent().getExtras();
        aboutt = bundle.getString("pidd");
        edt.setText(aboutt);
    }
}
