package com.zaptas.printindiamart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.zaptas.printindiamart.actor.SlideActor;
import com.zaptas.printindiamart.models.SectionDataModel;
import com.zaptas.printindiamart.models.SingleItemModel;
import com.zaptas.printindiamart.seller.Dashboard_Seller;
import com.zaptas.printindiamart.util.Methods;
import com.zaptas.printindiamart.wideget.RecyclerViewWithNavigationArrows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout mDemoSlider;
    private static Response response1;
    static String usertype, user_id;
    Button showPopupBtn, closePopupBtn, delet, topOfPageButton;
    EditText mobileno;
    int lenArray;
    NestedScrollView nestedScrollView;
    ArrayList<SingleItemModel> singleItem;
    private static Response sli_response;
    SlideActor sli_actor;
    private static RecyclerView recyclerView1;
    static String str_imgfile, str_decription, user_n, category, str_cname, str_status, product_user_n, str_subcategory_type, str_name, pricev, str_subcategory_id, str_cat_data, str_cat_id;
    static String ser_typee, ser_cattid;
    HashMap<String, String> file_maps;
    JSONObject jsonObject;
    static String sli_image, sli_name;
    private ArrayList<SlideActor> sli_list;
    ArrayList<SectionDataModel> allSampleData;
    RecyclerViewDataAdapter adapter;
    PopupWindow popupWindow;
    RelativeLayout rel1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usertype = Methods.getUSERTYPE(this);
        user_id = Methods.getUSERID(this);
        hideItem();

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        allSampleData = new ArrayList<SectionDataModel>();
        recyclerView1 = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView1.setHasFixedSize(true);
        adapter = new RecyclerViewDataAdapter(this.getApplication(), allSampleData);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setAdapter(adapter);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        topOfPageButton = (Button) findViewById(R.id.topOfPageButton);

        sli_list = new ArrayList<>();
