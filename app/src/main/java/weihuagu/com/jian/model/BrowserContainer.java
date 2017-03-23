/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-3-23.
 */

import java.util.List;
import java.util.LinkedList;
import weihuagu.com.jian.ui.view.CustomWebView;
public class BrowserContainer {

    private static List<IWebViewContainer> list = new LinkedList<>();

    public static IWebViewContainer get(int index) {
        return list.get(index);
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
}