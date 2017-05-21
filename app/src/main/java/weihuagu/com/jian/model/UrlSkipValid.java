/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

/**
 * Created by root on 17-5-21.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.util.Log;
public class UrlSkipValid {

    String url;
    public UrlSkipValid(String url) {
        this.url=url;
    }

    public boolean validSkip(){
        try{
            Document doc = Jsoup.connect(url).get();
            Elements meta = doc.select("meta[http-equiv]");
            Log.v("jsoupgetmeta",meta.toString());
            if(meta!=null){
                for (Element refresh:meta){
                    if(refresh.attr("http-equiv")=="refresh")
                        return true;

                }
            }
        }
        catch (Exception e){
            Log.v("jsoup exception",e.getMessage());
        }
        return false;
      }
}
