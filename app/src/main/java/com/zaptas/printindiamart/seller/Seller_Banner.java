package com.zaptas.printindiamart.seller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import android.os.Bundle;
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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.models.SingleItemModel;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Seller_Banner extends AppCompatActivity {
    private ProgressBar mProgress;
    static String bandelet,error,msg;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private GridView gridview;
    static int lenArray=0;
    static private MyArrayAdapter productadapter_state;
    private OkHttpClient client;
    private static Context context;
    JSONObject jsonObject;
    public static final String TAG = "TAG";
    private static Response response;
    static  String meth_cate_idd,  meth_cate_name;
    ArrayList<SingleItemModel> productlist;
    static List<SingleItemModel> modelList;
    static String se_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__banner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        se_id=  Methods.getUSERID(this);
        setTitle("Banner");
        Seller_Banner.context = getApplicationContext();
        System.out.println("jggjhfgjh"+meth_cate_idd+meth_cate_name);




        productlist = new ArrayList<>();
        productadapter_state = new MyArrayAdapter(this, productlist);
        gridview = (GridView)findViewById(R.id.lv_viewall);
        gridview.setAdapter(productadapter_state);


        new GetData_Viewall().execute();
    }
    public void update(View arg){
        Intent intent = new Intent(Seller_Banner.this, Update_Banner.class);
        startActivity(intent);
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



    class GetData_Viewall extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Seller_Banner.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = getDataFromWeb();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj =jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("seller_image");
                        int lenArray = array.length();
                        //   count = array.length();

                        if (lenArray > 0) {
                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {
                                SingleItemModel actor = new SingleItemModel();
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String subcategory_id1 = innerObject.getString("id");
                                //String name1 = innerObject.getString("product_name");
                                String cname1 = innerObject.getString("image");
                                // String decription1 = innerObject.getString("product_quantity");
                                //  String imgfile1 = innerObject.getString("pamount");
                                // String status1 = innerObject.getString("pdescription");
                                //  String str_subcategory_type = innerObject.getString("pname");





                                actor.setSt_subcategory_id(subcategory_id1);
                                //  actor.setSt_name(name1);
                                actor.setSt_cname(cname1);
                                // actor.setSt_decription(decription1);
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

                    .url("https://printindiamart.com/public/api/seller_image/"+se_id)
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
                View view = mInflater.inflate(R.layout.logo_adapter, parent, false);
                vh = ViewHolder.create((LinearLayout) view);
                view.setTag(vh);


            } else {

                vh = (ViewHolder) convertView.getTag();
            }

            final SingleItemModel item = getItem(position);
            assert item != null;




            vh.subcategory1.setText(item.getSt_subcategory_id());
            vh.name1.setText(item.getSt_name());
            vh.cname1.setText("Quantity-:"+item.getSt_decription());
            vh.description1.setText(item.getSt_decription());
            Picasso.get().load("https://printindiamart.com/public/subdomain/schon/photo/"+item.getSt_imgfile()).into(vh.imgfile1);


            System.out.println("ghgfdgdfgdfghddfg"+item.getSt_status() );



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

            vh.status1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String v_subcategory_id = item.getSt_subcategory_id();



                Intent in = new Intent(v.getContext(), Edit_Banner.class);
                    in.putExtra("int_subcategory_id",v_subcategory_id);
                    v.getContext().startActivity(in);


                }
            });

         /*   vh.status1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });
*/






            vh.delelet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bandelet=item.getSt_subcategory_id();
                    new GetData_deletBanner().execute();
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
            public final TextView delelet;


            private ViewHolder(LinearLayout rootView, TextView subcategory1, TextView name1,
                               TextView  cname1, TextView description1, ImageView imgfile1,
                               TextView  status1, TextView delelet) {
                this.rootView = rootView;

                this.subcategory1 = subcategory1;
                this.name1 = name1;
                this.cname1 = cname1;
                this.description1 = description1;
                this.imgfile1 = imgfile1;
                this.status1 = status1;
                this.delelet = delelet;


            }

            public static ViewHolder create(LinearLayout rootView) {



                TextView  subcategory1 = (TextView) rootView.findViewById(R.id.id_subcategory);
                TextView  name1 = (TextView) rootView.findViewById(R.id.id_name);
                TextView cname1 = (TextView) rootView.findViewById(R.id.id_cname);
                TextView  description1 = (TextView) rootView.findViewById(R.id.id_description);
                ImageView  imgfile1 = (ImageView) rootView.findViewById(R.id.id_imgfile);
                TextView status1 = (TextView) rootView.findViewById(R.id.id_statuss);
                TextView delelet = (TextView) rootView.findViewById(R.id.delelet);




                return new ViewHolder(rootView,  subcategory1, name1,cname1,description1,imgfile1,status1,delelet);
            }
        }
    }
    static class GetData_deletBanner extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dialog = new ProgressDialog(Seller_Banner.this);
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = getDataFromWebbaneer();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj =jsonObject.getJSONObject("data");
                        msg = jsonObject.getString("msg");
                        error = jsonObject.getString("error");

                        //   count = array.length();


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

            Toast.makeText(Seller_Banner.context, msg, Toast.LENGTH_LONG).show();
            if(error.equals("false")){
                Intent intent = new Intent(Seller_Banner.context, Seller_Banner.class);

                Seller_Banner.context.startActivity(intent);
            }
          /*  dialog.dismiss();

            if (productlist.size() > 0) {
                productadapter_state.notifyDataSetChanged();
            } else {
                // Toast.makeText(getApplicationContext(), "No Menu", Toast.LENGTH_LONG).show();
            }*/
        }
    }

    public static JSONObject getDataFromWebbaneer() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //   http://spacenterio.com/subdomain/filfox/ApiAdminController/subcategorybyid_get/8

                    .url("https://printindiamart.com/public/api/banner_delete/"+bandelet)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
