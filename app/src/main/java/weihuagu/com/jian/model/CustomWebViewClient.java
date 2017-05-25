/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-5-11.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.design.widget.Snackbar;
import android.view.View;



import java.util.Stack;

import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;

public class CustomWebViewClient extends WebViewClient{

    PhoneUrlBar urlbar=null;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context=null;
    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }

    public void setUrlbarUrl(String url){
        this.urlbar.setUrl(url);

    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.startsWith("http://")| url.startsWith("https://") ){
            view.loadUrl(url);
            return false;
        }
        else{
            if(view instanceof CustomWebView)
            showSkitSnackbar((CustomWebView)view,url);
        }
        return super.shouldOverrideUrlLoading(view,url);
    }



    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {




        Snackbar.make(view,"请注意这个ssl证书过期或有问题", Snackbar.LENGTH_SHORT).show();
        super.onReceivedSslError(view,handler,error);

    }




    public void onPageFinished(WebView view, String url)
    {

        super.onPageFinished(view, url);
        setUrlbarUrl(view.getUrl());
        urlbar.hideUrl();
        urlbar.setTitle(view.getTitle());
    }


    private void showSkitSnackbar(CustomWebView view, String url){
        String html="<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\"><meta content=\"telephone=no\" name=\"format-detection\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>跳转到app</title></head><body><p><h4>你可以点击Snackbar右侧的跳转,跳转到相应的app</h4><h4>或者你也可以滑掉Snackbar不进行跳转</h4><h5>跳转的url为"+url+"<h5>你可以通过查看url中的scheme确定会跳转到的app</h5></body></html>";
        view.loadDataWithBaseURL(null,html,"text/html","UTF-8",null);
        Stack<String> history=view.getUrlhistorystack();
        history.push("jian://tiaozhuan");
        Snackbar.make(view,"跳转到app提示", Snackbar.LENGTH_INDEFINITE).setAction("跳转", new SnackbarButton(view,url)).show();

        
    }



    class SnackbarButton implements  View.OnClickListener{

        WebView webview=null;
        String url=null;

        public SnackbarButton(WebView webview,String url) {
            this.webview = webview;
            this.url=url;
        }

        @Override
        public void onClick(View view) {

            if(webview!=null && url!=null){

                try{
                    Intent intent =Intent.parseUri(url,Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    webview.getContext().startActivity(intent);
                    Log.v("loadotherapp",intent.toString());

                }catch (Exception e){
                    Log.v("loadnonomal",e.getMessage());
                }


            }

        }
    }





}
