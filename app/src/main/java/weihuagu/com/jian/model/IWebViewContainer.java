/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import java.util.UUID;

/**
 * Created by root on 17-3-23.
 */
public interface IWebViewContainer {

    public UUID getUUID();
    public void destroyWebView();

}
