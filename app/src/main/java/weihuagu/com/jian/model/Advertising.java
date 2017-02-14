/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.util.Log;

import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;
import weihuagu.com.jian.ui.view.IAdView;

public class Advertising implements  BDBannerAd.BannerAdListener, BDInterstitialAd.InterstitialAdListener, BDSplashAd.SplashAdListener{


    private IAdView adview=null;
    public void loadBannerAd(IAdView adview){
        this.adview=adview;

    }

    private String stringTag;
    public Advertising(String tag) {
        this.stringTag = tag;
    }

    @Override
    public void onAdvertisementDataDidLoadFailure() {
        Log.d("ad", "data load failure");

    }

    @Override
    public void onAdvertisementDataDidLoadSuccess() {
        Log.d("ad", "data load success");
    }

    @Override
    public void onAdvertisementViewDidClick() {
        Log.d("ad", "click");
    }

    @Override
    public void onAdvertisementViewDidShow() {
        Log.d("ad","show");
    }

    @Override
    public void onAdvertisementViewWillStartNewIntent() {
        Log.d("ad", "start new intent");
    }

    @Override
    public void onAdvertisementViewDidHide() {
        Log.d("ad", "hide");
    }
}
