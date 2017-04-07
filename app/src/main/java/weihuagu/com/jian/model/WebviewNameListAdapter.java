/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by root on 17-4-7.
 */
public class WebviewNameListAdapter extends RecyclerView.Adapter<WebviewNameListAdapter.WebviewNameHolder> {


    @Override
    public WebviewNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WebviewNameHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class WebviewNameHolder extends RecyclerView.ViewHolder{


        public WebviewNameHolder(View itemView) {
            super(itemView);
        }
    }
}
