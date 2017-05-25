/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.content.Context;
import android.util.Log;

import weihuagu.com.jian.ui.view.CustomWebView;

/**
 * Created by root on 17-5-14.
 */
public class WebViewFactory {
    private Context context;

    public WebViewFactory(Context context) {
        this.context = context;
    }

    public CustomWebView createWebView(String openmethed){

        CustomWebView tmpwebveiw=new CustomWebView(context);
        if(openmethed.equals("front")){
            BrowserContainer.addCurrent(tmpwebveiw);
        }else {
            tmpwebveiw.loadUrl(openmethed);
            BrowserContainer.add(tmpwebveiw);
        }

        Log.i("createnew","webview["+openmethed+"]");
        return tmpwebveiw;

    }
}
