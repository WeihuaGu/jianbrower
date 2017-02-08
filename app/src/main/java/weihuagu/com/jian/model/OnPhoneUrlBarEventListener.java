/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-2-8.
 */
public interface OnPhoneUrlBarEventListener {
    void onVisibilityChanged(boolean urlBarVisible);

    void onUrlValidated();

    void onGoStopReloadClicked();

    void onMenuVisibilityChanged(boolean isVisible);
}
