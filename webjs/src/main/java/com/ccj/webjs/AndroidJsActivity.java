package com.ccj.webjs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

/**
 * js与Android互调，交互
 */
public class AndroidJsActivity extends AppCompatActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        webView.setVerticalScrollbarOverlay(true);
        //设置WebView支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        // 加载本地网页
        webView.loadUrl("file:///android_asset/test.html");

        //在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "smzdmwebaction");

        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());

        //设置监听
        onInvokeJs();
        onInvokeJs2();
    }

    /**
     * App调用JS代码
     */
    private void onInvokeJs() {
        findViewById(R.id.btn_invoke_js).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ((EditText) findViewById(R.id.input_et)).getText().toString();
                //调用js中的函数：showInfoFromApp(msg)
                webView.loadUrl("javascript:showInfoFromApp('" + msg + "')");
            }
        });
    }

    /**
     * App调用JS代码
     */
    private void onInvokeJs2() {
        findViewById(R.id.btn_invoke_js2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用js中的函数：showInfoFromApp()
                webView.loadUrl("javascript:showInfoFromApp()");
            }
        });
    }

}