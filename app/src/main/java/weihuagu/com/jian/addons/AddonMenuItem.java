/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.addons;

public class AddonMenuItem {

	private Addon mAddon;
	private String mMenuItem;	
	
	public AddonMenuItem(Addon addon, String menuItem) {
		mAddon = addon;
		mMenuItem = menuItem;
	}
	
	public Addon getAddon() {
		return mAddon;
	}
	
	public String getMenuItem() {
		return mMenuItem;
	}
	
}
