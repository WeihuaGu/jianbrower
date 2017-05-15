/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-5-11.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import weihuagu.com.jian.BrowserActivity;
import weihuagu.com.jian.ui.view.PhoneUrlBar;

public class CustomWebViewClient extends WebViewClient{

    PhoneUrlBar urlbar=null;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context=null;
    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }

    public void setUrlbarUrl(String url){
        this.urlbar.setUrl(url);

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url.startsWith("http://")| url.startsWith("https://") ){
            view.loadUrl(url);
            return true;
        }
        else{
            try {











                Intent intent =Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                view.getContext().startActivity(intent);
                Log.v("loadotherapp",intent.toString());
            }catch (Exception e){
                Log.v("loadnonomal",e.getMessage());

            }
        }
        return super.shouldOverrideUrlLoading(view,url);
    }




    public void onPageFinished(WebView view, String url)
    {

        super.onPageFinished(view, url);
        setUrlbarUrl(view.getUrl());
        urlbar.hideUrl();
        urlbar.setTitle(view.getTitle());
    }


    private void showDialog(WebView view,String url){
        new AlertDialog.Builder(context).setTitle("跳转提示")//设置对话框标题
                .setMessage("跳转至app")//设置显示的内容
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮


                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        // TODO Auto-generated method stub

                        Intent intent =Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        view.getContext().startActivity(intent);
                        Log.v("loadotherapp",intent.toString());

                    }

                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮

            @Override

            public void onClick(DialogInterface dialog, int which) {//响应事件

                // TODO Auto-generated method stub

                Log.i("alertdialog"," 请保存数据！");
            }

        }).show();//在按键响应事件中显示此对话框




    }

    class DialogButton implements DialogInterface.OnClickListener{
        WebView view;
        String url;

        public DialogButton(WebView view, String url) {
            this.view = view;
            this.url = url;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent =Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            view.getContext().startActivity(intent);
            Log.v("loadotherapp",intent.toString());
        }
    }



}
