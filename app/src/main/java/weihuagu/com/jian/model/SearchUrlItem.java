/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

public class SearchUrlItem {
	
	private String mName;
	private String mUrl;
	
	public SearchUrlItem(String name, String url) {
		mName = name;
		mUrl = url;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getUrl() {
		return mUrl;
	}
}