//        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
//        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
//        mDemoSlider.setDuration(4000);
//        mDemoSlider.addOnPageChangeListener(this);
//        mDemoSlider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        rel1 = (RelativeLayout) findViewById(R.id.rel1);
        new GetDataTaskslider().execute();
        new GetDataTask().execute();

        ImageSlider slider = (ImageSlider) findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider5, ScaleTypes.FIT));

        slider.setImageList(slideModels, ScaleTypes.FIT);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        topOfPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedScrollView.smoothScrollTo(0, 0);
            }
        });


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homeNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homeNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeNavigation:
                        return true;

                    case R.id.myAccount:

                        Intent intent = new Intent(MainActivity.this, User_Mobile.class);
                        startActivity(intent);
                        return true;

                    case R.id.becomeSeller:
                        usertype = Methods.getUSERTYPE(getApplicationContext());
                        user_id = Methods.getUSERID(getApplicationContext());
                        if (user_id == null) {
                            Intent intent2 = new Intent(MainActivity.this, SellerLogin.class);
                            startActivity(intent2);
                        } else {
                            if (usertype.equals("seller")) {
                                Intent intent3 = new Intent(MainActivity.this, Dashboard_Seller.class);
                                startActivity(intent3);
                            }
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

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

    class GetDataTaskslider extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog = new ProgressDialog((MainActivity.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            jsonObject = MainActivity.getDataFromWeb12();

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
                                String id1 = innerObject.getString("id");
                                //    sli_name = innerObject.getString("SliderName");
                                sli_image = innerObject.getString("image");
                                sli_name = "";
                                sli_image = "https://printindiamart.com/public/photo/" + sli_image;
                                sli_actor.setName(sli_name);
                                sli_actor.setImage(sli_image);
                                sli_list.add(sli_actor);

                                try {
                                    file_maps = new HashMap<String, String>();
                                    file_maps.put(sli_name, sli_image);

                                    for (final String name1 : file_maps.keySet()) {
                                        TextSliderView textSliderView = new TextSliderView(getApplicationContext());

                                        textSliderView
                                                .description(name1)
                                                .image(file_maps.get(name1))
                                                .setScaleType(BaseSliderView.ScaleType.Fit);

                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle().putString("extra", name1);
                                        mDemoSlider.addSlider(textSliderView);

                                        textSliderView.bundle(new Bundle());
                                        textSliderView.getBundle().putString("extra", name1);
                                        mDemoSlider.addSlider(textSliderView);

                                    }

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
                    .url("https://printindiamart.com/public/api/get_slider")
                    .build();
            sli_response = client.newCall(request).execute();
            return new JSONObject(sli_response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            // Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        MenuItem user = menu.findItem(R.id.user);
        MenuItem seller = menu.findItem(R.id.seller);
        MenuItem logout = menu.findItem(R.id.logout);
        if (user_id == null) {
            seller.setVisible(true);
            user.setVisible(true);
            logout.setVisible(false);
        }
        else {
            if (usertype.equals("user")) {
                seller.setVisible(false);
                user.setVisible(false);
                logout.setVisible(true);
            } else if (usertype.equals("seller")) {
                seller.setVisible(false);
                user.setVisible(false);
                logout.setVisible(true);
            } else if (usertype.equals("zap")) {
                seller.setVisible(true);
                user.setVisible(true);
                logout.setVisible(false);
            }
        }
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {


            Intent intent = new Intent(MainActivity.this, Search_Activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideItem() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.user).setVisible(false);

        if (user_id == null) {
            //   nav_Menu.findItem(R.id.seller).setVisible(true);
            nav_Menu.findItem(R.id.user).setVisible(true);
            nav_Menu.findItem(R.id.logout1).setVisible(false);
        } else {
            if (usertype.equals("user")) {
                nav_Menu.findItem(R.id.seller).setVisible(false);
                nav_Menu.findItem(R.id.user).setVisible(false);
                nav_Menu.findItem(R.id.logout1).setVisible(true);
            } /*else if (usertype.equals("seller")) {
               nav_Menu.findItem(R.id.seller).setVisible(true);
               nav_Menu.findItem(R.id.user).setVisible(false);
               nav_Menu.findItem(R.id.logout1).setVisible(true);
           }*/ else if (usertype.equals("zap")) {
                nav_Menu.findItem(R.id.seller).setVisible(true);
                nav_Menu.findItem(R.id.user).setVisible(true);
                nav_Menu.findItem(R.id.logout1).setVisible(false);
            }
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
      /*  MenuItem user = item.findItem(R.id.user);
        MenuItem seller = menu.findItem(R.id.seller);
        MenuItem logout = menu.findItem(R.id.logout);
        if (user_id == null) {
            seller.setVisible(true);
            user.setVisible(true);
            logout.setVisible(false);
        }
        else {
            if (usertype.equals("user")) {
                seller.setVisible(false);
                user.setVisible(false);
                logout.setVisible(true);
            } else if (usertype.equals("seller")) {
                seller.setVisible(false);
                user.setVisible(false);
                logout.setVisible(true);
            } else if (usertype.equals("zap")) {
                seller.setVisible(true);
                user.setVisible(true);
                logout.setVisible(false);
            }
        }
*/

        if (id == R.id.seller) {
            usertype = Methods.getUSERTYPE(this);
            user_id = Methods.getUSERID(this);
            if (user_id == null) {
                Intent intent = new Intent(MainActivity.this, SellerLogin.class);
                startActivity(intent);
            } else {
                if (usertype.equals("seller")) {
                    Intent intent = new Intent(MainActivity.this, Dashboard_Seller.class);
                    startActivity(intent);
                }
            }
            // Handle the camera action
        }
        if (id == R.id.user) {
            Intent intent = new Intent(MainActivity.this, User_Mobile.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.logout1) {
            Methods.saveUSERTYPE(MainActivity.this, "zap");
            Methods.saveUSERID(MainActivity.this, null);
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_search) {
            Methods.saveUSERTYPE(MainActivity.this, "zap");
            Methods.saveUSERID(MainActivity.this, Methods.getUSERID(this));
            Intent intent = new Intent(MainActivity.this, Search_Activity.class);
            startActivity(intent);
        }
       /* else if (id == R.id.dashboard) {
            Intent intent = new Intent(MainActivity.this, Dashboard_Seller.class);
            startActivity(intent);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {
        LinearLayoutManager linearLayoutManager;
        private ArrayList<SectionDataModel> dataList;
        private Context mContext;

        public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
            ItemRowHolder mh = new ItemRowHolder(v);
            return mh;
        }
 /*   public boolean onCreateOptionsMenu(Menu menu) {
            Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
            tb.inflateMenu(R.menu.activity_main_drawer);

            return true;
        }*/

        @Override
        public void onBindViewHolder(final ItemRowHolder itemRowHolder, final int i) {

            final String sectionName = dataList.get(i).getHeaderTitle();

            ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

            itemRowHolder.itemTitle.setText(sectionName);
            itemRowHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
                }
            });

            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

            // itemRowHolder.recycler_view_list.setHasFixedSize(true);
            //  itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
            }


            itemRowHolder.itemTitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (user_id == null) {
                        delet();

                    } else {
                        String v_cate_ids = dataList.get(i).getHeaderId();
                        String v_cate_data = dataList.get(i).getHeaderTitle();
                        Intent i = new Intent(v.getContext(), ViewAllSubCategory.class);
                        i.putExtra("int_namefdfdf", v_cate_ids);
                        i.putExtra("int_cat_data", v_cate_data);
                        v.getContext().startActivity(i);
                        //     Toast.makeText(getApplicationContext(), "Click view All", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }


        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            protected TextView itemTitle;
            protected TextView itemTitle2;
            protected RecyclerViewWithNavigationArrows recycler_view_list;


            public ItemRowHolder(View view) {
                super(view);
                this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
                this.recycler_view_list = (RecyclerViewWithNavigationArrows) view.findViewById(R.id.recycler_view_list);
                this.itemTitle2 = (TextView) view.findViewById(R.id.itemTitle2);

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


            holder.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (user_id == null) {
                        delet();

                    } else {
                        ser_typee = singleItem.getSt_subcategory_type();
                        ser_cattid = singleItem.getSt_cat_id();
                        String v_subcategory_id = singleItem.getSt_subcategory_id();

                        String v_name = singleItem.getSt_name();
                        Intent in = new Intent(v.getContext(), ViewAllProduct.class);
                        in.putExtra("prd_id", v_subcategory_id);
                        in.putExtra("prd_name", v_name);
                        v.getContext().startActivity(in);
                    }

                }


            });


            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (user_id == null) {
                        delet();

                    } else {
                        ser_typee = singleItem.getSt_subcategory_type();
                        ser_cattid = singleItem.getSt_cat_id();
                        String v_subcategory_id = singleItem.getSt_subcategory_id();

                        String v_name = singleItem.getSt_name();
                        Intent in = new Intent(v.getContext(), ViewAllProduct.class);
                        in.putExtra("prd_id", v_subcategory_id);
                        in.putExtra("prd_name", v_name);
                        v.getContext().startActivity(in);
                    }

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
            dialog = new ProgressDialog((MainActivity.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = MainActivity.getDataFromWeb1();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        JSONObject obj = jsonObject.getJSONObject("data");
                        JSONArray array = obj.getJSONArray("categories");
                        lenArray = array.length();

                        if (lenArray > 0) {


                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                str_cat_data = innerObject.getString("category_name");
                                str_cat_id = innerObject.getString("id");
                                System.out.println("jai_rajputana1_prductname" + str_cat_data + str_cat_id);


                            /*    Methods.saveCategoryIdd(getContext(),str_cat_id);
                                Methods.saveCategoryNamee(getContext(),str_cat_data);*/


                                System.out.println("lenArray:" + jIndex);
                                SectionDataModel dm = new SectionDataModel();
                                dm.setHeaderTitle(str_cat_data);
                                dm.setHeaderId(str_cat_id);
                                singleItem = new ArrayList<SingleItemModel>();

                                JSONArray array1 = innerObject.getJSONArray("children");
                                int lenArray1 = array1.length();
                                if (lenArray1 > 0) {

                                    for (int jIndex1 = 0; jIndex1 < lenArray1; jIndex1++) {
                                        JSONObject innerObject1 = array1.getJSONObject(jIndex1);

                                        SingleItemModel actor = new SingleItemModel();

                                        str_subcategory_id = innerObject1.getString("id");
                                        str_name = innerObject1.getString("subcat_name");
                                        str_cname = innerObject1.getString("parent_id");
                                        //  str_decription = innerObject1.getString("decription");
                                        str_imgfile = innerObject1.getString("simage");
                                        //    str_status = innerObject1.getString("status");
                                        // str_subcategory_type = innerObject1.getString("subcategory_type");


                                        singleItem.add(new SingleItemModel(str_subcategory_id, str_name, str_cname, str_decription, str_imgfile, str_status, str_subcategory_type, str_cat_data, str_cat_id, product_user_n));
                                        actor.setSt_subcategory_id(str_subcategory_id);
                                        actor.setSt_name(str_name);
                                        actor.setSt_cname(str_cname);
                                        actor.setSt_decription(str_decription);
                                        actor.setSt_imgfile(str_imgfile);
                                        actor.setSt_status(str_status);
                                        actor.setSt_subcategory_type(str_subcategory_type);

                                        actor.setSt_cat_data(str_cat_data);
                                        actor.setSt_cat_id(str_cat_id);


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
                    .url("https://printindiamart.com/public/api/get_allcategory")
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
    /* Adapter class SectionListDataAdapter */


    public void delet() {
        Intent intent = new Intent(MainActivity.this, User_Mobile.class);

        startActivity(intent);
      /*  LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.user_mobile_dailog,null);

        closePopupBtn = (Button) customView.findViewById(R.id.dialogButtonOK);
        delet = (Button) customView.findViewById(R.id.dialogButtonOK1);
        delet = (Button) customView.findViewById(R.id.dialogButtonOK1);
        mobileno = (EditText) customView.findViewById(R.id.text);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
                user_n=mobileno.getText().toString();
                new GetUser().execute();
            }
        });*/
    }


    class GetUser extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((MainActivity.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = getDataFromWeb4();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj=jsonObject.getJSONObject("msg");

                        String user_id = jsonObject.getString("data");
                        Methods.saveUSERTYPE(MainActivity.this, "user");
                        Methods.saveUSERID(MainActivity.this, user_id);
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);

                        startActivity(intent);

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


        }
    }

    public static JSONObject getDataFromWeb4() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/user_mobile/" + user_n)
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}
