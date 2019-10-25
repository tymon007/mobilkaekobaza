package com.example.ekobaza;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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
//import org.apache.http.util.EncodingUtils;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView web = (WebView) findViewById(R.id.webview);
        WebSettings websetting = web.getSettings();
        websetting.setJavaScriptEnabled(true);
        websetting.setSupportMultipleWindows(true);

        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                getWindow().setTitle(title);
            }
        });

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        WebView browser  = (WebView) findViewById(R.id.webview);
        browser.addJavascriptInterface(new WebInterface(this), "Android");
        web.loadUrl("http://31.183.0.134:8000");
    }

    public class BrowserScreen extends AppCompatActivity {

        private WebView webView;
        private String url = "https://31.183.0.134";

        private ValueCallback<Uri> mUploadMessage;
        private final static int FILECHOOSER_RESULTCODE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.browser_activity);
            initFields();
            setListeners();
        }

        public void initFields() {
            webView = (WebView) findViewById(R.id.webview);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setAllowFileAccess(true);
        }

        @SuppressLint("AddJavascriptInterface")
        public void setListeners() {
            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    webView.loadUrl("about:blank");

                    view.clearHistory();
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {

                }

                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

                }
            });

            webView.loadUrl(url);

            final MyJavaScriptInterface myJavaScriptInterface
                    = new MyJavaScriptInterface(this);
            webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        }

        @Override
        public void onBackPressed() {
            // TODO Auto-generated method stub

            if (webView.canGoBack() == true) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
        }


        public class MyJavaScriptInterface {
            Context mContext;

            MyJavaScriptInterface(Context c) {
                mContext = c;
            }

            @JavascriptInterface
            public void showToast(String toast) {
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                webView.loadUrl("javascript:document.getElementById(\"Button3\").innerHTML = \"bye\";");
            }

            @JavascriptInterface
            public void openAndroidDialog() {
                AlertDialog.Builder myDialog
                        = new AlertDialog.Builder(BrowserScreen.this);
                myDialog.setTitle("DANGER!");
                myDialog.setMessage("You can do what you want!");
                myDialog.setPositiveButton("ON", null);
                myDialog.show();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent intent) {
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null
                        : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }
}

class WebInterface{
    Context mContext;

    WebInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void playSound(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void pauseSound(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
