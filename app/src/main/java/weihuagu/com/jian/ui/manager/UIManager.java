/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.view.View.OnTouchListener;

import weihuagu.com.jian.model.IWebViewContainer;
import weihuagu.com.jian.ui.view.CustomWebView;

public interface UIManager extends OnTouchListener{
    void setCurrentWebview(CustomWebView webview);
    void loadUrl(String url);
    void loadUrlInHttps(String url);
    boolean onKeyBack();
    String getCurrentUrl();
    void freshUrl();
    void hideurl();

}
