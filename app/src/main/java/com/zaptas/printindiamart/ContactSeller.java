package com.zaptas.printindiamart;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ContactSeller extends AppCompatActivity {
public  static String product_name,product_desc,product_idd,se_id,quantity,email_tv,mobileno,requirement,seller_id;
   static EditText textView2,tv1,tv2,tv6,tv11;
    final Context context = this;
    static TextView textView;
    static String p_seid,pid,userid,p_desc,p_name,p_quantity,p_rquirement,email,phone,name,usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);

        Bundle bundle = getIntent().getExtras();
        p_seid = bundle.getString("se_id");
        pid = bundle.getString("product_id");
        p_desc = bundle.getString("product_description");
        p_name = bundle.getString("product_name");
        userid= Methods.getUSERID(this);
        usertype= Methods.getUSERTYPE(this);



        textView2=(EditText) findViewById(R.id.textView2);
        tv1=(EditText) findViewById(R.id.tv1);
        tv2=(EditText) findViewById(R.id.tv2);
        tv6=(EditText) findViewById(R.id.tv6);
        tv11=(EditText) findViewById(R.id.tv11);
        textView=(TextView) findViewById(R.id.textView);
        textView.setText(p_name);


    }

public void submit(View arg){
    name = tv11.getText().toString();
    email = tv2.getText().toString();
    phone = tv1.getText().toString();
    p_quantity = textView2.getText().toString();
    if(name.equals("")||email.equals("")||phone.equals("")||p_quantity.equals("")){
        Toast.makeText(getApplicationContext(),"All field mandatory",Toast.LENGTH_SHORT).show();
    }
else {
        DocumentData asyncT = new DocumentData();
        asyncT.execute();
    }

}


    public class DocumentData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(ContactSeller.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/lead_post"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               /* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*/



                postDataParams.put("pid",pid );
                if(usertype.equals("seller")){
                    postDataParams.put("sellerid",userid );
                }
                if(usertype.equals("user")){
                    postDataParams.put("userid",userid );
                }



                postDataParams.put("sid",p_seid );
                postDataParams.put("name",name );

                postDataParams.put("user_email"," " );
                postDataParams.put("product",p_name );
                postDataParams.put("pdescription",p_desc );
                postDataParams.put("pqty",p_quantity );
                postDataParams.put("requirement",email );





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
            dialog.setContentView(R.layout.leadapply);
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
                    Intent intent = new Intent(ContactSeller.this, MainActivity.class);

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

        while(itr.hasNext()){

            String key= itr.next();
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
