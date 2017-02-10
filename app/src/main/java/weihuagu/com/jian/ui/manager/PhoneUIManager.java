/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;

import weihuagu.com.jian.model.OnPhoneUrlBarEventListener;
import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.util.UrlUtil;


/**
 * Created by root on 17-2-9.
 */
public class PhoneUIManager implements UIManager{

    PhoneUrlBar urlbar=null;
    CustomWebView webview=null;
    OnPhoneUrlBarEventListener urlbarEventhandle=null;


    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }


    public void setWebview(CustomWebView webview) {
        this.webview = webview;
    }

    public PhoneUIManager(PhoneUrlBar urlbar,CustomWebView webview){
        this.urlbar=urlbar;
        this.webview=webview;
        this.initresources();
        this.init();



    }

    @Override
    public void loadUrl(String url) {

    }
    public void initresources(){
        this.webview.getSettings().setJavaScriptEnabled(true); //设置设否支持JavaScript

        this.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }); //设置浏览

        this.urlbarEventhandle=new UrlBarEventHandle();
        this.urlbar.setEventListener(urlbarEventhandle);






    }

    public void init(){
        this.webview.loadUrl("http://m.baidu.com");


    }

    @Override
    public boolean onKeyBack() {
        if(webview.canGoBack()){
            webview.goBack();
            return true;

        }
        else
            return false;
    }

    @Override
    public String getCurrentUrl() {

        return this.webview.getUrl();
    }

    @Override
    public void freshUrl() {
        this.webview.loadUrl(webview.getUrl());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


    class UrlBarEventHandle implements OnPhoneUrlBarEventListener{


        @Override
        public void onVisibilityChanged(boolean urlBarVisible) {

            Log.v("urlbar","visible changed");

            String title=webview.getTitle();
            if(title!=null){
                urlbar.setTitle(title);
            }

        }

        @Override
        public void onUrlValidated() {
            Log.v("urlbar","key enter");
            String openurl= UrlUtil.addressMatch(urlbar.getUrl());
            Log.v("openurlstring",openurl);
            webview.loadUrl(openurl);

        }

        @Override
        public void onGoStopReloadClicked() {

        }

        @Override
        public void onMenuVisibilityChanged(boolean isVisible) {

        }
    }
}
