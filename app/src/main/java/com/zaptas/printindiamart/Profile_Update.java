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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import javax.net.ssl.HttpsURLConnection;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class Profile_Update extends AppCompatActivity {
    EditText firstname, lastname, mobileno, companyname, pin, city, adress, business, noe, anuaal, ownership, gst, tan, pan, cin, dgft;
    String se_id, firstname_t, lastname_t, mobile_no, companyname_t, pin_t, city_t, adress_t, business_t, noe_t, annual_t, ownership_t, gst_t, tan_t, pan_t, cin_t, dgft_t;
    ImageView imageViewBtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        se_id = Methods.getUSERID(this);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        mobileno = (EditText) findViewById(R.id.mobileno);
        companyname = (EditText) findViewById(R.id.companyname);
        pin = (EditText) findViewById(R.id.pin);
        city = (EditText) findViewById(R.id.city);
        adress = (EditText) findViewById(R.id.adress);
        business = (EditText) findViewById(R.id.business);
        noe = (EditText) findViewById(R.id.noe);
        anuaal = (EditText) findViewById(R.id.anuaal);
        ownership = (EditText) findViewById(R.id.ownership);
        gst = (EditText) findViewById(R.id.gst);
        tan = (EditText) findViewById(R.id.tan);
        pan = (EditText) findViewById(R.id.pan);
        cin = (EditText) findViewById(R.id.cin);
        imageViewBtnBack = findViewById(R.id.btnBack);
        dgft = (EditText) findViewById(R.id.dgft);


        imageViewBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        Bundle bundle = getIntent().getExtras();
        firstname.setText(bundle.getString("firstname"));
        lastname.setText(bundle.getString("lastname"));
        mobileno.setText(bundle.getString("mobileno"));
        companyname.setText(bundle.getString("comapnyname"));
        pin.setText(bundle.getString("pincode"));
        city.setText(bundle.getString("city"));
        adress.setText(bundle.getString("adress"));
        business.setText(bundle.getString("nemployee"));
        noe.setText(bundle.getString("nemployee"));
        anuaal.setText(bundle.getString("annualturnover"));
        ownership.setText(bundle.getString("ownershiptype"));
        gst.setText(bundle.getString("gst"));
        tan.setText(bundle.getString("tan"));
        pan.setText(bundle.getString("pan"));
        cin.setText(bundle.getString("cin"));
        dgft.setText(bundle.getString("dgft"));

    }

    public void submit(View arg) {

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

    public void submitdata(View arg) {
        firstname_t = firstname.getText().toString();
        lastname_t = lastname.getText().toString();
        mobile_no = mobileno.getText().toString();
        companyname_t = companyname.getText().toString();
        pin_t = pin.getText().toString();
        city_t = city.getText().toString();
        adress_t = adress.getText().toString();
        business_t = business.getText().toString();
        noe_t = noe.getText().toString();
        annual_t = anuaal.getText().toString();
        ownership_t = ownership.getText().toString();
        gst_t = gst.getText().toString();
        tan_t = tan.getText().toString();
        pan_t = pan.getText().toString();
        cin_t = cin.getText().toString();
        dgft_t = dgft.getText().toString();


        if (firstname_t.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
        } else if (companyname_t.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter company name", Toast.LENGTH_SHORT).show();
        } else if (mobile_no.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter mobile no. name", Toast.LENGTH_SHORT).show();
        } else {

            SendPostRequest asyncT = new SendPostRequest();
            asyncT.execute();

        }
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(Profile_Update.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/update_profile"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               /* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*/


                postDataParams.put("Last_name", lastname_t);
                postDataParams.put("id", se_id);
                postDataParams.put("First_name", firstname_t);
                postDataParams.put("Phone", mobile_no);
                postDataParams.put("Bussiness_name", companyname_t);
                postDataParams.put("primary_bussiness_type", business_t);
                postDataParams.put("ownership_type", ownership_t);
                postDataParams.put("annual_turnover", annual_t);
                postDataParams.put("gstin", gst_t);
                postDataParams.put("tan", tan_t);
                postDataParams.put("PAN_Number", pan_t);
                postDataParams.put("CIN_Number", cin_t);
                postDataParams.put("DGFT_or_IE_code", dgft_t);
                postDataParams.put("company_city", city_t);
                postDataParams.put("company_pincode", pin_t);
                postDataParams.put("company_address", adress_t);
                postDataParams.put("number_of_emplyee", noe_t);


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


            Methods.saveUSERTYPE(Profile_Update.this, "zap");
            Methods.saveUSERID(Profile_Update.this, null);
            Intent intent = new Intent(Profile_Update.this, SellerLogin.class);
            startActivity(intent);
          /*  //{"error":false,"msg":"Photo saved successfully.","data":[{"id":38,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a06d9bd82.jpeg","created_at":"2018-04-26 09:48:29","updated_at":"2018-04-26 09:48:29"},{"id":39,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a218b4419.jpeg","created_at":"2018-04-26 09:55:36","updated_at":"2018-04-26 09:55:36"},{"id":40,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a2f29f052.jpeg","created_at":"2018-04-26 09:59:14","updated_at":"2018-04-26 09:59:14"},{"id":42,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a5625d5a8.jpeg","created_at":"2018-04-26 10:09:38","updated_at":"2018-04-26 10:09:38"},{"id":48,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a78858b35.jpeg","created_at":"2018-04-26 10:18:48","updated_at":"2018-04-26 10:18:48"}]}
            Log.d("wid xml", result);
            String newresult = result;
            System.out.print("Everset1"+newresult);
            try {
                Log.d("widout xml", newresult);
                JSONObject json = new JSONObject(newresult);
                //
                String result_data = json.getString("error");
                String result_data2 = json.getString("msg");
                System.out.println("lionn" + result);

                JSONArray array = json.getJSONArray("data");
                int lenArray = array.length();

                for (int jIndex = 0; jIndex < lenArray; jIndex++) {



                    JSONObject innerObject = array.getJSONObject(jIndex);
                    String id1 = innerObject.getString("id");
                    String customer_id = innerObject.getString("customer_id");

                }






            } catch (JSONException je) {
                //Log.i(Profile_Update.TAG, "" + je.getLocalizedMessage());
            }*/

            //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

//  return image

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
}
