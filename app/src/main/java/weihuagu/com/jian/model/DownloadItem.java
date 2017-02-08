/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.app.DownloadManager.Request;
import android.net.Uri;
import android.os.Environment;

public class DownloadItem extends Request {
	
	private long mId;
	private String mUrl;
	private String mFileName;
	private Boolean mIncognito;
	
	public DownloadItem(String url) {
		super(Uri.parse(url));
		mUrl = url;
		mFileName = mUrl.substring(url.lastIndexOf("/") + 1);
		
		setTitle(mFileName);
		setDescription(mUrl);
		setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mFileName);
	}
	
	public long getId() {
		return mId;
	}
	
	public void setId(long value) {
		mId = value;
	}
	
	public void setFilename(String filename) {
		mFileName = filename;
		setTitle(filename);
		setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
	}
	
	public void setIncognito(Boolean incognito) {
		mIncognito = incognito;
		setVisibleInDownloadsUi(!incognito);
	}
	
	public Boolean isIncognito() {
		return mIncognito;
	}
	
	public String getFileName() {
		return mFileName;
	}
	
	public String getUrl() {
		return mUrl;
	}

}
