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
import android.widget.TextView;

import weihuagu.com.jian.R;
import weihuagu.com.jian.model.DownloadItem;
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
    PhoneUrlBar urlbar=null;
    CustomWebView webview=null;
    OnPhoneUrlBarEventListener urlbarEventhandle=null;
    Context context=null;
    WebViewListener listerner=new WebViewListener();


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

    @Override
    public void loadUrl(String url) {
        if(UrlUtil.isUrl(url)){
            Log.v("loadurl","is url"+url);
            String openurl= UrlUtil.addressMatch(url);
            this.webview.loadUrl(openurl);

        }
        else{

            String engine=this.sharedPref.getString("presearchitems",null);
            if(engine!=null)
            Log.v("search engine",engine);
            if(engine.equals("bing")) {
                Log.v("fuck","1");
                this.searchByBing(url);

            }
            if(engine.equals("google")){
                Log.v("fuck","2");
                this.searchByGoogle(url);

            }
            if(engine.equals("wolframalpha")){
                this.searchByWolframalpha(url);
            }
            else{
                Log.v("fuck","3");
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
        this.webview.getSettings().setJavaScriptEnabled(true); //设置设否支持JavaScript
        this.webview.getSettings().setDomStorageEnabled(true);
        this.webview.getSettings().setSupportZoom(true);
        this.webview.getSettings().setBuiltInZoomControls(true);
        this.webview.getSettings().setDisplayZoomControls(false);//隐藏Zoom缩放按钮

        this.webview.setDownloadListener(new MyWebViewDownLoadListener(this.context));
        this.webview.setOnTouchListener(listerner);
        this.webview.setOnLongClickListener(listerner);

        this.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if(!url.startsWith("http://")| !url.startsWith("http://") ){
                    return false;
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
                Log.i("phonuimanager","pagefinised and title:"+webview.getTitle());
            }


        }); //设置浏览

        this.webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                urlbar.setTitle(webview.getTitle());
                Log.i("phonuimanager","pageprogressChanged and title:"+webview.getTitle());
            }

        });

        this.urlbarEventhandle=new UrlBarEventHandle();
        this.urlbar.setEventListener(urlbarEventhandle);






    }

    public void init(){
        //this.webview.loadUrl("http://m.baidu.com");
        this.urlbar.showUrl();
        this.urlbar.getUrlFocus();
        sharedPref=PreferenceManager.getDefaultSharedPreferences(this.context);
        this.setFontSize();
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

    class UrlBarEventHandle implements OnPhoneUrlBarEventListener{


        @Override
        public void onVisibilityChanged(boolean urlBarVisible) {

            Log.v("urlbar","visible changed");

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


    class WebViewListener implements View.OnTouchListener,View.OnLongClickListener {
        int downX;
        int downY;
        ItemLongClickedPopWindow itemLongClickedPopWindow;
        String saveImgUrl;

        public void hindleIMAGE_TYPE(String ImgUrl,View v){
            // 获取图片的路径
            saveImgUrl =ImgUrl;
            //通过GestureDetector获取按下的位置，来定位PopWindow显示的位置

            itemLongClickedPopWindow = new ItemLongClickedPopWindow(context,
                    ItemLongClickedPopWindow.IMAGE_VIEW_POPUPWINDOW,
                    300,400);

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

            itemLongClickedPopWindow.getView(R.id.item_longclicked_attr)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(saveImgUrl!=null|saveImgUrl.equals("")){
                                //showDialog("图片属性","图片地址："+saveImgUrl);
                                ItemLongClickedPopWindow attrshowpop=new ItemLongClickedPopWindow(context,
                                        ItemLongClickedPopWindow.ATTR_SHOW,
                                        600,800);
                                attrshowpop.showAtLocation(v, Gravity.TOP|Gravity.LEFT, downX, downY + 10);
                                TextView attrcontent=(TextView)attrshowpop.getView(R.id.item_attrcontent);
                                attrcontent.setText(saveImgUrl);

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
}
