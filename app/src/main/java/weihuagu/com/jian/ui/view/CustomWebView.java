/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.util.Log;
import java.lang.reflect.Method;

import weihuagu.com.jian.model.IWebViewContainer;
import weihuagu.com.jian.model.MyWebViewDownLoadListener;
import weihuagu.com.jian.model.OnCustemWebViewEventListener;
import weihuagu.com.jian.model.WebViewListener;
import java.util.UUID;

/**
 * Created by root on 17-2-9.
 */
public class CustomWebView extends WebView implements IWebViewContainer{


    protected UUID mUUID;
    private OnCustemWebViewEventListener mEventListener = null;
    WebSettings websetting=null;
    Context context;
    public CustomWebView(Context context) {
        super(context);
        this.logWebview();
        this.context=context;
        this.init();

    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.logWebview();
        this.context=context;
        this.init();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void init(){
        this.setUUID();
        this.initWebView();
        this.initSetting();
    }

    private synchronized void initWebView() {
        setDownloadListener(new MyWebViewDownLoadListener(this.context));
        WebViewListener listerner=new WebViewListener(context);
        setOnTouchListener(listerner);
        setOnLongClickListener(listerner);

    }


    public synchronized void initSetting(){

        websetting = getSettings();
        websetting.setJavaScriptEnabled(true); //设置设否支持JavaScript
        websetting.setDomStorageEnabled(true);
        websetting.setSupportZoom(true);
        websetting.setBuiltInZoomControls(true);
        websetting.setDisplayZoomControls(false);//隐藏Zoom缩放按钮


    }
    public void logWebview(){


    }

    private void setUUID(){
        mUUID = UUID.randomUUID();
    }


    @Override
    public UUID getUUID(){
        return mUUID;

    }

    @Override
    public void destroyWebView() {
        this.destroy();
    }

    public void setNightMode(String mode){


    }

    @Override
    public void destroy(){
    //flushMessageQueue();
    clearCache(true);
    clearFormData();
    clearMatches();
    clearSslPreferences();
    clearDisappearingChildren();
    clearHistory();
    //@Deprecated
    //clearView();
    clearAnimation();
    loadUrl("about:blank");
    removeAllViews();
    freeMemory();
    super.destroy();
   }



}
