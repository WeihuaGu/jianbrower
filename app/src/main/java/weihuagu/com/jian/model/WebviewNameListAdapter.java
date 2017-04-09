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
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;


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
        holder.setPositon(position);
        if(item!=null){
            String [] tmp=splitNameAndInext(item);
            String name=tmp[0];
            String index=tmp[1];
            if(name!=null){
                if(name.equals("")){
                    if(!index.equals("")){
                        holder.webviewtitle.setText("空白标签:"+index);


                    }

                }
                else {
                    holder.webviewtitle.setText(tmp[0]);
                }
                holder.setWebviewindex(Integer.parseInt(index));
            }

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

    private String [] splitNameAndInext(String string){
        String [] nameandindex=string.split("\\&");
        return nameandindex;


    }

    class WebviewNameHolder extends RecyclerView.ViewHolder implements  OnClickListener {
        TextView webviewtitle;

        public void setWebviewindex(int webviewindex) {
            this.webviewindex = webviewindex;
        }

        int webviewindex;


        public void setPositon(int positon) {
            this.positon = positon;
        }

        int positon;
        ImageButton close;

        public WebviewNameHolder(View itemView) {
            super(itemView);
            this.webviewtitle=(TextView) itemView.findViewById(R.id.webviewtitle);
            this.webviewtitle.setTag("webviewtitle");
            this.close=(ImageButton) itemView.findViewById(R.id.close);
            this.close.setTag("close");
        }

        public void setListerner(){
            webviewtitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String tag = (String) view.getTag();
            if(tag.equals("webviewtitle")){

            }
            if(tag.equals("close")){

                namelist.remove(positon);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());

            }
        }
    }


}
