package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.seller.Dashboard_Seller;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class User_Mobile extends AppCompatActivity {
    EditText mobileno;
    private static Response response1;
    static String number;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__mobile);
     /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        mobileno = (EditText) findViewById(R.id.mobileno);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homeNavigation);
        bottomNavigationView.setSelectedItemId(R.id.myAccount);
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
                        return true;

                    case R.id.becomeSeller:
                        usertype = Methods.getUSERTYPE(getApplicationContext());
                        user_id = Methods.getUSERID(getApplicationContext());
                        if (user_id == null) {
                            Intent intent2 = new Intent(User_Mobile.this, SellerLogin.class);
                            startActivity(intent2);
                            finish();
                        } else {
                            if (usertype.equals("seller")) {
                                Intent intent3 = new Intent(User_Mobile.this, Dashboard_Seller.class);
                                startActivity(intent3);
                                finish();
                            }
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    public void submit(View arg) {
        number = mobileno.getText().toString();
        if (number.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter mobile no.", Toast.LENGTH_LONG).show();
        } else {
            new GetCategorySpinner().execute();
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

    class GetCategorySpinner extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((User_Mobile.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = User_Mobile.getDataFromWeb1();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj=jsonObject.getJSONObject("msg");

                        String user_id = jsonObject.getString("data");
                        Methods.saveUSERTYPE(User_Mobile.this, "user");
                        Methods.saveUSERID(User_Mobile.this, user_id);
                        Intent intent = new Intent(User_Mobile.this, MainActivity.class);

                        startActivity(intent);
                        finish();

                    }
                } else {

                }
            } catch (JSONException je) {
                // Log.i(MainActivity.TAG, "" + je.getLocalizedMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();


        }
    }

    public static JSONObject getDataFromWeb1() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/user_mobile/" + number)
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
