package com.autoreload.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    WebView webview;
    EditText urlInput, reloadInput, timeInput;
    Button startButton;
    TextView statusText;

    Handler handler = new Handler();

    int reloadCount = 0;
    int maxReload = 0;
    int interval = 0;

    Runnable reloadTask = new Runnable() {
        @Override
        public void run() {

            if(reloadCount < maxReload){

                webview.clearCache(true);

                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookies(null);

                webview.reload();

                reloadCount++;

                statusText.setText("Reload: " + reloadCount);

                handler.postDelayed(this, interval * 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = findViewById(R.id.webview);
        urlInput = findViewById(R.id.url_input);
        reloadInput = findViewById(R.id.reload_input);
        timeInput = findViewById(R.id.time_input);
        startButton = findViewById(R.id.start_button);
        statusText = findViewById(R.id.status_text);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        startButton.setOnClickListener(v -> {

            reloadCount = 0;

            maxReload = Integer.parseInt(reloadInput.getText().toString());

            interval = Integer.parseInt(timeInput.getText().toString());

            webview.loadUrl(urlInput.getText().toString());

            handler.postDelayed(reloadTask, interval * 1000);
        });
    }
}
