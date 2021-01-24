package com.zaptas.printindiamart;

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
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Change_Password extends AppCompatActivity {
EditText old,newpass,confirmpass;
    static  String oldd,neww,confirmm,se_id,red,msg,error;
    private static Response response;
    JSONObject jsonObject;
    final Context context = this;
   // static String se_id,lead12,product,packagename,time,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        confirmpass=(EditText) findViewById(R.id.confirmpass);
        newpass=(EditText) findViewById(R.id.newpass);
        old=(EditText) findViewById(R.id.old);
        se_id=  Methods.getUSERID(this);
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
    public void update(View arg){
        oldd = old.getText().toString();
        neww = newpass.getText().toString();
        confirmm = confirmpass.getText().toString();

        if(oldd.equals("") || neww.equals("") || confirmm.equals("")){
            Toast.makeText(getApplicationContext(),"All feild mandatory",Toast.LENGTH_SHORT).show();
        }
        else {
            if(neww.equals(confirmm)){
                change_password asyncT = new change_password();
                asyncT.execute();
            }
            else {
                Toast.makeText(getApplicationContext(), "Password and confirm password not match",
                        Toast.LENGTH_LONG).show();
            }
        }



    }


 /*   public class DocumentData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(Change_Password.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/change_password"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               *//* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*//*


                postDataParams.put("old_password", oldd);
                postDataParams.put("new_password", neww);
                postDataParams.put("sid", se_id);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 *//* milliseconds *//*);
                conn.setConnectTimeout(15000 *//* milliseconds *//*);
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
           Log.d("wid xml", result);
            String newresult = result;
            System.out.print("Everset1" + newresult);

            Log.d("widout xml", newresult);
            JSONObject json = null;
            try {
                json = new JSONObject(newresult);
                red = json.getString("error");
                 msg = json.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //
            if (red.equals("true")) {
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_LONG).show();
            } else
            {

                final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.changepassword_dailog);
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
                    Intent intent = new Intent(Change_Password.this, Dashboard_Seller.class);

                    startActivity(intent);
                }
            });
            dialogButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Change_Password.this, Dashboard_Seller.class);

                    startActivity(intent);
                }
            });
            dialog.show();
        }
        //{"error":false,"msg":"Photo saved successfully.","data":[{"id":38,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a06d9bd82.jpeg","created_at":"2018-04-26 09:48:29","updated_at":"2018-04-26 09:48:29"},{"id":39,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a218b4419.jpeg","created_at":"2018-04-26 09:55:36","updated_at":"2018-04-26 09:55:36"},{"id":40,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a2f29f052.jpeg","created_at":"2018-04-26 09:59:14","updated_at":"2018-04-26 09:59:14"},{"id":42,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a5625d5a8.jpeg","created_at":"2018-04-26 10:09:38","updated_at":"2018-04-26 10:09:38"},{"id":48,"customer_id":"251002584","user_name":"ARAMPAL","distribution_channel":"30","division":"51","salse_office":"S513","zone":"ZONED","img_name":"251002584","image":"customerimage\/5ae1a78858b35.jpeg","created_at":"2018-04-26 10:18:48","updated_at":"2018-04-26 10:18:48"}]}
    }

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
    }*/









    class change_password extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        /*public void add_filcal(View arg){
            Intent inaction3 = new Intent(v.getContext(), FilCal_ParticularItem.class);
        }*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((Change_Password.this));
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
                Intent intent = new Intent(Change_Password.this, Dashboard_Seller.class);
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

                    .url("https://printindiamart.com/public/api/change_password/"+se_id+"/"+oldd+"/"+neww)
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
