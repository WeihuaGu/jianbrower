/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import java.util.List;

import org.tint.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class SearchUrlAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<SearchUrlGroup> mData;
	
	public SearchUrlAdapter(Context context, List<SearchUrlGroup> data) {
		mContext = context;
		mData = data;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mData.get(groupPosition).getItems().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return mData.get(groupPosition).getItems().get(childPosition).hashCode();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		TextView item;
        if ((convertView == null) ||
        		(!(convertView instanceof TextView))) {
        	LayoutInflater inflater = LayoutInflater.from(mContext);
        	item = (TextView) inflater.inflate(R.layout.expandable_list_item, null);
        } else {
        	item = (TextView) convertView;
        }
        
        item.setText(((SearchUrlItem) getChild(groupPosition, childPosition)).getName());
		
		return item;
	}

	@Override
	public int getChildrenCount(int groupPosition) {		
		return mData.get(groupPosition).getItems().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return mData.get(groupPosition).hashCode();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,	View convertView, ViewGroup parent) {
		TextView item;
        if ((convertView == null) ||
        		(!(convertView instanceof TextView))) {
        	LayoutInflater factory = LayoutInflater.from(mContext);
        	item = (TextView) factory.inflate(R.layout.expandable_list_header, null);
        } else {
        	item = (TextView) convertView;
        }
        
        item.setText(((SearchUrlGroup) getGroup(groupPosition)).getName());
        
        return item;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
