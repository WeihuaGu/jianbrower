/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import weihuagu.com.jian.R;
import weihuagu.com.jian.ui.view.ItemLongClickedPopWindow;

/**
 * Created by root on 17-3-22.
 */
public class WebViewListener implements View.OnTouchListener,View.OnLongClickListener{
    int downX;
    int downY;
    ItemLongClickedPopWindow itemLongClickedPopWindow;
    String saveImgUrl;
    Context context=null;

    public WebViewListener(Context context) {
        this.context = context;
    }


    public void hindleIMAGE_TYPE(String ImgUrl,View v){
        // 获取图片的路径
        saveImgUrl =ImgUrl;
        //通过GestureDetector获取按下的位置，来定位PopWindow显示的位置

        itemLongClickedPopWindow = new ItemLongClickedPopWindow(context,
                ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW,
                300,200);

        itemLongClickedPopWindow.showAtLocation(v, Gravity.TOP|Gravity.LEFT, downX, downY + 10);

        itemLongClickedPopWindow.getView(R.id.item_longclicked_save)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(saveImgUrl!=null|saveImgUrl.equals("")){
                            saveImage();
                        }
                    }
                });

    }

    public void saveImage(){
        DownloadManager downloadManager;
        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadItem request=new DownloadItem(saveImgUrl);
        downloadManager.enqueue(request);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        downX = (int) motionEvent.getX();
        downY = (int) motionEvent.getY();
        return false;
    }




    @Override
    public boolean onLongClick(View v) {
        WebView.HitTestResult result = ((WebView)v).getHitTestResult();
        Log.v("longclick type",Integer.toString(result.getType()));

        if (null == result)
            return false;

        int type = result.getType();

        if (type == WebView.HitTestResult.UNKNOWN_TYPE)
            return false;
        if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {

        }
        // 相应长按事件弹出菜单

        // 这里可以拦截很多类型，我们只处理图片类型就可以了
        switch (type) {
            case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                break;
            case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                break;
            case WebView.HitTestResult.GEO_TYPE: // TODO
                break;
            case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                break;
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                break;
            case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                Log.v("longpress","image"+type);
                this.hindleIMAGE_TYPE(result.getExtra(),v);
                break;
            default:
                break;
        }

        return true;

    }




}
