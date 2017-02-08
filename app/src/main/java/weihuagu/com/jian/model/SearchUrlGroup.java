/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchUrlGroup {
	
	private String mName;
	private List<SearchUrlItem> mItems;
	
	public SearchUrlGroup(String name) {
		mName = name;
		mItems = new ArrayList<SearchUrlItem>();
	}	
	
	public String getName() {
		return mName;
	}
	
	public List<SearchUrlItem> getItems() {
		return mItems;
	}
	
	public void addItem(String name, String url) {
		mItems.add(new SearchUrlItem(name, url));
	}
	
	public void sort() {
		Collections.sort(mItems, new Comparator<SearchUrlItem>() {
			@Override
			public int compare(SearchUrlItem lhs, SearchUrlItem rhs) {						
				return lhs.getName().compareTo(rhs.getName());
			}		        	
        });
	}

}
