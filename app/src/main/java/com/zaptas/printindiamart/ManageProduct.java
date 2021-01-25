package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.actor.SlideActor;
import com.zaptas.printindiamart.models.SectionDataModel_Seller;
import com.zaptas.printindiamart.models.SingleItemModel;
import com.zaptas.printindiamart.models.SingleItemModel_Seller;
import com.zaptas.printindiamart.util.Methods;
import com.zaptas.printindiamart.wideget.RecyclerViewWithNavigationArrows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class ManageProduct extends AppCompatActivity {
    SliderLayout mDemoSlider;
    private static Response response1;
    private static Response response;
    static String deleid, apimsg, apierror;
    ImageView imageViewManageProduct;
    private static Context context;
    static String usertype, user_id;
    int lenArray;
    ArrayList<SingleItemModel> singleItem;
    Button showPopupBtn, closePopupBtn, delet;
    PopupWindow popupWindow;
    RelativeLayout rel1;
    private static Response sli_response;
    SlideActor sli_actor;
    private static RecyclerView recyclerView1;
    String str_imgfile, str_decription, category, str_cname, str_status, product_user_n, str_subcategory_type, str_name, pricev, str_subcategory_id, str_cat_data, str_cat_id, quantity, ammount, category1, cat_id, subcat_id, subcate, desc, desc1;
    static String ser_typee, ser_cattid;
    HashMap<String, String> file_maps;
    JSONObject jsonObject;
    static String sli_image, sli_name;
    private ArrayList<SlideActor> sli_list;
    ArrayList<SectionDataModel_Seller> allSampleData;
    RecyclerViewDataAdapter adapter;
    static String se_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //      getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageViewManageProduct = findViewById(R.id.iv_ManageProduct);
        imageViewManageProduct.setOnClickListener(new View.OnClickListener() {
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

        ManageProduct.context = getApplicationContext();
        se_id = Methods.getUSERID(this);
        allSampleData = new ArrayList<>();
        recyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view);
        rel1 = (RelativeLayout) findViewById(R.id.rel1);
        recyclerView1.setHasFixedSize(true);
        adapter = new RecyclerViewDataAdapter(this.getApplication(), allSampleData);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        new GetDataTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ManageProduct.this, Dashboard_Seller.class);

                startActivity(intent);
                break;
        }
        return true;
    }

    class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {
        LinearLayoutManager linearLayoutManager;
        private ArrayList<SectionDataModel_Seller> dataList;
        private Context mContext;

        public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel_Seller> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_mange, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }


        @Override
        public void onBindViewHolder(final ItemRowHolder itemRowHolder, final int i) {

            final String sectionName = dataList.get(i).getHeaderTitle();
            final String ammount = dataList.get(i).getammount();
            final String quantity = dataList.get(i).getquantity();
            final String category = dataList.get(i).getcategory();
            final String subcat = dataList.get(i).getsubcategory();
            final String desc = dataList.get(i).getdescription();

            ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

            itemRowHolder.itemTitle.setText(sectionName);
            itemRowHolder.ammount.setText("Ammount-:$" + ammount);
            itemRowHolder.quantity.setText("Quantity-:" + quantity);
            itemRowHolder.category1.setText("Category-:" + category);
            itemRowHolder.sub_category.setText("Sub Category-:" + subcat);

            itemRowHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
                }
            });

            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

