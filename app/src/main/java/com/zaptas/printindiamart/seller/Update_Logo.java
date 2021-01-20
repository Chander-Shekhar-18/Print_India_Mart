package com.zaptas.printindiamart.seller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zaptas.printindiamart.R;
import com.zaptas.printindiamart.startingscreen.SellerLogin;
import com.zaptas.printindiamart.util.Methods;

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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Update_Logo extends AppCompatActivity {
    private String userChoosenTask;
    public static String document_type;
    private Uri mCapturedImageURI;
    private static final int CAMERA_REQUEST = 1888;
    private int SELECT_FILE = 1;
    static int sk = 1;
    String[] perms = { "android.permission.CAMERA"};
    String encodedImage;
    public static String userid;
    String realPath="";
    int permsRequestCode = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    String base64img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        userid= Methods.getUSERID(this);
        sk=1;
    }
    public  void submit(View arg){
        if(sk==1){
            Toast.makeText(getApplicationContext(), "Please Select image", Toast.LENGTH_LONG).show();
        }
        else {
            DocumentData asyncT = new DocumentData();
            asyncT.execute();
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
    public void upload_image(View arg){

    selectImage();


    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Update_Logo.this);
        builder.setTitle("Add Photo of "+document_type);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(Update_Logo.this);

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
        boolean result = Utility.checkPermission(Update_Logo.this);
        if (result)
        {
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
        boolean result = Utility.checkPermission_camera(Update_Logo.this);
        if (result)
        {
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
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                //  boolean locationAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                //   boolean cameraAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;

                break;

        }

    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;

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




        System.out.println("realPath_realPath"+realPath);

        Bitmap converetdImage = getResizedBitmap(bm, 720);
sk=2;
        //imageView.setImageBitmap(converetdImage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        converetdImage.compress(Bitmap.CompressFormat.PNG, 70, outputStream);
        byte[] b = outputStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        base64img="data:image/png;base64,"+encodedImage;

    }
    public class DocumentData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        protected void onPreExecute() {

            super.onPreExecute();
            dialog = new ProgressDialog(Update_Logo.this);
            dialog.setMessage("Image Uploading...");
            dialog.show();
            dialog.setCancelable(false);

        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("https://printindiamart.com/public/api/seller_update_logo"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
               /* postDataParams.put("name", "abc");
                postDataParams.put("email", "abc@gmail.com");*/


                postDataParams.put("id", userid);

                postDataParams.put("logo", "data:image/jpeg;base64," + encodedImage);





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
            Methods.saveUSERTYPE(Update_Logo.this,"zap");
            Methods.saveUSERID(Update_Logo.this,null);
            Intent intent = new Intent(Update_Logo.this, SellerLogin.class);

            startActivity(intent);
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sk=2;
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
            System.out.print("Imgpath - "+picturePath);

            Bitmap bm = BitmapFactory.decodeFile(picturePath);
/*ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();*/

            System.out.println("realPath_realPath"+realPath);

            Bitmap converetdImage = getResizedBitmap(bm, 720);

            //imageView.setImageBitmap(converetdImage);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            converetdImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] b = outputStream.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            //String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            System.out.print("base67--"+encodedImage);
            base64img="data:image/png;base64,"+encodedImage;
          /*  DocumentData asyncT = new DocumentData();
            asyncT.execute();*/






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
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        encodedImage=encImage;
        new DocumentData().execute();


        Log.i("InputStream1", encodedImage);
        return encImage;
    }
}
