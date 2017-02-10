/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.view.View.OnTouchListener;
public interface UIManager extends OnTouchListener{
    void loadUrl(String url);
    boolean onKeyBack();
    String getCurrentUrl();
    void freshUrl();

}
