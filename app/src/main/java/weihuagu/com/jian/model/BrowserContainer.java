/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-3-23.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import weihuagu.com.jian.ui.view.CustomWebView;
public class BrowserContainer {



    private static int currentindex=0;

    private static List<IWebViewContainer> list = new LinkedList<>();

    public static IWebViewContainer get(int index) {
        return list.get(index);
    }

    public static IWebViewContainer getCurrent(){return list.get(currentindex);}

    public static int getCurrentindex() {
        return currentindex;
    }

    public synchronized static void set(IWebViewContainer controller, int index) {
        if (list.get(index) instanceof CustomWebView) {
            ((CustomWebView) list.get(index)).destroy();
        }

        list.set(index, controller);
    }

    public synchronized static void add(IWebViewContainer controller) {
        list.add(controller);
    }

    public synchronized static void add(IWebViewContainer controller, int index) {
        list.add(index, controller);
    }

    public synchronized static void addCurrent(IWebViewContainer controller) {
        list.add(controller);
        currentindex=list.indexOf(controller);
    }

    public synchronized static void setCurrentindex(int index){
        
        currentindex=index;
    }

    public synchronized static void remove(int index) {
        if (list.get(index) instanceof IWebViewContainer) {
            ((CustomWebView) list.get(index)).destroy();
        }

        list.remove(index);
    }

    public synchronized static void remove(CustomWebView controller) {
        if (controller instanceof CustomWebView) {
            ((CustomWebView) controller).destroy();
        }

        list.remove(controller);
    }

    public static int indexOf(IWebViewContainer controller) {
        return list.indexOf(controller);
    }

    public static List<IWebViewContainer> list() {
        return list;
    }

    public static int size() {
        return list.size();
    }

    public synchronized static void clear() {
        for (IWebViewContainer albumController : list) {
            if (albumController instanceof CustomWebView) {
                ((CustomWebView) albumController).destroy();
            }
        }

        list.clear();
    }



    public static List<String> getWebViewListname(){

        List<String> namelist=new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            CustomWebView tmpwebview=(CustomWebView)list.get(i);
            String title=tmpwebview.getTitle();
            if(title!=null){
                String nameandindex=title+"&"+i;
                namelist.add(nameandindex);
            }else{
                String namenandindex="&"+i;
                namelist.add(namenandindex);
            }

        }
        return namelist;
    }
    public static int WebViewNum(){
        return list.size();
    }
}
