/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import weihuagu.com.jian.model.OnCustemWebViewEventListener;

/**
 * Created by root on 17-2-9.
 */
public class CustomWebView extends WebView {

    private OnCustemWebViewEventListener mEventListener = null;
    public CustomWebView(Context context) {
        super(context);

    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initSetting();
    }


    public void initSetting(){




    }

}
