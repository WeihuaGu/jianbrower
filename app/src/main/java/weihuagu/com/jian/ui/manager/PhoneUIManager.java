/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.util.Log;
import android.preference.PreferenceManager;


import weihuagu.com.jian.R;
import weihuagu.com.jian.model.OnPhoneUrlBarEventListener;
import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.util.UrlUtil;
import weihuagu.com.jian.model.MyWebViewDownLoadListener;



/**
 * Created by root on 17-2-9.
 */
public class PhoneUIManager implements UIManager{

    PreferenceManager sharedPref=null;
    PhoneUrlBar urlbar=null;
    CustomWebView webview=null;
    OnPhoneUrlBarEventListener urlbarEventhandle=null;
    Context context=null;


    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }


    public void setWebview(CustomWebView webview) {
        this.webview = webview;
    }

    public PhoneUIManager(PhoneUrlBar urlbar,CustomWebView webview,Context context){
        this.context=context;
        this.urlbar=urlbar;
        this.webview=webview;
        this.initresources();
        this.init();



    }

    @Override
    public void loadUrl(String url) {
        if(UrlUtil.isUrl(url)){
            Log.v("loadurl","is url"+url);
            String openurl= UrlUtil.addressMatch(url);
            this.webview.loadUrl(openurl);

        }
        else{
            this.searchByBing(url);

        }




    }

    public void loadHome(){

    }

    public void searchByBing(String searchkey){
        Log.v("loadurl","not url"+searchkey+"use research");
        String searchurl=context.getString(R.string.SearchUrlBing);
        String openurl=UrlUtil.getSearchUrl(searchurl,searchkey);
        this.webview.loadUrl(openurl);


    }
    @Override
    public void loadUrlInHttps(String url){
        Log.v("loadinhttps:","sorce url"+url);
        String openurl= UrlUtil.addressMatchInHttps(url);
        Log.v("loadinhttps:","open url"+openurl);
        this.webview.loadUrl(openurl);
    }
    public void initresources(){
        this.webview.getSettings().setJavaScriptEnabled(true); //设置设否支持JavaScript
        this.webview.getSettings().setDomStorageEnabled(true);
        this.webview.getSettings().setSupportZoom(true);

        webview.setDownloadListener(new MyWebViewDownLoadListener(this.context));

        this.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
//结束
                super.onPageFinished(view, url);
                setUrlbarUrl(getCurrentUrl());
                urlbar.hideUrl();
                urlbar.setTitle(webview.getTitle());
                Log.i("phonuimanager","pagefinised and title:"+webview.getTitle());
            }


        }); //设置浏览

        this.webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                urlbar.setTitle(webview.getTitle());
                Log.i("phonuimanager","pageprogressChanged and title:"+webview.getTitle());
            }

        });

        this.urlbarEventhandle=new UrlBarEventHandle();
        this.urlbar.setEventListener(urlbarEventhandle);






    }

    public void init(){
        //this.webview.loadUrl("http://m.baidu.com");
        this.urlbar.showUrl();
        this.urlbar.getUrlFocus();

        PreferenceManager.getDefaultSharedPreferences(this.context);


    }

    public void setUrlbarUrl(String url){
        this.urlbar.setUrl(url);

    }

    @Override
    public void hideurl(){
        this.urlbar.hideUrl();
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
            loadUrl(urlbar.getUrl());

        }

        @Override
        public void onGoStopReloadClicked() {

        }

        @Override
        public void onMenuVisibilityChanged(boolean isVisible) {

        }
    }
}
