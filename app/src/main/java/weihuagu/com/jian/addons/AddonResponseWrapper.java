/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.addons;

import java.util.List;

import org.tint.addons.framework.Action;

public class AddonResponseWrapper {

	private Addon mAddon;
	private List<Action> mResponse;
	
	public AddonResponseWrapper(Addon addon, List<Action> response) {
		mAddon = addon;
		mResponse = response;
	}
	
	public Addon getAddon() {
		return mAddon;
	}
	
	public List<Action> getResponse() {
		return mResponse;
	}
	
}
