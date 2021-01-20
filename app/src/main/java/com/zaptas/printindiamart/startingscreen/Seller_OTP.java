package com.zaptas.printindiamart.startingscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.seller.Password_Update;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Seller_OTP extends AppCompatActivity {
    public static String emailid, otp_text, msg, error, id;
    EditText otp;
    JSONObject jsonObject;
    private static Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__otp);
        emailid = getIntent().getStringExtra("Emailid");
        otp = (EditText) findViewById(R.id.otp_tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    public void validate(View arg) {
        otp_text = otp.getText().toString();
        if (otp_text.equals("")) {
            Toast.makeText(getApplicationContext(), "All feild mandatory", Toast.LENGTH_SHORT).show();
        } else {
            otp asyncT = new otp();
            asyncT.execute();
        }
    }

    class otp extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        /*public void add_filcal(View arg){
            Intent inaction3 = new Intent(v.getContext(), FilCal_ParticularItem.class);
        }*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((Seller_OTP.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObject = getDataFromWeb();

            try {
                if (jsonObject != null) {
                    // JSONObject array = jsonObject.getJSONObject("data");
                    msg = jsonObject.getString("msg");
                    error = jsonObject.getString("error");
                    id = jsonObject.getString("data");

                } else {
                }
            } catch (JSONException je) {
                //  Log.i(ParticularImage.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (error.equals("false")) {
                Toast.makeText(getApplicationContext(), msg,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Seller_OTP.this, Password_Update.class);
                intent.putExtra("seeidd", id);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), msg,
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/ff12092018131049

                    .url("https://printindiamart.com/public/api/otp_verify/" + emailid + "/" + otp_text)
                    .build();

            response = client.newCall(request).execute();
            //  System.out.println("8878788787"+"http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/"+meth_username);
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
