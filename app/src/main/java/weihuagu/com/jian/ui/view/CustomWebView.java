/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.view;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by root on 17-2-9.
 */
public class CustomWebView extends WebView {
    public CustomWebView(Context context) {
        super(context);
        this.initSetting();
    }
    public void initSetting(){
        this.getSettings().setJavaScriptEnabled(true); //设置设否支持JavaScript

        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }); //设置浏览

    }

}
