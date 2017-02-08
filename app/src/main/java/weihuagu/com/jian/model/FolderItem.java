/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

public class FolderItem {
	
	private long mId;
	private String mTitle;
	
	public FolderItem(long id, String title) {
		mId = id;
		mTitle = title;
	}
	
	public long getId() {
		return mId;
	}
	
	public String getTitle() {
		return mTitle;
	}

}
