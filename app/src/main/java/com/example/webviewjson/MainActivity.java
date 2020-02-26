package com.example.webviewjson;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String webURL = "http://192.168.200.26:8800";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyChromeWebClient(this));

        webView.loadUrl(webURL);
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished (WebView view, String url) {
            if (url.contains("json")) {
                String js = "javascript:console.log(document.body.firstChild.textContent)";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.evaluateJavascript(js, null);
                } else {
                    view.loadUrl(js);
                }
            }
        }
    }

    private static class MyChromeWebClient extends WebChromeClient {
        Context mContext;

        MyChromeWebClient(Context context) {
            mContext = context;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            try {
                JSONObject data = new JSONObject(consoleMessage.message());

                Intent intent = new Intent(mContext, PreviewActivity.class);

                intent.putExtra("name", data.getString("name"));
                intent.putExtra("class", data.getString("class"));

                mContext.startActivity(intent);

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}
