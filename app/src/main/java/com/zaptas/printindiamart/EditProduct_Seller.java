package com.zaptas.printindiamart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zaptas.printindiamart.seller.Dashboard_Seller;
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

public class EditProduct_Seller extends AppCompatActivity {
    static EditText pname, pquantity, ammount, desc;
    String tv_pname, tv_quantity, tv_ammount, tv_desc, tv_pid;
    static String u_pname, u_quantity, u_ammount, u_desc, u_pid;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product__seller);

        Bundle bundle = getIntent().getExtras();
        tv_pid = bundle.getString("pidd");
        tv_pname = bundle.getString("pname");
        tv_quantity = bundle.getString("quantiy");
        tv_ammount = bundle.getString("ammount");
        tv_desc = bundle.getString("desc");

        pname = (EditText) findViewById(R.id.pname);
        pquantity = (EditText) findViewById(R.id.pquantity);
        ammount = (EditText) findViewById(R.id.ammount);
        desc = (EditText) findViewById(R.id.desc);

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


        pname.setText(tv_pname);
        pquantity.setText(tv_quantity);
        ammount.setText(tv_ammount);
        //desc.setText(tv_desc);

    }

    public void update_data(View arg) {
        u_pname = pname.getText().toString();

        u_quantity = pquantity.getText().toString();
        u_ammount = ammount.getText().toString();
        u_desc = desc.getText().toString();
        Update asyncT = new Update();
        asyncT.execute();
    }


    public class Update extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(EditProduct_Seller.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/update_product"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               /* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*/


                postDataParams.put("pname", u_pname);
                postDataParams.put("pamount", u_ammount);
                postDataParams.put("pqty", u_quantity);
                postDataParams.put("pdescription", u_desc);

                postDataParams.put("pid", tv_pid);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString1(postDataParams));

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
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.update_product_dailog);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);
            // text.setText("Android custom dialog example!");
            TextView image = (TextView) dialog.findViewById(R.id.image);


            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            Button dialogButton1 = (Button) dialog.findViewById(R.id.dialogButtonOK1);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialogButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditProduct_Seller.this, ManageProduct.class);

                    startActivity(intent);
                }
            });
            dialog.show();
        }
        //{"error":false,"msg":"Photo saved successfully.","data":[{"id":38,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a06d9bd82.jpeg","created_at":"2018-04-26 09:48:29","updated_at":"2018-04-26 09:48:29"},{"id":39,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a218b4419.jpeg","created_at":"2018-04-26 09:55:36","updated_at":"2018-04-26 09:55:36"},{"id":40,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a2f29f052.jpeg","created_at":"2018-04-26 09:59:14","updated_at":"2018-04-26 09:59:14"},{"id":42,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a5625d5a8.jpeg","created_at":"2018-04-26 10:09:38","updated_at":"2018-04-26 10:09:38"},{"id":48,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a78858b35.jpeg","created_at":"2018-04-26 10:18:48","updated_at":"2018-04-26 10:18:48"}]}


    }

    public String getPostDataString1(JSONObject params) throws Exception {

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
