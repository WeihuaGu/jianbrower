/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import android.app.DownloadManager;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.util.Log;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.webkit.WebSettings.TextSize;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ProgressBar;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import weihuagu.com.jian.R;
import weihuagu.com.jian.model.DownloadItem;
import weihuagu.com.jian.model.IWebViewContainer;
import weihuagu.com.jian.model.OnPhoneUrlBarEventListener;
import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.util.UrlUtil;
import weihuagu.com.jian.model.MyWebViewDownLoadListener;
import weihuagu.com.jian.ui.view.ItemLongClickedPopWindow;




/**
 * Created by root on 17-2-9.
 */
public class PhoneUIManager implements UIManager{

    SharedPreferences sharedPref=null;
    OnSharedPreferenceChangeListener preferenceChangeListener=null;
    PhoneUrlBar urlbar=null;
    ProgressBar progressbar = null;
    CustomWebView webview=null;
    OnPhoneUrlBarEventListener urlbarEventhandle=null;
    Context context=null;


    public void setUrlbar(PhoneUrlBar urlbar) {
        this.urlbar = urlbar;
    }


    public void setWebview(CustomWebView webview) {
        this.webview = webview;
    }

    public PhoneUIManager(PhoneUrlBar urlbar,CustomWebView webview,Context context){
        this.context=context;
        this.urlbar=urlbar;
        this.webview=webview;
        this.initresources();
        this.init();
    }

    public PhoneUIManager(PhoneUrlBar urlbar,CustomWebView webview,ProgressBar progressbar,Context context){
        this.context=context;
        this.urlbar=urlbar;
        this.webview=webview;
        this.setProgressbar(progressbar);
        this.initresources();
        this.init();
    }

    public void setProgressbar(ProgressBar progressbar){
        this.progressbar=progressbar;
    }

    @Override
    public void setCurrentWebview(CustomWebView webview) {
        this.webview=webview;
        this.initresources();
        this.init();

    }

    @Override
    public void loadUrl(String url) {
        if(UrlUtil.isUrl(url)){
            Log.v("loadurl","is url"+url);
            String openurl= UrlUtil.addressMatch(url);
            this.webview.loadUrl(openurl);

        }
        else{
            Log.v("loadurl","is not urltype:"+url);
            String engine=this.sharedPref.getString("presearchitems",null);
            if(engine!=null){
                    Log.v("search engine",engine);
                    if(engine.equals("bing")) {

                        this.searchByBing(url);

                    }
                    if(engine.equals("google")){

                        this.searchByGoogle(url);

                     }
                    if(engine.equals("wolframalpha")){
                        this.searchByWolframalpha(url);
                    }

            }else{

                this.searchByBing(url);
            }
        }




    }

    public void loadHome(){
        String homeurl=this.sharedPref.getString("prefhome",null);
        if(homeurl!=null){
            if(homeurl!=""){
                this.loadUrl(homeurl);
            }
        }


    }

    public void searchByBing(String searchkey){
        Log.v("loadurl","not url and use bing:"+searchkey);
        String searchurl=context.getString(R.string.SearchUrlBing);
        String openurl=UrlUtil.getSearchUrl(searchurl,searchkey);
        this.webview.loadUrl(openurl);

    }

    public void searchByGoogle(String searchkey){
        Log.v("loadurl","not url and use google:"+searchkey);
        String searchurl=context.getString(R.string.SearchUrlGoogle);
        String openurl=UrlUtil.getSearchUrl(searchurl,searchkey);
        this.webview.loadUrl(openurl);

    }

    public void searchByWolframalpha(String searchkey){
        Log.v("loadurl","not url and use Wolframalpha:"+searchkey);
        String searchurl=context.getString(R.string.SearchUrlWolframAlpha);
        String openurl=UrlUtil.getSearchUrl(searchurl,searchkey);
        this.webview.loadUrl(openurl);
    }

