package com.zaptas.printindiamart;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zaptas.printindiamart.seller.Dashboard_Seller;
import com.zaptas.printindiamart.startingscreen.Seller_OTP;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class SellerRegistration extends AppCompatActivity {
    EditText fname, lname, company_namre, mobile, email;
    public static String firstname, lastname, company_name, mobile_no, email_id;
    String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    boolean check;
    Pattern p;
    Matcher m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fname = (EditText) findViewById(R.id.firstname);
        lname = (EditText) findViewById(R.id.lastname);
        company_namre = (EditText) findViewById(R.id.comapnyname);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.becomeSeller);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNavigation:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.myAccount:

                        Intent intent3 = new Intent(getApplicationContext(), User_Mobile.class);
                        startActivity(intent3);
                        return true;

                    case R.id.becomeSeller:
                        usertype = Methods.getUSERTYPE(getApplicationContext());
                        user_id = Methods.getUSERID(getApplicationContext());
                        if (user_id == null) {
                            Intent intent2 = new Intent(getApplicationContext(), SellerLogin.class);
                            startActivity(intent2);
                        } else {
                            if (usertype.equals("seller")) {
                                Intent intent4 = new Intent(getApplicationContext(), Dashboard_Seller.class);
                                startActivity(intent4);
                            }
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });
    }


    public void registered(View arg) {
        firstname = fname.getText().toString();
        lastname = lname.getText().toString();
        company_name = company_namre.getText().toString();
        mobile_no = mobile.getText().toString();
        email_id = email.getText().toString();
        if (firstname.equals("") || lastname.equals("") || company_name.equals("") || mobile_no.equals("") || email_id.equals("")) {
            Toast.makeText(getApplicationContext(), "All field mandatory", Toast.LENGTH_SHORT).show();
        } else {

            p = Pattern.compile(EMAIL_STRING);

            m = p.matcher(email_id);
            check = m.matches();

            if (!check) {
                Toast.makeText(getApplicationContext(), "Invalid email id.", Toast.LENGTH_SHORT).show();
            } else {
                new Registration().execute();
            }
        }


    }

    public class Registration extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(SellerRegistration.this);
            dialog.setMessage("please wait");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://printindiamart.com/public/api/sel_post"); // here is your URL path

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("First_name", firstname);
                postDataParams.put("Last_name", lastname);
                postDataParams.put("Email", email_id);
                postDataParams.put("Company_Name", company_name);
                postDataParams.put("Phone", mobile_no);
                postDataParams.put("privacy", "1");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {


                // return new String("Exception: " + e.getMessage());
                String gk = e.getMessage().toString();

                System.out.println("zinccccc" + gk);
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {


            super.onPostExecute(result);
            dialog.dismiss();
            Intent intent = new Intent(SellerRegistration.this, Seller_OTP.class);
            intent.putExtra("Emailid", email_id);
            startActivity(intent);
         /*   try {
                Log.d("wid xml", result);
                String newresult = result;
                Log.d("widout xml", newresult);
                JSONObject json = new JSONObject(newresult);
                String result_data=json.getString("msg");


            } catch (Exception e) {
                e.printStackTrace();
            }
*/
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
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
