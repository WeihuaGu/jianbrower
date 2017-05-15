/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-5-11.
 */

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;

import weihuagu.com.jian.ui.view.PhoneUrlBar;

public class CustomWebViewClient extends WebViewClient{

    PhoneUrlBar urlbar=null;
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
            return true;
        }
        else{
            try {

                Intent intent =Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                view.getContext().startActivity(intent);
                Log.v("loadotherapp",intent.toString());
            }catch (Exception e){
                Log.v("loadnonomal",e.getMessage());

            }
        }
        return super.shouldOverrideUrlLoading(view,url);
    }




    public void onPageFinished(WebView view, String url)
    {

        super.onPageFinished(view, url);
        setUrlbarUrl(view.getUrl());
        urlbar.hideUrl();
        urlbar.setTitle(view.getTitle());
    }





}
