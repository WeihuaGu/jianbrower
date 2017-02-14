/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;
import android.content.Context;
import android.webkit.DownloadListener;
import android.app.DownloadManager;

public class MyWebViewDownLoadListener implements DownloadListener{
    private Context context=null;
    public MyWebViewDownLoadListener(Context context){
        this.context=context;

    }
    @Override
    public void onDownloadStart(String url, String s1, String s2, String s3, long l) {

        DownloadManager downloadManager;
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadItem request=new DownloadItem(url);
        downloadManager.enqueue(request);


    }
}
