package com.zaptas.printindiamart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.actor.SlideActor;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class Product_Detail extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    static public String product_id;
    private static Response response;
    TextView prd_name, prd_desc, prd_ammount, prd_feature;
    ImageView prdimg, iv_back;
    private static Response sli_response;

    SliderLayout mDemoSlider;
    SlideActor sli_actor;
    static String sli_image, sli_name;
    HashMap<String, String> file_maps;
    private ArrayList<SlideActor> sli_list;
    JSONObject jsonObject;
    static public String name1, ammount, decription1, feature, mobile, seid, pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail);
    /*    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        sli_list = new ArrayList<>();
       /* mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        mDemoSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        prd_name = (TextView) findViewById(R.id.prdname);
        prd_desc = (TextView) findViewById(R.id.prddescription);
        prd_ammount = (TextView) findViewById(R.id.prdamount);
        prd_feature = (TextView) findViewById(R.id.prdfeature);


        Intent in = getIntent();
        product_id = in.getStringExtra("int_subcategory_id");
        new GetDataTaskslider().execute();
        new Get_productdetail().execute();
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
        bottomNavigationView.setSelectedItemId(R.id.homeNavigation);
        bottomNavigationView.setItemBackground(null);
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

    class GetDataTaskslider extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog = new ProgressDialog((Product_Detail.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            jsonObject = getDataFromWeb12();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        int lenArray = array.length();
                        if (lenArray > 0) {
                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                sli_actor = new SlideActor();
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String id1 = innerObject.getString("product_id");
                                //  sli_name = innerObject.getString("SliderName");
                                sli_image = innerObject.getString("image");
                                sli_name = "";
                                sli_image = "https://printindiamart.com/public/photo/" + sli_image;
                                sli_actor.setName("");
                                sli_actor.setImage(sli_image);
                                sli_list.add(sli_actor);

                                ImageSlider slider = (ImageSlider) findViewById(R.id.slider);

                                List<SlideModel> slideModels = new ArrayList<>();


                                try {
                                    file_maps = new HashMap<String, String>();
                                    file_maps.put(sli_name, sli_image);

                                    for (final String name1 : file_maps.keySet()) {
                                        /*TextSliderView textSliderView = new TextSliderView(getApplicationContext());

                                        textSliderView
                                                .description(name1)
                                                .image(file_maps.get(name1))
                                                .setScaleType(BaseSliderView.ScaleType.Fit);

                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle().putString("extra", name1);*/
                                        // mDemoSlider.addSlider(textSliderView);


                                        slideModels.add(new SlideModel(file_maps.get(name1), ScaleTypes.FIT));

                                    }

                                    slider.setImageList(slideModels, ScaleTypes.FIT);

                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                } else {
                }
            } catch (JSONException je) {

            }

        }
    }


    public static JSONObject getDataFromWeb12() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/productimage_by_id/" + product_id)
                    .build();
            sli_response = client.newCall(request).execute();
            return new JSONObject(sli_response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public void mobile_number(View arg) {
        new Get_MobileNo().execute();
    }


    public void contact_supplier(View arg) {

        Intent intent = new Intent(Product_Detail.this, ContactSeller.class);
        intent.putExtra("product_id", product_id);
        intent.putExtra("se_id", seid);
        intent.putExtra("product_name", name1);
        intent.putExtra("product_description", decription1);
        startActivity(intent);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class Get_MobileNo extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  dialog = new ProgressDialog(Product_Detail.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = getDataFromWeb1();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        JSONObject obj = jsonObject.getJSONObject("data");
                        mobile = obj.getString("mobile");

                        // JSONArray array = jsonObject.getJSONArray("seller_number");
                        // int lenArray = array.length();
                        //   count = array.length();


                    }


                } else {
                }
            } catch (JSONException je) {
                //  Log.i(ParticularImage.TAG, "" + je.getLocalizedMessage());
            }
//            Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //   dialog.dismiss();
//            prd_name.setText(name1);
            //  prd_ammount.setText(ammount);
            //    prd_desc.setText(d);
            //  prd_feature.setText(name1);


            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Product_Detail.this);
            View mView = getLayoutInflater().inflate(R.layout.dialog_enquiry_success, null);

            TextView btnCancelRating = (TextView) mView.findViewById(R.id.btn_done);
            TextView textView2 = (TextView) mView.findViewById(R.id.textView2);
            textView2.setText(mobile);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            btnCancelRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }
    }

    public static JSONObject getDataFromWeb1() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("\n" +
                            "https://printindiamart.com/public/api/seller_mobile/" + seid)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
        }
        return null;
    }

    class Get_productdetail extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  dialog = new ProgressDialog(Product_Detail.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = getDataFromWeb();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        JSONObject obj = jsonObject.getJSONObject("product");
                        mobile = obj.getString("pname");
                        ammount = obj.getString("pamount");
                        feature = obj.getString("field_name");
                        decription1 = obj.getString("pdescription");
                        name1 = obj.getString("pname");
                        seid = obj.getString("sid");
                        pid = obj.getString("pid");

                        // JSONArray array = jsonObject.getJSONArray("seller_number");
                        // int lenArray = array.length();
                        //   count = array.length();


                    }


                } else {
                }
            } catch (JSONException je) {
                //  Log.i(ParticularImage.TAG, "" + je.getLocalizedMessage());
            }
//            Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //   dialog.dismiss();
//            prd_name.setText(name1);
            //  prd_ammount.setText(ammount);
            //    prd_desc.setText(d);
            //  prd_feature.setText(name1);

            prd_ammount.setText(ammount + " " + "Rs");
            prd_feature.setText(feature);
            prd_desc.setText(decription1);
            prd_name.setText(name1);


        }
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("https://printindiamart.com/public/api/product_by_id/" + product_id)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
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