    @Override
    public void loadUrlInHttps(String url){
        Log.v("loadinhttps:","sorce url"+url);
        String openurl= UrlUtil.addressMatchInHttps(url);
        Log.v("loadinhttps:","open url"+openurl);
        this.webview.loadUrl(openurl);
    }
    public void initresources(){
        settingWebView();
        this.urlbarEventhandle=new UrlBarEventHandle();
        this.urlbar.setEventListener(urlbarEventhandle);

    }

    public void init(){
        this.urlbar.showUrl();
        this.urlbar.getUrlFocus();
        sharedPref=PreferenceManager.getDefaultSharedPreferences(this.context);
        this.preferenceChangeListener=new OnPreferenceChangeListener();
        sharedPref.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        this.setFontSize();
        this.setEnableImage();
        this.loadHome();
    }

    public void setFontSize(){
        String fontsize=this.sharedPref.getString("prefontsizeitems",null);
        if(fontsize!=null){
            TextSize size=fontSizeIntToTextSize(fontsize);
            Log.v("fontsize:",size.toString());
            this.webview.getSettings().setTextSize(size);

        }

    }

    public void setEnableImage(){
        boolean enableimage=sharedPref.getBoolean("preenableimage",true);
        this.webview.getSettings().setLoadsImagesAutomatically(enableimage);
    }

    private TextSize fontSizeIntToTextSize(String sizenum){
        if(sizenum.equals("1")){
            return TextSize.SMALLEST;

        }
        if(sizenum.equals("2")){
            return TextSize.SMALLER;
        }
        if(sizenum.equals("3")){
            return TextSize.NORMAL;
        }
        if(sizenum.equals("4")){
            return TextSize.LARGER;

        }
        if(sizenum.equals("5")){
            return TextSize.LARGEST;

        }
        else{
            return TextSize.NORMAL;
        }

    }


    public void setUrlbarUrl(String url){
        this.urlbar.setUrl(url);

    }

    @Override
    public void hideurl(){
        this.urlbar.hideUrl();
    }

    @Override
    public boolean onKeyBack() {
        if(webview.canGoBack()){
            webview.goBack();
            return true;

        }
        else
            return false;
    }

    @Override
    public String getCurrentUrl() {

        return this.webview.getUrl();
    }

    @Override
    public void freshUrl() {
        this.webview.loadUrl(webview.getUrl());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }


    private void settingWebView(){
        this.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.startsWith("http://")| url.startsWith("http://") ){
                    view.loadUrl(url);
                }else{
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent =new Intent(Intent.ACTION_VIEW, uri);
                        view.getContext().startActivity(intent);
                        Log.v("loadotherpro",intent.toString());
                    }catch (Exception e){
                        Log.v("loadnonomal",e.getMessage());

                    }
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
//结束
                super.onPageFinished(view, url);
                setUrlbarUrl(getCurrentUrl());
                urlbar.hideUrl();
                urlbar.setTitle(webview.getTitle());
            }


        }); //设置浏览

        this.webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub

                if(progressbar!=null) {
                    if (newProgress == 100) {
                        progressbar.setVisibility(View.INVISIBLE);
                    } else {
                        if (View.INVISIBLE == progressbar.getVisibility()) {
                            progressbar.setVisibility(View.VISIBLE);
                        }
                        progressbar.setProgress(newProgress);
                    }

                }
                super.onProgressChanged(view, newProgress);
                urlbar.setTitle(webview.getTitle());
            }

        });


    }

    class UrlBarEventHandle implements OnPhoneUrlBarEventListener{


        @Override
        public void onVisibilityChanged(boolean urlBarVisible) {

            String title=webview.getTitle();
            if(title!=null){
                urlbar.setTitle(title);
            }

        }

        @Override
        public void onUrlValidated() {
            Log.v("urlbar","key enter");
            loadUrl(urlbar.getUrl());

        }

        @Override
        public void onGoStopReloadClicked() {

        }

        @Override
        public void onMenuVisibilityChanged(boolean isVisible) {

        }
    }




    class OnPreferenceChangeListener implements OnSharedPreferenceChangeListener{


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.i("preferenceChanged key",key);
            if(key.equals("preenableimage")){
                setEnableImage();
            }

        }
    }
}
