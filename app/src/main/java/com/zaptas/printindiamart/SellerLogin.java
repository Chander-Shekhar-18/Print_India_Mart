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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.seller.Forget_Password;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class SellerLogin extends AppCompatActivity {
    JSONObject jsonObject;
    private static Response response;
   static String Errr;
    EditText Email,password;
    public  static  String logo;
   public static String email,password_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        Email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);


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

    public void signin(View arg){
        Intent intent = new Intent(SellerLogin.this, SellerRegistration.class);
        startActivity(intent);
    }
    public void forgetpassword(View arg){
        Intent intent = new Intent(SellerLogin.this, Forget_Password.class);
        startActivity(intent);
    }
    public void login(View arg){
        email= Email.getText().toString();;
        password_tv= password.getText().toString();;
        if(email.equals("")){
            Toast.makeText(getApplicationContext(),"Enter email id",Toast.LENGTH_SHORT).show();
        }
        else if(password_tv.equals("")){
            Toast.makeText(getApplicationContext(),"Enter password.",Toast.LENGTH_SHORT).show();
        }
        else {
            new Login().execute();
        }

    }
    class Login extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        /*public void add_filcal(View arg){
            Intent inaction3 = new Intent(v.getContext(), FilCal_ParticularItem.class);
        }*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((SellerLogin.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonObject = getDataFromWeb();

            try {
                 Errr = jsonObject.getString("error");
                if(Errr.equals("true")){



                }
                else {
                    if (jsonObject != null) {
                        if (jsonObject.length() > 0) {

                            JSONArray array = jsonObject.getJSONArray("data");
                          //  JSONObject cond = jsonObject.getJSONObject("error");

                            int lenArray = array.length();





                            if (lenArray > 0) {
                                for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                    JSONObject innerObject = array.getJSONObject(jIndex);


                                    String user_id = innerObject.getString("id");
                                    String f_name = innerObject.getString("fname");
                                    String l_name = innerObject.getString("lname");
                                    String email = innerObject.getString("email");
                                    String registartionid = innerObject.getString("registartionId");
                                    String categries = innerObject.getString("categories");
                                    String phone = innerObject.getString("phone");
                                    String image = innerObject.getString("image");
                                    String anual_incom = innerObject.getString("image");
                                    String numberemp = innerObject.getString("number_emp");
                                    String busname = innerObject.getString("Business_name");
                              String logo12 = innerObject.getString("logo");
                                    String add1 = innerObject.getString("b_address");
                                    String city = innerObject.getString("b_city");
                                    String pincode = innerObject.getString("b_pincode");
                                    String gst = innerObject.getString("gstin");
                                    String tin = innerObject.getString("tan");
                                    String pan = innerObject.getString("pan");
                                    String cin = innerObject.getString("cin");
                                    String cindgft = innerObject.getString("dgft");
                                    String nempl = innerObject.getString("number_emp");
                                    String turn = innerObject.getString("annual_turnover");
                                    String anualturn = innerObject.getString("annual_turnover");
                                    String businesstype = innerObject.getString("primary_bus_type");
                                    String ownertype = innerObject.getString("ownership_type");
                                    String about = innerObject.getString("about_us");
                                    String package1 = innerObject.getString("package");


                                    Methods.saveUSERID(SellerLogin.this,user_id);
                                    Methods.saveUSERNAME(SellerLogin.this,f_name);
                                    Methods.saveLUSERNAME(SellerLogin.this,l_name);
                                    Methods.saveEmailID(SellerLogin.this,email);
                                    Methods.saveREGID(SellerLogin.this,registartionid);
                                    Methods.saveMobileNo(SellerLogin.this,phone);
                                    Methods.saveUserIMG(SellerLogin.this,image);
                                    Methods.saveLogo(SellerLogin.this,logo12);
                                    Methods.savePinCode(SellerLogin.this,pincode);
                                    Methods.saveAdress(SellerLogin.this,add1);
                                    Methods.saveCity(SellerLogin.this,city);
                                    Methods.saveUSERTYPE(SellerLogin.this,"seller");
                                    Methods.saveComapny(SellerLogin.this,busname);
                                    Methods.saveabout(SellerLogin.this,about);
                                    Methods.savepack_zaptas(SellerLogin.this,package1);


                                    Methods.saveanualturn(SellerLogin.this,anualturn);
                                    Methods.savebtype(SellerLogin.this,businesstype);
                                    Methods.saveownertype(SellerLogin.this,ownertype);
                                    Methods.savegst(SellerLogin.this,gst);
                                    Methods.savetan(SellerLogin.this,tin);
                                    Methods.saveturn(SellerLogin.this,turn);
                                    Methods.savepan(SellerLogin.this,pan);
                                    Methods.savenemp(SellerLogin.this,nempl);
                                    Methods.saveturn(SellerLogin.this,cindgft);
                                    Methods.savecin(SellerLogin.this,cin);


                                }
                                //     Collections.reverse(productlist);
                            }

                        /////////////////


                        }


                    } else {
                    }
                    Intent intent = new Intent(SellerLogin.this, Dashboard_Seller.class);
                    startActivity(intent);
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

            if(Errr.equals("true")){

                Toast.makeText(getApplicationContext(), "Username Password Mismatch",
                        Toast.LENGTH_LONG).show();

            }

        }
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/ff12092018131049

                    .url("http://printindiamart.com/public/api/sellerlogin_get/"+email+"/"+password_tv)
                    .build();

            response = client.newCall(request).execute();
          //  System.out.println("8878788787"+"http://spacenterio.com/subdomain/filfox/ApiAdminController/query_get/"+meth_username);
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
           // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
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
