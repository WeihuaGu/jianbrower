/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import weihuagu.com.jian.ui.view.CustomWebView;

/**
 * Created by root on 17-5-14.
 */
public class WebViewFactory {
    private Context context;
    AttributeSet attrs;

    public WebViewFactory(Context context,AttributeSet attrs) {
        this.context = context;this.attrs=attrs;
    }

    public CustomWebView createWebView(String openmethed){

        CustomWebView tmpwebveiw=new CustomWebView(context,attrs);
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
