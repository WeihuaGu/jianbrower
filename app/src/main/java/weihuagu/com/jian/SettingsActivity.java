/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.preference.Preference;
import android.widget.TextView;

import weihuagu.com.jian.model.Advertising;

import com.baidu.appx.BDBannerAd;

public class SettingsActivity extends ActionBarActivity{


    private SettingsFragment mSettingsFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            replaceFragment(R.id.settings_container, mSettingsFragment);
        }

        this.showBannerAd();

    }

    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }


    }

    private BDBannerAd bannerview=null;
    public void showBannerAd(){

        bannerview=new BDBannerAd(this, "ZHY7GuRu0ycYGlGkiwjzv7glD2GHgcB3", "gY4DR1QGzxYkCDQxlArO9dyG");
        bannerview.setAdSize(BDBannerAd.SIZE_FLEXIBLE);
        bannerview.setAdListener(new Advertising("Banner"));
        Log.v("ad", "adview new");
        ViewGroup container=(LinearLayout)findViewById(R.id.adview_container);
        container.addView(bannerview);


    }



}
