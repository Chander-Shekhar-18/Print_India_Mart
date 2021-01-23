package com.zaptas.printindiamart.seller;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.zaptas.printindiamart.MainActivity;
import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.SellerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Password_Update extends AppCompatActivity {
    EditText old,newpass,confirmpass;
    static  String oldd,neww,confirmm,se_id_id,red,msg,error;
    private static Response response;
    JSONObject jsonObject;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__update);
        newpass= (EditText) findViewById(R.id.newpass);
        confirmpass= (EditText) findViewById(R.id.confirmpass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        se_id_id = bundle.getString("seeidd");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Password_Update.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    public void update(View arg){


        neww = newpass.getText().toString();
        confirmm = confirmpass.getText().toString();
        if( neww.equals("") || confirmm.equals("")){
            Toast.makeText(getApplicationContext(),"All feild mandatory",Toast.LENGTH_SHORT).show();
        }
        else {
            if(neww.equals(confirmm)){
                change_password asyncT = new change_password();
                asyncT.execute();
            }
            else {
                Toast.makeText(getApplicationContext(),"Password and Confirm Password do not match",Toast.LENGTH_SHORT).show();
            }

        }
    }




    class change_password extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        /*public void add_filcal(View arg){
            Intent inaction3 = new Intent(v.getContext(), FilCal_ParticularItem.class);
        }*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((Password_Update.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObject = getDataFromWeb();

            try {
                msg = jsonObject.getString("msg");
                error = jsonObject.getString("error");




            } catch (JSONException je) {
                //  Log.i(ParticularImage.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(error.equals("false")){
                Toast.makeText(getApplicationContext(), msg,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Password_Update.this, SellerLogin.class);
                startActivity(intent);
            }
            else {

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

                    .url("https://printindiamart.com/public/api/password_post/"+se_id_id+"/"+neww)
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
