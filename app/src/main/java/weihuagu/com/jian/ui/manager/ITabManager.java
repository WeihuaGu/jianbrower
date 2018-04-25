/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.manager;

public interface ITabManager {
    public void openNewTab();
    public void openNewTab(String url);
    public void alterToTab(int tabindex);
    public void closeTab(int tabindex);
    public void showTabManager();
    public void hideTabManager();
    public void openBackTab(String url);


}
