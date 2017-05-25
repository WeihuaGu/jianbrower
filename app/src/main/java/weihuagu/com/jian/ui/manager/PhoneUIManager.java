/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

import java.util.Stack;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.util.Log;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.webkit.WebSettings.TextSize;
import android.widget.ProgressBar;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import weihuagu.com.jian.R;
import weihuagu.com.jian.model.CustomWebViewClient;
import weihuagu.com.jian.model.HttpCodeResponse;
import weihuagu.com.jian.model.OnPhoneUrlBarEventListener;
import weihuagu.com.jian.ui.view.CustomWebView;
import weihuagu.com.jian.ui.view.PhoneUrlBar;
import weihuagu.com.jian.util.UrlUtil;
import weihuagu.com.jian.model.HttpCodeTask;

import rx.Subscriber;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;
import java.util.EmptyStackException;

/**
 * Created by root on 17-2-9.
 */
public class PhoneUIManager implements UIManager,HttpCodeResponse<String>{

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
        settingWebView();

        //freshUrl();

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
        urlbarEventhandle=new UrlBarEventHandle();
        urlbar.setEventListener(urlbarEventhandle);
    }

    public void init(){
        urlbar.cleanUrlAndGetfocus();
        sharedPref=PreferenceManager.getDefaultSharedPreferences(this.context);
        preferenceChangeListener=new OnPreferenceChangeListener();
        sharedPref.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        setFontSize();
        setEnableImage();
        loadHome();
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
    public void setNightMode(String mode) {

        if(mode=="night"){
            try {
                Class clsWebSettingsClassic =
                        this.context.getClassLoader().loadClass("android.webkit.WebSettings");
                Method md = clsWebSettingsClassic.getMethod(
                        "setProperty", String.class, String.class);
                md.invoke(webview.getSettings(), "inverted", "true");
                md.invoke(webview.getSettings(), "inverted_contrast", "1");
            } catch (Exception e) {
                Log.v("nightmode",e.getMessage().toString());
            }

        }
        if(mode=="day"){
            try {
                Class clsWebSettingsClassic =
                        this.context.getClassLoader().loadClass("android.webkit.WebSettings");
                Method md = clsWebSettingsClassic.getMethod(
                        "setProperty", String.class, String.class);
                md.invoke(webview.getSettings(), "inverted", "true");
                md.invoke(webview.getSettings(), "inverted_contrast", "1");
            } catch (Exception e) {}

        }
        this.freshUrl();

    }

    private void hindleGoBack(String url) {
        HttpCodeTask httpcode=new HttpCodeTask();
        httpcode.setReturncoderesponse(this);
        httpcode.execute(url);
    }

    @Override
    public boolean onKeyBack() {
        Stack<String> history=webview.getUrlhistorystack();
        Log.v("historystackinback",history.toString());
        if(history.size()>1){
            history.pop();
            String gobackurl=history.peek();
            if(gobackurl.startsWith("jian://")){
                onKeyBack();
            }else{
                hindleGoBack(gobackurl);
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public String getCurrentUrl() {

        return webview.getUrl();
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
        CustomWebViewClient webviewclient=new CustomWebViewClient();
        webviewclient.setContext(context);
        webviewclient.setUrlbar(urlbar);
        webview.setWebViewClient(webviewclient);
        webview.setWebChromeClient(new WebChromeClient(){


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }

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

    @Override
    public void onHttpCodeReturnSuccess(String httpcode) {

        try {
            Log.v("getbackhttpcode", httpcode);
            Stack<String> history = webview.getUrlhistorystack();
            if (httpcode.equals("301") | httpcode.equals("302")) {
                Log.v("back url","301 or 302");
                Log.v("back url","his stack"+history.toString());
                history.pop();
                webview.loadUrl(history.pop());

            }
            if (httpcode.equals("200")) {
                webview.loadUrl(history.pop());

            } else {
                webview.loadUrl(history.pop());
            }
        }catch (EmptyStackException e){

        }

    }

    @Override
    public void onHttpCodeReturnFailed() {

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
            Log.i("urlbar","key enter");
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
