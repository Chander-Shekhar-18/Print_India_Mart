package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Dashboard_Seller extends AppCompatActivity {
    TextView package1, prd, lead;
    private static Response response;
    static String se_id, lead12, product, packagename, time, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__seller);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        package1 = (TextView) findViewById(R.id.package1);
        prd = (TextView) findViewById(R.id.prd);
        lead = (TextView) findViewById(R.id.lead);


        se_id = Methods.getUSERID(this);
        if (Methods.getabout(this).equals("0")) {
            Intent intent = new Intent(Dashboard_Seller.this, Purchase_Package.class);
            startActivity(intent);
        }
        new GetData_Viewall().execute();


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.becomeSeller);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNavigation:
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        return true;

                    case R.id.myAccount:
                        Intent intent = new Intent(getApplicationContext(), User_Mobile.class);
                        startActivity(intent);
                        finish();
                        return true;

                    case R.id.becomeSeller:
                       return true;

                    default:
                        return false;
                }
            }
        });
    }

    class GetData_Viewall extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Dashboard_Seller.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = getDataFromWeb();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj =jsonObject.getJSONObject("data");
                        // JSONArray array = jsonObject.getJSONObject("leads");
                        lead12 = jsonObject.getString("total_leads");
                        product = jsonObject.getString("total_products");
                        packagename = jsonObject.getString("package_name");
                        time = jsonObject.getString("package_time");
                        price = jsonObject.getString("package_price");

                    }

                } else {
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            lead.setText("Lead \n" + lead12);
            prd.setText("Product \n" + product);
            package1.setText("Package \n" + packagename + "\n" + time + "\n $" + price);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Dashboard_Seller.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutus) {
            Intent intent = new Intent(Dashboard_Seller.this, SellerAbout_Us.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.package11) {
            Intent intent = new Intent(Dashboard_Seller.this, Purchase_Package.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.changepassword) {
            Intent intent = new Intent(Dashboard_Seller.this, Change_Password.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.profile) {
            Intent intent = new Intent(Dashboard_Seller.this, SellerProfile.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.logo) {
            Intent intent = new Intent(Dashboard_Seller.this, Seller_Add.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.banner) {
            Intent intent = new Intent(Dashboard_Seller.this, Seller_Banner.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.home) {
            Intent intent = new Intent(Dashboard_Seller.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.logout) {
            Methods.saveUSERTYPE(Dashboard_Seller.this, "zap");
            Methods.saveUSERID(Dashboard_Seller.this, null);
            Intent intent = new Intent(Dashboard_Seller.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("https://printindiamart.com/public/api/seller_leads/" + se_id)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public void home(View arg) {
        Intent intent = new Intent(Dashboard_Seller.this, MainActivity.class);
        startActivity(intent);
    }

    public void logout(View arg) {

    }

    public void addproduct(View arg) {
        Intent intent = new Intent(Dashboard_Seller.this, AddProduct.class);
        startActivity(intent);
    }

    public void logo(View arg) {

    }

    public void banner(View arg) {

    }

    public void change_password(View arg) {

    }

    public void manageproduct(View arg) {
        Intent intent = new Intent(Dashboard_Seller.this, ManageProduct.class);
        startActivity(intent);
    }

    public void profile(View arg) {
        Intent intent = new Intent(Dashboard_Seller.this, SellerProfile.class);
        startActivity(intent);
    }

    public void lead(View arg) {
        Intent intent = new Intent(Dashboard_Seller.this, SellerLead.class);
        startActivity(intent);
    }
    //@Override
   /* public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }*/
}
