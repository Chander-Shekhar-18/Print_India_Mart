package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.models.SingleItemModel;
import com.zaptas.printindiamart.seller.Dashboard_Seller;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;


/**
 * Created by PC on 8/17/2018.
 */

public class ViewAllProduct extends AppCompatActivity {

    private ProgressBar mProgress;
    static String api_msg;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private GridView gridview;
    static int lenArray = 0;
    static private MyArrayAdapter productadapter_state;
    private OkHttpClient client;
    JSONObject jsonObject;
    ImageView iv_back;
    public static final String TAG = "TAG";
    private static Response response;
    static String meth_cate_idd, meth_cate_name;
    ArrayList<SingleItemModel> productlist;
    static List<SingleItemModel> modelList;

    static String c_Id, c_Data;
    static String ser_typee, ser_cattid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE); //for disabling status bar
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //         WindowManager.LayoutParams.FLAG_FULLSCREEN);//for disabling status bar

        setContentView(R.layout.view_all_product);

      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

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

        Intent in = getIntent();
        c_Id = in.getStringExtra("prd_id");
        c_Data = in.getStringExtra("prd_name");

        setTitle(c_Data);

        System.out.println("jggjhfgjh" + meth_cate_idd + meth_cate_name);


        productlist = new ArrayList<>();
        productadapter_state = new MyArrayAdapter(this, productlist);
        gridview = (GridView) findViewById(R.id.lv_viewall);
        gridview.setAdapter(productadapter_state);
        String n = in.getStringExtra("pidd");
        new GetData_Viewall().execute();
        // loadJSON(n);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    class GetData_Viewall extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ViewAllProduct.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = ViewAllProduct.getDataFromWeb();
            try {
                if (jsonObject != null) {
                    api_msg = jsonObject.getString("msg");
                    if (jsonObject.length() > 0) {
                        // JSONObject obj =jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");


                        int lenArray = array.length();
                        //   count = array.length();

                        if (lenArray > 0) {
                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {
                                SingleItemModel actor = new SingleItemModel();
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String subcategory_id1 = innerObject.getString("product_id");
                                String name1 = innerObject.getString("product_name");
                                String cname1 = innerObject.getString("image");
                                String decription1 = innerObject.getString("product_quantity");
                                //  String imgfile1 = innerObject.getString("pamount");
                                // String status1 = innerObject.getString("pdescription");
                                //  String str_subcategory_type = innerObject.getString("pname");


                                actor.setSt_subcategory_id(subcategory_id1);
                                actor.setSt_name(name1);
                                actor.setSt_cname(cname1);
                                actor.setSt_decription(decription1);
                                actor.setSt_imgfile(cname1);
                                //  actor.setSt_status(status1);
                                // actor.setSt_subcategory_type(str_subcategory_type);


                                //     System.out.println("arraylist_name1"+id1);
                                productlist.add(actor);
                            }
                            //     Collections.reverse(productlist);
                        }
                    }


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
            //     Toast.makeText(getApplicationContext(), api_msg, Toast.LENGTH_LONG).show();
            if (productlist.size() > 0) {
                productadapter_state.notifyDataSetChanged();
            } else {
                // Toast.makeText(getApplicationContext(), "No Menu", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static JSONObject getDataFromWeb() {


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("https://printindiamart.com/public/api/product_by_category/" + c_Id)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

/*    public static String GET(OkHttpClient client, HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .build();
        Response responseOK = client.newCall(request).execute();
        return responseOK.body().string();
    }
    public static HttpUrl buildURL() {
        return new HttpUrl.Builder()
                .scheme("https") //http
                .host("www.a.com")
                .addPathSegment("a")//adds "/pathSegment" at the end of hostname
                .addQueryParameter("a", "a") //add query parameters to the URL
                .addEncodedQueryParameter("a", "a")//add encoded query parameters to the URL
                .build();
    }*/


    public static class MyArrayAdapter extends ArrayAdapter<SingleItemModel> {


        Context context;
        private LayoutInflater mInflater;

        // Constructors
        public MyArrayAdapter(Context context, List<SingleItemModel> objects) {
            super(context, 0, objects);
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            modelList = objects;
        }

        @Override
        public SingleItemModel getItem(int position) {
            return modelList.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;

            if (convertView == null) {
                View view = mInflater.inflate(R.layout.adapter_viewall, parent, false);
                vh = ViewHolder.create((LinearLayout) view);
                view.setTag(vh);


            } else {

                vh = (ViewHolder) convertView.getTag();
            }

            final SingleItemModel item = getItem(position);
            assert item != null;


            vh.subcategory1.setText(item.getSt_subcategory_id());
            vh.name1.setText(item.getSt_name());
            vh.cname1.setText("Quantity-:" + item.getSt_decription());
            vh.description1.setText(item.getSt_decription());
            Picasso.get().load("https://printindiamart.com/public/photo/" + item.getSt_imgfile()).into(vh.imgfile1);


            System.out.println("ghgfdgdfgdfghddfg" + item.getSt_status());



         /*   if(item.getSt_status().equals("0"))
            {
                vh.status1.setText("New");
            }
            else if(item.getSt_status().equals("1"))
            {
                vh.status1.setText("In-Progress");
            }
            else if(item.getSt_status().equals("2"))
            {
                vh.status1.setText("Close");
            }
*/

            vh.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String v_subcategory_id = item.getSt_subcategory_id();


                    Intent in = new Intent(v.getContext(), Product_Detail.class);
                    //   Intent in = new Intent(v.getContext(), Apply_Services.class);
                    in.putExtra("int_subcategory_id", v_subcategory_id);
                    in.putExtra("int_cat_data", c_Data);


                    v.getContext().startActivity(in);


                }
            });


            System.out.println("nhjhngj" + ser_typee);


            vh.imgfile1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //   c_Id, c_Data;


                    ser_typee = item.getSt_subcategory_type();
                    ser_cattid = c_Id;

                    String v_subcategory_id = item.getSt_subcategory_id();


                    Intent in = new Intent(v.getContext(), Product_Detail.class);
                    //   Intent in = new Intent(v.getContext(), Apply_Services.class);
                    in.putExtra("int_subcategory_id", v_subcategory_id);
                    in.putExtra("int_cat_data", c_Data);


                    v.getContext().startActivity(in);


                }


            });


            return vh.rootView;
        }

        private static class ViewHolder {
            public final LinearLayout rootView;
            public final TextView subcategory1;
            public final TextView name1;
            public final TextView cname1;
            public final TextView description1;
            public final ImageView imgfile1;
            public final TextView status1;


            private ViewHolder(LinearLayout rootView, TextView subcategory1, TextView name1,
                               TextView cname1, TextView description1, ImageView imgfile1,
                               TextView status1) {
                this.rootView = rootView;

                this.subcategory1 = subcategory1;
                this.name1 = name1;
                this.cname1 = cname1;
                this.description1 = description1;
                this.imgfile1 = imgfile1;
                this.status1 = status1;


            }

            public static ViewHolder create(LinearLayout rootView) {


                TextView subcategory1 = (TextView) rootView.findViewById(R.id.id_subcategory);
                TextView name1 = (TextView) rootView.findViewById(R.id.id_name);
                TextView cname1 = (TextView) rootView.findViewById(R.id.id_cname);
                TextView description1 = (TextView) rootView.findViewById(R.id.id_description);
                ImageView imgfile1 = (ImageView) rootView.findViewById(R.id.id_imgfile);
                TextView status1 = (TextView) rootView.findViewById(R.id.id_statuss);


                return new ViewHolder(rootView, subcategory1, name1, cname1, description1, imgfile1, status1);
            }
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


    /*private void loadJSON(String v) {
        final ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Fetching About Us...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://printindiamart.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ISearch iSearch=retrofit.create(ISearch.class);
        iSearch.getplist(v).enqueue(new Callback<List<Searchproductresponsemodel>>() {
            @Override
            public void onResponse(Call<List<Searchproductresponsemodel>> call, retrofit2.Response<List<Searchproductresponsemodel>> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Search res"+response.body().get(0).getData().get(0).getProductName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Searchproductresponsemodel>> call, Throwable t) {
            progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Search fail res"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }*/


}



