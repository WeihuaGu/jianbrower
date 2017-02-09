/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.view.MotionEvent;
import android.view.View;

import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;

/**
 * Created by root on 17-2-9.
 */
public class PhoneUIManager implements UIManager{

    PhoneUrlBar urlbar=null;
    CustomWebView webview=null;


    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }


    public void setWebview(CustomWebView webview) {
        this.webview = webview;
    }

    public PhoneUIManager(PhoneUrlBar urlbar,CustomWebView webview){
        this.urlbar=urlbar;
        this.webview=webview;
        this.init();



    }

    @Override
    public void loadUrl(String url) {

    }

    public void init(){
        this.webview.loadUrl("http://m.baidu.com");


    }

    @Override
    public boolean onKeyBack() {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
