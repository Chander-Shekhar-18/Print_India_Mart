package com.zaptas.printindiamart.seller;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.util.Methods;

public class SellerProfile extends AppCompatActivity {
TextView name,designation,location,mobileNumber,gender,email,companyname,noempl,btype,anual,ownership,gst,tan,pann,cin,dgft;
    ImageView profile;
    static  String user_img;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayShowHomeEnabled(true);


        name=(TextView) findViewById(R.id.name);
        cin=(TextView) findViewById(R.id.cin);
        pann=(TextView) findViewById(R.id.pann);
        dgft=(TextView) findViewById(R.id.dgft);
        profile=(ImageView) findViewById(R.id.profile);
        location=(TextView) findViewById(R.id.location);
        gst=(TextView) findViewById(R.id.gst);
        mobileNumber=(TextView) findViewById(R.id.mobileNumber);
        anual=(TextView) findViewById(R.id.anual);
        tan=(TextView) findViewById(R.id.tan);
        companyname=(TextView) findViewById(R.id.companyname);
        email=(TextView) findViewById(R.id.email);
        noempl=(TextView) findViewById(R.id.noempl);
        btype=(TextView) findViewById(R.id.btype);
        ownership=(TextView) findViewById(R.id.ownership);
     //   gender=(TextView) findViewById(R.id.gender);
        designation=(TextView) findViewById(R.id.designation);
name.setText(Methods.getUSERNAME(this));
        location.setText(Methods.getAdress(this)+","+Methods.getCity(this));
        email.setText(Methods.getEmailID(this));

        designation.setText(Methods.getComapny(this));
        companyname.setText(Methods.getComapny(this));
        pann.setText(Methods.getpan(this));
        cin.setText(Methods.getcin(this));
        gst.setText(Methods.getgst(this));
        dgft.setText(Methods.getdghft(this));
        btype.setText(Methods.getbtype(this));
        tan.setText(Methods.gettan(this));
        noempl.setText(Methods.getnemp(this));
        anual.setText(Methods.getanualturn(this));
        ownership.setText(Methods.getownertype(this));
        user_img=Methods.getUserIMG(this);
        mobileNumber.setText(Methods.getMobileNo(this));
        Picasso.get().load("https://printindiamart.com/public/upload_files/user/" + user_img).into(profile);


    }

    public void profileupdate(View arg){
        Intent intent = new Intent(SellerProfile.this, Profile_Update.class);
        intent.putExtra("firstname",  Methods.getUSERNAME(this));
        intent.putExtra("lastname",  Methods.getLUSERNAME(this));
        intent.putExtra("mobileno",  mobileNumber.getText().toString());
        intent.putExtra("comapnyname",  companyname.getText().toString());
        intent.putExtra("pincode",  Methods.getPinCode(this));
        intent.putExtra("city",  Methods.getCity(this));
        intent.putExtra("adress",  Methods.getAdress(this));
        intent.putExtra("nemployee",  noempl.getText().toString());
        intent.putExtra("annualturnover",  anual.getText().toString());
        intent.putExtra("ownershiptype",  ownership.getText().toString());
        intent.putExtra("gst",  gst.getText().toString());
        intent.putExtra("tan",  tan.getText().toString());
        intent.putExtra("pan",  pann.getText().toString());
        intent.putExtra("cin",  cin.getText().toString());
        intent.putExtra("dgft",  dgft.getText().toString());

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
