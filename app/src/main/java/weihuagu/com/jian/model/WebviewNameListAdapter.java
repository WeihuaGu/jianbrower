/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import weihuagu.com.jian.R;

/**
 * Created by root on 17-4-7.
 */
public class WebviewNameListAdapter extends RecyclerView.Adapter<WebviewNameListAdapter.WebviewNameHolder> {
    private Context mContext;
    private List<String> namelist=new ArrayList<String>();

    public WebviewNameListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public WebviewNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WebviewNameHolder(LayoutInflater.from(mContext).inflate(R.layout.webviewnamelistview_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WebviewNameHolder holder, int position) {
        String item=this.namelist.get(position);
        if(item!=null){
            holder.webviewtitle.setText(item);
        }

    }

    @Override
    public int getItemCount() {
        return this.namelist.size();
    }

    public void addWebviewNameList(List<String> list){

        for (int i=0;i<list.size();i++){
            this.namelist.add(list.get(i));
        }


    }

    class WebviewNameHolder extends RecyclerView.ViewHolder{
        TextView webviewtitle;

        public WebviewNameHolder(View itemView) {
            super(itemView);
            this.webviewtitle=(TextView) itemView.findViewById(R.id.webviewtitle);
        }
    }


}
