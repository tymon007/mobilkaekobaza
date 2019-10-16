package com.example.ekobaza;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView web = (WebView) findViewById(R.id.webview);
        WebSettings websetting = web.getSettings();
        websetting.setJavaScriptEnabled(true);
       // WebView web = (WebView)findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setSupportMultipleWindows(true); // This forces ChromeClient enabled.

    web.setWebChromeClient(new WebChromeClient(){
        @Override
        public void onReceivedTitle(WebView view, String title) {
            getWindow().setTitle(title); //Set Activity tile to page title.
    }
});

    web.setWebViewClient(new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
    }
});



        web.loadUrl("http://31.183.0.134:8000");
    }


}
