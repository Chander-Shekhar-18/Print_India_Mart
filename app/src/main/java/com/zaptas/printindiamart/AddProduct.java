package com.zaptas.printindiamart;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zaptas.printindiamart.models.Cat_Spinner_Model;
import com.zaptas.printindiamart.util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.zaptas.printindiamart.MainActivity.user_id;
import static com.zaptas.printindiamart.MainActivity.usertype;

public class AddProduct extends AppCompatActivity {
    EditText productname, product_quantity, product_ammount, product_desc, product_feature;
    Spinner spinner_one, spinner_two;
    ImageView iv_white;

    public static int img1 = 1, img3 = 1, img2 = 1;
    private static Response response1;
    final Context context = this;
    public static String document_type;
    private String userChoosenTask;
    private Uri mCapturedImageURI;
    private static final int CAMERA_REQUEST = 1888;
    private int SELECT_FILE = 1;
    String[] perms = {"android.permission.CAMERA"};
    String encodedImage, encodedImage2, encodedImage3;
    public static String userid;
    String realPath = "";
    int permsRequestCode = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    static int img = 0;
    String base64img, base64img2, base64img3;
    public ArrayList<Cat_Spinner_Model> CustomListViewValuesArr = new ArrayList<Cat_Spinner_Model>();

    JSONObject jsonObject;
    static String cat_id, sub_catid;
    static ArrayAdapter<String> adapter;
    static ArrayAdapter<String> adapter2;
    static String se_id;
    int lenArray;
    ArrayList<String> category_spinner = new ArrayList<String>();
    ArrayList<String> sub_category_spinner = new ArrayList<String>();
    TextView imgg1, imgg1s, imgg2, imgg2s, imgg3, imgg3s;
    static String pname, pquantity, pamount, pdescrption, pfeature;
    String vari, vari2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);




        spinner_one = (Spinner) findViewById(R.id.spinner1);
        spinner_two = (Spinner) findViewById(R.id.spinner2);

        productname = (EditText) findViewById(R.id.productname);
        product_quantity = (EditText) findViewById(R.id.product_quantity);
        product_ammount = (EditText) findViewById(R.id.product_ammount);
        product_desc = (EditText) findViewById(R.id.product_desc);
        product_feature = (EditText) findViewById(R.id.product_feature);
        imgg1 = (TextView) findViewById(R.id.imgg1);
        imgg1s = (TextView) findViewById(R.id.imgg1s);
        imgg2 = (TextView) findViewById(R.id.imgg2);
        imgg2s = (TextView) findViewById(R.id.imgg2s);
        imgg3 = (TextView) findViewById(R.id.imgg3);
        imgg3s = (TextView) findViewById(R.id.imgg3s);
        imgg1.setVisibility(View.VISIBLE);
        imgg2.setVisibility(View.VISIBLE);
        imgg2.setVisibility(View.VISIBLE);
        img1 = 1;
        img2 = 1;
        img3 = 1;
        se_id = Methods.getUSERID(this);

        iv_white = findViewById(R.id.iv_white);
        iv_white.setOnClickListener(new View.OnClickListener() {
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

        new GetCategorySpinner().execute();

        spinner_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              /*  final Cat_Spinner_Model sched1 = new Cat_Spinner_Model();
                int size = CustomListViewValuesArr.size();
                for (int z=0;z<=size;z++){
                    String catName=sched1.getCompanyName();
                }
*/


                vari = (String) spinner_one.getSelectedItem();
                String[] output = vari.split("-");
                // toast.makeText(getApplicationContext(), "selected item id " + output[1], // toast.LENGTH_LONG).show();
                if (vari == null) {
                } else {
                    if (vari.equals("Select Category")) {
                    } else {
                        cat_id = output[1];
                        new GetSubCategorySpinner().execute();
                    }
                }
                // toast.makeText(getApplicationContext(), "selected item "+vari,// toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vari2 = (String) spinner_two.getSelectedItem();
                String[] output = vari2.split("-");
                // toast.makeText(getApplicationContext(), "selected item id " + output[1], // toast.LENGTH_LONG).show();
                if (vari2 == null) {
                } else {
                    if (vari2.equals("Select Sub-Category")) {
                    } else {
                        sub_catid = output[1];

                    }
                }
                // toast.makeText(getApplicationContext(), "selected item "+vari,// toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void submit_data(View arg) {
        pamount = product_ammount.getText().toString();
        pname = productname.getText().toString();
        pfeature = product_feature.getText().toString();
        pdescrption = product_desc.getText().toString();
        pquantity = product_quantity.getText().toString();

        if (pamount.equals("") || pname.equals("") || pfeature.equals("") || pdescrption.equals("") || pquantity.equals("")) {
            Toast.makeText(getApplicationContext(), "All feild mandatory", Toast.LENGTH_SHORT).show();
        } else {
            if (img1 == 2) {
                DocumentData asyncT = new DocumentData();
                asyncT.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Please upload atlest one image", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void image_upload1(View arg) {
        img = 1;
        selectImage();
    }

    public void image_upload2(View arg) {
        img = 2;
        selectImage();

    }

    public void image_upload3(View arg) {
        img = 3;
        selectImage();

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

    class GetCategorySpinner extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog((AddProduct.this));
            dialog.setMessage("Loading...");

            dialog.show();
            dialog.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = AddProduct.getDataFromWeb1();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj=jsonObject.getJSONObject("msg");
                        JSONArray array = jsonObject.getJSONArray("data");
                        lenArray = array.length();
                        final Cat_Spinner_Model sched = new Cat_Spinner_Model();
                        if (lenArray > 0) {
                            category_spinner.add("Select Category");

                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String cat_sp_id = innerObject.getString("id");
                                String cat_sp_name = innerObject.getString("category_name");
                                category_spinner.add(cat_sp_name + "-" + cat_sp_id);
                                sched.setCompanyName(cat_sp_name);
                                //sched.setImage("image"+i);
                                sched.setUrl(cat_sp_id);

                                /******** Take Model Object in ArrayList **********/
                                CustomListViewValuesArr.add(sched);
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
            adapter = new ArrayAdapter<String>(AddProduct.this, R.layout.category_spinner, category_spinner);
            spinner_one.setAdapter(adapter);

        }
    }

    public static JSONObject getDataFromWeb1() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/get_category")
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }


    class GetSubCategorySpinner extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog1 = new ProgressDialog((AddProduct.this));
            dialog1.setMessage("Loading...");

            dialog1.show();
            dialog1.setCancelable(false);
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = AddProduct.getDataFromWeb2();
            try {
                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        // JSONObject obj=jsonObject.getJSONObject("msg");
                        JSONArray array = jsonObject.getJSONArray("data");
                        lenArray = array.length();
//                        adapter2.clear();
                        if (lenArray > 0) {
                            category_spinner.add("Select Sub-Category");

                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String cat_sp_id = innerObject.getString("id");
                                String cat_sp_name = innerObject.getString("subcat_name");
                                sub_category_spinner.add(cat_sp_name + "-" + cat_sp_id);


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
            dialog1.dismiss();
            adapter2 = new ArrayAdapter<String>(AddProduct.this, R.layout.sub_category_spinner, sub_category_spinner);
            spinner_two.setAdapter(adapter2);

        }
    }

    public static JSONObject getDataFromWeb2() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://printindiamart.com/public/api/get_subcategoryby_id/" + cat_id)
                    .build();
            response1 = client.newCall(request).execute();
            return new JSONObject(response1.body().string());
        } catch (@NonNull IOException | JSONException e) {
            //  Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddProduct.this);
        builder.setTitle("Add Photo of " + document_type);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddProduct.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        boolean result = Utility.checkPermission(AddProduct.this);
        if (result) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
            }
        }
    }

    private void cameraIntent() {
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
        boolean result = Utility.checkPermission_camera(AddProduct.this);
        if (result) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                String fileName = "temp.jpg";
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                mCapturedImageURI = getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);
                takePictureIntent
                        .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:

                //  boolean locationAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                //   boolean cameraAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                break;

        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT < 11)
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
        else if (Build.VERSION.SDK_INT < 19)
            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


        System.out.println("realPath_realPath" + realPath);

        Bitmap converetdImage = getResizedBitmap(bm, 720);

        //imageView.setImageBitmap(converetdImage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        converetdImage.compress(Bitmap.CompressFormat.PNG, 70, outputStream);
        byte[] b = outputStream.toByteArray();


        if (img == 1) {
            img1 = 2;
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            imgg1s.setVisibility(View.VISIBLE);
            imgg1.setVisibility(View.GONE);

        } else if (img == 2) {
            img2 = 2;
            encodedImage2 = Base64.encodeToString(b, Base64.DEFAULT);
            imgg2s.setVisibility(View.VISIBLE);
            imgg2.setVisibility(View.GONE);

        } else if (img == 3) {
            img3 = 2;
            imgg2s.setVisibility(View.VISIBLE);
            imgg2.setVisibility(View.GONE);
            encodedImage3 = Base64.encodeToString(b, Base64.DEFAULT);
        } else {

        }

        base64img = "data:image/png;base64," + encodedImage;

    }

    public class DocumentData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(AddProduct.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/createproduct_post"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               /* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*/

                if(img1 == 2){
                    postDataParams.put("image", "data:image/jpeg;base64,/" + encodedImage);
                }
                if (img2 == 2) {
                    postDataParams.put("image1", "data:image/jpeg;base64," + encodedImage2);
                }
                if (img3 == 2) {
                    postDataParams.put("image3", "data:image/jpeg;base64," + encodedImage3);
                }


                postDataParams.put("pname", pname);
                postDataParams.put("pamount", pamount);
                postDataParams.put("pdescription", pdescrption);
                postDataParams.put("field_name", pfeature);
                postDataParams.put("pqty", pquantity);

                postDataParams.put("parrent_category", sub_catid);
                postDataParams.put("sid", se_id);


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
            dialog.setContentView(R.layout.add_product_dailog);
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
                    Intent intent = new Intent(AddProduct.this, Dashboard_Seller.class);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");


              /*  if(i==1){
                    imageView.setImageBitmap(photo);
                    //  imageView.setImageBitmap(converetdImage);

                }*/

                String encodedImage = encodeImage(photo);

            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor =
                    managedQuery(mCapturedImageURI, projection, null,
                            null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String picturePath = cursor.getString(column_index_data);
            System.out.print("Imgpath - " + picturePath);

            Bitmap bm = BitmapFactory.decodeFile(picturePath);
/*ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();*/

            System.out.println("realPath_realPath" + realPath);

            Bitmap converetdImage = getResizedBitmap(bm, 720);

            //imageView.setImageBitmap(converetdImage);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] b = outputStream.toByteArray();

            if (img == 1) {
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            } else if (img == 2) {
                encodedImage2 = Base64.encodeToString(b, Base64.DEFAULT);

            } else if (img == 3) {
                encodedImage3 = Base64.encodeToString(b, Base64.DEFAULT);
            } else {

            }
            //String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            //System.out.print("base67--" + encodedImage);


            if (img == 1) {
                base64img = "data:image/png;base64," + encodedImage;
            } else if (img == 2) {
                base64img2 = "data:image/png;base64," + encodedImage2;

            } else if (img == 3) {
                base64img3 = "data:image/png;base64," + encodedImage3;
            } else {

            }








/*
            MyImage image = new MyImage();
            image.setTitle("Test");
            image.setDescription(
                    "test take a photo and add it to list view");
            image.setDatetime(System.currentTimeMillis());
            image.setPath(picturePath);
            images.add(image);*/
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        if (img == 1) {
            encodedImage = encImage;
            imgg1s.setVisibility(View.VISIBLE);
            imgg1.setVisibility(View.GONE);
        } else if (img == 2) {
            encodedImage2 = encImage;
            imgg2s.setVisibility(View.VISIBLE);
            imgg2.setVisibility(View.GONE);

        } else if (img == 3) {
            encodedImage3 = encImage;
            imgg3s.setVisibility(View.VISIBLE);
            imgg1.setVisibility(View.GONE);
        } else {

        }
         new DocumentData().execute();


        Log.i("InputStream1", encodedImage);
        return encImage;
    }
}
