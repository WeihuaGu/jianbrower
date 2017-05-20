/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-5-20.
 */
public interface HttpCodeResponse<T> {
    public void onHttpCodeReturnSuccess(T resultlist);
    public void onHttpCodeReturnFailed();
}
