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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.models.SingleItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by PC on 8/17/2018.
 */

public class Search_Product extends AppCompatActivity {
    ImageView iv_back;
    private ProgressBar mProgress;
    static String api_msg;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private GridView gridview;
    static int lenArray = 0;
    static private MyArrayAdapter productadapter_state;
    private OkHttpClient client;
    JSONObject jsonObject;
    public static final String TAG = "TAG";
    private static Response response;
    static String meth_cate_idd, meth_cate_name;
    ArrayList<SingleItemModel> productlist;
    static List<SingleItemModel> modelList;
    TextView tv;

    static String c_Id, c_Data;
    static String ser_typee, ser_cattid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE); //for disabling status bar
        //  getWindow().setFlags(WindowM0anager.LayoutParams.FLAG_FULLSCREEN,
        //         WindowManager.LayoutParams.FLAG_FULLSCREEN);//for disabling status bar

        setContentView(R.layout.view_all_product);
     /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        Intent in = getIntent();
        //    c_Id = in.getStringExtra("prd_name");
        c_Id = in.getStringExtra("prd_id");
        c_Data = in.getStringExtra("pidd");


        System.out.println("jggjhfgjh" + meth_cate_idd + meth_cate_name);


        productlist = new ArrayList<>();
        productadapter_state = new MyArrayAdapter(this, productlist);
        gridview = (GridView) findViewById(R.id.lv_viewall);
        gridview.setAdapter(productadapter_state);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv = (TextView) findViewById(R.id.tv);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadJSON(c_Data);
        //    new GetData_Viewall().execute();


//
    }

    class GetData_Viewall extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Search_Product.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = Search_Product.getDataFromWeb();
            try {
                if (jsonObject != null) {
                    api_msg = jsonObject.getString("msg");
                    if (jsonObject.length() > 0) {
                        // JSONObject obj =jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");


                        int lenArray = array.length();
                        //   count = array.length();

                        //    trytsin bromoeilene rutoside acceflonac paracetemol
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
            Toast.makeText(getApplicationContext(), api_msg, Toast.LENGTH_LONG).show();
            if (api_msg.equals("Data not found")) {
                Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_LONG).show();
            }
            if (productlist.size() > 0) {
                productadapter_state = new MyArrayAdapter(Search_Product.this, productlist);
                productadapter_state.notifyDataSetChanged();
            } else {
                // Toast.makeText(getApplicationContext(), "No Menu", Toast.LENGTH_LONG).show();
            }
/*
try {

}catch (Exception e)
{
    e.getMessage();
}

*/
        }
    }

    public static JSONObject getDataFromWeb() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("https://printindiamart.com/public/api/get_allcategory")
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
            Picasso.get().load(item.getSt_imgfile()).into(vh.imgfile1);


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


    private void loadJSON(String v) {
        final ProgressDialog progressDialog = new ProgressDialog(Search_Product.this);
        progressDialog.setMessage("Fetching About Us...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://printindiamart.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ISearch iSearch = retrofit.create(ISearch.class);
        iSearch.getplist(v).enqueue(new Callback<Searchproductresponsemodel>() {
            @Override
            public void onResponse(Call<Searchproductresponsemodel> call, retrofit2.Response<Searchproductresponsemodel> response) {
                progressDialog.dismiss();
                //       Toast.makeText(getApplicationContext(),"Search res"+response.body().getData().get(0).getProductImage(),Toast.LENGTH_LONG).show();

                for (int i = 0; i < response.body().getData().size(); i++) {
                    SingleItemModel actor = new SingleItemModel();
                    int subcategory_id1 = response.body().getData().get(i).getProductId();
                    String name1 = response.body().getData().get(i).getProductName();
                    String cname1 = response.body().getData().get(i).getProductImage();
                    String decription1 = response.body().getData().get(i).getProductQunatity();
                    actor.setSt_subcategory_id(String.valueOf(subcategory_id1));
                    actor.setSt_name(name1);
                    actor.setSt_imgfile(cname1);
                    actor.setSt_decription(decription1);

                    productlist.add(actor);
                }


                if (response.body().getMsg().equals("Data not found")) {
                    Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_LONG).show();
                } else {
                    gridview.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                    productadapter_state = new MyArrayAdapter(Search_Product.this, productlist);
                    productadapter_state.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Searchproductresponsemodel> call, Throwable t) {
                progressDialog.dismiss();
                gridview.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "No Record Found", Toast.LENGTH_LONG).show();

            }
        });


    }


}



