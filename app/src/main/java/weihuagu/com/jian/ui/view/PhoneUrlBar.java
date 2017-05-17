/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


import android.widget.ArrayAdapter;

import weihuagu.com.jian.R;
import weihuagu.com.jian.model.OnPhoneUrlBarEventListener;
import weihuagu.com.jian.util.UrlUtil;


public class PhoneUrlBar extends LinearLayout{

    private Context mContext;
    private LinearLayout mTitleLayout;
    private LinearLayout mUrlLayout;
    private TextView mTitle;
    private TextView mSubTitle;
    private AutoCompleteTextView mUrl;

    private TextWatcher mUrlTextWatcher;
    private boolean mIsUrlBarVisible = false;
    private boolean mIsUrlChangedByUser = false;
    private OnPhoneUrlBarEventListener mEventListener = null;


    public PhoneUrlBar(Context context) {
        this(context, null);
    }

    public PhoneUrlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneUrlBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.initResouces(context);
        this.setListerner();
        this.UrlSuggestion();
    }

    public void initResouces(Context context){

        LayoutInflater mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.phone_url_bar, null);
        addView(v);



        mTitleLayout = (LinearLayout) v.findViewById(R.id.UrlBarTitleLayout);
        mUrlLayout = (LinearLayout) v.findViewById(R.id.UrlBarUrlLayout);

        mTitle = (TextView) v.findViewById(R.id.UrlBarTitle);
        mSubTitle = (TextView) v.findViewById(R.id.UrlBarSubTitle);

        mUrl = (AutoCompleteTextView) v.findViewById(R.id.UrlBarUrlEdit);

        mUrlTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, 	int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                mIsUrlChangedByUser = true;
            }
        };



    }

    public void setListerner(){
        mTitleLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrl();
            }
        });

        mTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrl();
            }
        });

        mSubTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrl();
            }
        });

        mUrl.addTextChangedListener(mUrlTextWatcher);

        mUrl.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    triggerOnUrlValidated();
                    return true;
                }
                return false;
            }
        });

        mUrl.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                triggerOnUrlValidated();
            }
        });

        mUrl.setDropDownAnchor(R.id.UrlBarContainer);


    }

    public void UrlSuggestion(){
        String [] from= UrlUtil.getCommonlyUsedUrls();
        mUrl.setThreshold(1);
        ArrayAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,from);
        mUrl.setAdapter(adapter);

    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(int resId) {
        setTitle(mContext.getString(resId));
    }

    public void setTitleOnly(String title) {
        mTitle.setText(title);

        mSubTitle.setText(null);
        mSubTitle.setVisibility(View.GONE);
    }

    public void setTitleOnly(int resId) {
        setTitleOnly(mContext.getString(resId));
    }

    public void setSubtitle(String subtitle) {
        mSubTitle.setText(subtitle);

        if ((subtitle == null) ||
                (subtitle.isEmpty())) {
            mSubTitle.setVisibility(View.GONE);
        } else {
            mSubTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setSubtitle(int resId) {
        setSubtitle(mContext.getString(resId));
    }
    public void getUrlFocus(){ mUrl.requestFocus();}
    public void showUrl() {
        mTitleLayout.setVisibility(View.GONE);
        mUrlLayout.setVisibility(View.VISIBLE);

        mIsUrlBarVisible = true;
        mUrl.requestFocus();

        InputMethodManager mgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(mUrl, InputMethodManager.SHOW_IMPLICIT);

        triggerOnUrlBarVisibilityChanged();
    }

    public void hideUrl() {
        hideUrl(true);
    }

    public void hideUrl(boolean hideKeyboard) {
        mUrlLayout.setVisibility(View.GONE);
        mTitleLayout.setVisibility(View.VISIBLE);

        mIsUrlBarVisible = false;

        if (hideKeyboard) {
            InputMethodManager mgr = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(mUrl.getWindowToken(), 0);
        }

        triggerOnUrlBarVisibilityChanged();
    }

    public boolean isUrlBarVisible() {
        return mIsUrlBarVisible;
    }

    public String getUrl() {
        return mUrl.getText().toString();
    }

    public void setUrl(String url) {
        mUrl.removeTextChangedListener(mUrlTextWatcher);
        mUrl.setText(url);
        mUrl.addTextChangedListener(mUrlTextWatcher);
        mIsUrlChangedByUser = false;
    }
    public void cleanUrlAndGetfocus(){
        this.setUrl("");
        this.showUrl();
        this.getUrlFocus();
    }

    public boolean isUrlChangedByUser() {
        return mIsUrlChangedByUser;
    }

    public void setEventListener(OnPhoneUrlBarEventListener listener) {
        mEventListener = listener;
    }

    private void triggerOnUrlBarVisibilityChanged() {
        if (mEventListener != null) {
            mEventListener.onVisibilityChanged(mIsUrlBarVisible);
        }
    }

    private void triggerOnUrlValidated() {
        if (mEventListener != null) {
            mEventListener.onUrlValidated();
        }
    }

}

