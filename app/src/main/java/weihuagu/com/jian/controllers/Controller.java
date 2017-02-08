/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.controllers;

import java.util.ArrayList;
import java.util.List;

import weihuagu.com.jian.BrowserActivity;
import weihuagu.com.jian.addons.AddonManager;
import weihuagu.com.jian.model.DownloadItem;
import  weihuagu.com.jian.ui.managers.UIManager;

public class Controller { 
	
	/**
	 * Holder for singleton implementation.
	 */
	private static final class ControllerHolder {
		private static final Controller INSTANCE = new Controller();
		/**
		 * Private Constructor.
		 */
		private ControllerHolder() { }
	}
	
	/**
	 * Get the unique instance of the Controller.
	 * @return The instance of the Controller
	 */
	public static Controller getInstance() {
		return ControllerHolder.INSTANCE;
	}
	
	/**
	 * Private Constructor.
	 */
	private Controller() {
		mDownloads = new ArrayList<DownloadItem>();
	}
	
	private UIManager mUIManager;
	private BrowserActivity mMainActivity;
	
	private List<DownloadItem> mDownloads;
	
	private AddonManager mAddonManager;
	
	public void init(UIManager uiManager, BrowserActivity activity) {
		mUIManager = uiManager;
		mMainActivity = activity;
		mAddonManager = new AddonManager(mMainActivity, mUIManager);
	}
	
	public UIManager getUIManager() {
		return mUIManager;
	}
	
	public BrowserActivity getMainActivity() {
		return mMainActivity;
	}

	public List<DownloadItem> getDownloadsList() {
		return mDownloads;
	}
	
	public DownloadItem getDownloadItemById(long id) {
		for (DownloadItem item : mDownloads) {
			if (item.getId() == id) {
				return item;
			}
		}
		
		return null;
	}
	
	public AddonManager getAddonManager() {
		return mAddonManager;
	}
	
}
