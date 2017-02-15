/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.util;

public class UrlUtil {
    public static String [] commonusedurls={"m.baidu.com","www.baidu.com","cn.bing.com","Jd.com","weibo.com","weibo.cn","sina.cn","www.tmall.com","www.taobao.com","tianya.cn","github.com","news.163.com","mail.163.com"};
    public static String [] getCommonlyUsedUrls(){
        return commonusedurls;
    }

    public static boolean isUrl(String url) {
        return
                url.contains(".") ||
                        url.equals(Constants.URL_ABOUT_BLANK) ||
                        url.equals(Constants.URL_ABOUT_START) ||
                        url.equals(Constants.URL_ABOUT_TUTORIAL);
    }


    public static String getSearchUrl(String searchurl,String serchcontent) {
        return searchurl.replaceAll("\\{searchTerms\\}", serchcontent);
    }
    public static String addressMatch(String address){
        if(address.startsWith("http://")){
            return address;
        }
        if(address.startsWith("https://"))
            return address;

        if(!address.startsWith("http://")|!address.startsWith("https://")) {
            address = "http://" + address;
        } // 如果不以http://开头，识别不了，所以判断
        return address;
    }
    public static String addressMatchInHttps(String address){
        if(address.startsWith("https://")){
            return address;

        }
        if(address.startsWith("http://")){
            return address.replaceAll("http","https");
        }
        else{
            return "https://"+address;
        }

    }
}
