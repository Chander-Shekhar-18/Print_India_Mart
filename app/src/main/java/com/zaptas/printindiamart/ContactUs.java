package com.zaptas.printindiamart;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ContactUs extends AppCompatActivity {
    private WebView webview;
    private ProgressDialog progressBar;
    AlertDialog alertDialog;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        webview = (WebView) findViewById(R.id.webview);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        progressBar = new ProgressDialog(ContactUs.this);
        progressBar.setMessage("Please Wait Loading.......");
        progressBar.show();

        //    progressBar = ProgressDialog.show(getContext(), "Please Wait Loading.......", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                //header-section transparent pin-style
                //container
                //no-pad-bottom
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('container-fluid')[0].style.display='none'; })()");

                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('row')[0].style.display='none'; })()");
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassname('container-fluid')[0].style.display='none'; })()");
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('footer-bottom black-o-bg')[0].style.display='none'; })()");
                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('breadcrumb-area ptb-50')[0].style.display='none'; })()");
             /*   webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('submit')[0].style.display='none'; })()");

                webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('row')[0].style.display='none'; })()");*/
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(ContactUs.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
              /*  alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/
                alertDialog.show();
            }
        });
        webview.loadUrl("https://printindiamart.com/contactus");


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
 /*   @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"indo@printindiamart.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }*/


}