//            itemRowHolder.recycler_view_list.setHasFixedSize(true);
//            itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            }


            itemRowHolder.itemTitle3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    deleid = dataList.get(i).getHeaderId();

                    delet();
                }
            });


            itemRowHolder.itemTitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String pid = dataList.get(i).getHeaderId();
                    String pname = dataList.get(i).getHeaderTitle();
                    String quantity = dataList.get(i).getquantity();
                    String amount = dataList.get(i).getammount();
                    String desc = dataList.get(i).getdescription();


                    Intent i = new Intent(v.getContext(), EditProduct_Seller.class);

                    i.putExtra("pidd", pid);
                    i.putExtra("pname", pname);
                    i.putExtra("quantiy", quantity);
                    i.putExtra("ammount", amount);
                    i.putExtra("desc", desc);


                    v.getContext().startActivity(i);
                    Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
                }
            });


        }


        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView itemTitle2;
            protected TextView itemTitle3;
            protected TextView itemTitle;
            protected TextView quantity;
            protected TextView ammount;
            protected TextView category1;
            protected TextView sub_category;
            protected RecyclerViewWithNavigationArrows recycler_view_list;


            public ItemRowHolder(View view) {
                super(view);
                this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
                this.recycler_view_list = (RecyclerViewWithNavigationArrows) view.findViewById(R.id.recycler_view_list);
                this.itemTitle2 = (TextView) view.findViewById(R.id.itemTitle2);
                this.itemTitle3 = (TextView) view.findViewById(R.id.itemTitle3);
                this.quantity = (TextView) view.findViewById(R.id.quantity);
                this.ammount = (TextView) view.findViewById(R.id.ammount);
                this.category1 = (TextView) view.findViewById(R.id.category11);
                this.sub_category = (TextView) view.findViewById(R.id.sub_category);

              /*  left1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                            recycler_view_list.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                        } else {
                            recycler_view_list.smoothScrollToPosition(0);
                        }


                    }
                });

                right1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        recycler_view_list.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);


                    }
                });*/
            }

        }


    }


    /* Adapter class SectionListDataAdapter */

    public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

        private ArrayList<SingleItemModel> itemsList;
        private Context mContext;

        public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
            this.itemsList = itemsList;
            this.mContext = context;

        }


        @Override
        public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
            SingleItemRowHolder mh = new SingleItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(final SingleItemRowHolder holder, int i) {

            final SingleItemModel singleItem = itemsList.get(i);
            holder.tvTitle.setText(singleItem.getSt_name());


            Picasso.get().load("https://printindiamart.com/public/photo/" + singleItem.getSt_imgfile()).into(holder.itemImage);

            System.out.println("immmmg" + "http://filfox.zaptas.com/categorylogo/" + singleItem.getSt_imgfile());
            System.out.println("im000mg" + singleItem.getSt_imgfile());


            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  ser_typee = singleItem.getSt_subcategory_type();
                    ser_cattid = singleItem.getSt_cat_id();
                    String v_subcategory_id = singleItem.getSt_subcategory_id();

                    String v_name = singleItem.getSt_name();
                    Intent in = new Intent(v.getContext(), ViewAllProduct.class);
                    in.putExtra("prd_id", v_subcategory_id);
                    in.putExtra("prd_name", v_name);
                    v.getContext().startActivity(in);*/
               /*     String v_cname = singleItem.getSt_cname();
                    String v_decription = singleItem.getSt_decription();
                    String v_imgfile = singleItem.getSt_imgfile();
                    String v_status = singleItem.getSt_status();
                    String v_subcategory_type = singleItem.getSt_subcategory_type();
                    String v_cat_data = singleItem.getSt_cat_data();
                    String cat_inte_catname = singleItem.getSt_cat_data();
                    String cat_inte_subcatname = singleItem.getSt_subcategory_type();
                    String cat_inte_catid = ser_cattid;*/

               /*     if(ser_typee.equalsIgnoreCase("paid")){
                        Toast.makeText(v.getContext(), "paid", Toast.LENGTH_LONG).show();


                        String v_subcategory_id = singleItem.getSt_subcategory_id();
                        String v_name = singleItem.getSt_name();
                        String v_cname = singleItem.getSt_cname();
                        String v_decription = singleItem.getSt_decription();
                        String v_imgfile = singleItem.getSt_imgfile();
                        String v_status = singleItem.getSt_status();
                        String v_subcategory_type = singleItem.getSt_subcategory_type();
                        String v_cat_data = singleItem.getSt_cat_data();
                        String cat_inte_catname = singleItem.getSt_cat_data();
                        String cat_inte_subcatname = singleItem.getSt_subcategory_type();
                        String cat_inte_catid = ser_cattid;



                        String id = singleItem.getSt_name();
                        Intent in = new Intent(v.getContext(), Services_Activity.class);

                        in.putExtra("int_subcategory_id", v_subcategory_id);
                        in.putExtra("int_name", v_name);
                        in.putExtra("int_cname", v_cname);
                        in.putExtra("int_decription", v_decription);
                        in.putExtra("int_imgfile", "http://spacenterio.com/subdomain/filfox/categorylogo/" + singleItem.getSt_imgfile());
                        in.putExtra("int_status", v_status);
                        in.putExtra("int_subcategory_type", v_subcategory_type);
                        in.putExtra("int_cat_data", v_cat_data);
                        in.putExtra("int_catname ", cat_inte_catname);
                        in.putExtra("int_subcategory_name ", cat_inte_subcatname);
                        in.putExtra("cat_inte_catid ", cat_inte_catid);

                        System.out.println("hggjhhhhhg44h1"+cat_inte_catname+cat_inte_subcatname);

                        Methods.saveCategoryIdd2(getContext(),cat_inte_catid);
                        Methods.saveCategoryName2(getContext(),cat_inte_catname);
                        Methods.saveSubCategoryName2(getContext(),cat_inte_subcatname);


                        v.getContext().startActivity(in);

                    }

                    else if(ser_typee.equalsIgnoreCase("unpaid")){

                        Toast.makeText(v.getContext(), "un-paid", Toast.LENGTH_LONG).show();

                        String v_subcategory_id = singleItem.getSt_subcategory_id();
                        String v_name = singleItem.getSt_name();
                        String v_cname = singleItem.getSt_cname();
                        String v_decription = singleItem.getSt_decription();
                        String v_imgfile = singleItem.getSt_imgfile();
                        String v_status = singleItem.getSt_status();
                        String v_subcategory_type = singleItem.getSt_subcategory_type();
                        String v_cat_data = singleItem.getSt_cat_data();

                        String id = singleItem.getSt_name();
                        Intent in = new Intent(v.getContext(), Apply_Services.class);

                        in.putExtra("int_subcategory_id", v_subcategory_id);
                        in.putExtra("int_name", v_name);
                        in.putExtra("int_cname", v_cname);
                        in.putExtra("int_decription", v_decription);
                        in.putExtra("int_imgfile", "http://spacenterio.com/subdomain/filfox/categorylogo/" + singleItem.getSt_imgfile());
                        in.putExtra("int_status", v_status);
                        in.putExtra("int_subcategory_type", v_subcategory_type);
                        in.putExtra("int_cat_data", v_cat_data);

                        v.getContext().startActivity(in);

                    }*/


                }


            });
        }

        @Override
        public int getItemCount() {
            return (null != itemsList ? itemsList.size() : 0);
        }

        public class SingleItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView tvTitle;
            protected TextView type;
            protected ImageView itemImage;
            protected TextView pprice;
            protected TextView pprod_code;


            public SingleItemRowHolder(final View view) {
                super(view);
                this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
                this.pprice = (TextView) view.findViewById(R.id.price);
                this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

            }
        }


    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((ManageProduct.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = ManageProduct.getDataFromWeb1();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        //JSONObject obj=jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("product");
                        lenArray = array.length();

                        if (lenArray > 0) {


                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                str_cat_data = innerObject.getString("pname");
                                str_cat_id = innerObject.getString("pid");
                                ammount = innerObject.getString("pamount");
                                quantity = innerObject.getString("pqty");
                                desc = innerObject.getString("pdescription");

                                System.out.println("jai_rajputana1_prductname" + str_cat_data + str_cat_id);


                            /*    Methods.saveCategoryIdd(getContext(),str_cat_id);
                                Methods.saveCategoryNamee(getContext(),str_cat_data);*/


                                System.out.println("lenArray:" + jIndex);
                                SectionDataModel_Seller dm = new SectionDataModel_Seller();
                                dm.setHeaderTitle(str_cat_data);
                                dm.setHeaderId(str_cat_id);
                                singleItem = new ArrayList<SingleItemModel>();

                                JSONArray array1 = jsonObject.getJSONArray("product_details");

                                int lenArray1 = array1.length();
                                if (lenArray1 > 0) {

                                    for (int jIndex1 = 0; jIndex1 < lenArray1; jIndex1++) {
                                        JSONObject innerObject1 = array1.getJSONObject(jIndex1);

                                        SingleItemModel_Seller actor = new SingleItemModel_Seller();
                                        SectionDataModel_Seller actor2 = new SectionDataModel_Seller();

                                        str_subcategory_id = innerObject1.getString("product_id");

                                        cat_id = innerObject1.getString("cat_id");
                                        subcat_id = innerObject1.getString("subcat_id");
                                        desc = innerObject1.getString("product_id");
                                        category1 = innerObject1.getString("category_name");
                                        subcate = innerObject1.getString("subcat_name");
                                        JSONArray array2 = innerObject1.getJSONArray("pro_image");
                                        if (str_subcategory_id.equals(str_cat_id)) {


                                            int lenArray2 = array2.length();
                                            if (lenArray1 > 0) {

                                                for (int jIndex2 = 0; jIndex2 < lenArray2; jIndex2++) {
                                                    JSONObject innerObject2 = array2.getJSONObject(jIndex2);


                                                    str_imgfile = innerObject2.getString("pimage");
                                                    //    str_status = innerObject1.getString("status");
                                                    // str_subcategory_type = innerObject1.getString("subcategory_type");


                                                    singleItem.add(new SingleItemModel(str_subcategory_id, str_name, str_cname, str_decription, str_imgfile, str_status, str_subcategory_type, str_cat_data, str_cat_id, product_user_n));


                                                    actor.setSt_subcategory_id(str_subcategory_id);
                                                    actor.setSt_name(str_name);
                                                    actor.setSt_cname(str_cname);
                                                    actor.setSt_decription(desc);
                                                    actor.setSt_imgfile(str_imgfile);
                                                    dm.setammount(ammount);
                                                    //sub cat name----------------------------->
                                                    // actor.setSt_subcategory_type(subcate);
//sub cat id------------------------------->
                                                    actor.setSt_cat_data(subcat_id);
                                                    actor.setcat_id(cat_id);
                                                    dm.setcategory(category1);
                                                    dm.setquantity(quantity);
                                                    dm.setsubcategory(subcate);
                                                    dm.setdescription(desc1);
                                                }
                                            }
                                        }

                                    }
                                }
                                dm.setAllItemsInSection(singleItem);
                                allSampleData.add(dm);

                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                // Log.i(MainActivity.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if (allSampleData.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {

            }
        }
    }

    public static JSONObject getDataFromWeb1() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/products_by_sellerid/" + se_id)
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
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
                        apimsg = jsonObject.getString("msg");
                        apierror = jsonObject.getString("error");

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

            Toast.makeText(ManageProduct.context, apimsg, Toast.LENGTH_LONG).show();
            if (apierror.equals("false")) {
                Intent intent = new Intent(ManageProduct.context, ManageProduct.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ManageProduct.context.startActivity(intent);
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

                    .url("https://printindiamart.com/public/api/product_delete/" + deleid)
                    .build();

            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {

        }
        return null;
    }

    public void delet() {
        LayoutInflater layoutInflater = (LayoutInflater) ManageProduct.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.delet_product_dailog, null);

        closePopupBtn = (Button) customView.findViewById(R.id.dialogButtonOK);
        delet = (Button) customView.findViewById(R.id.dialogButtonOK1);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(rel1, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetData_deletBanner().execute();
            }
        });
    }
}
