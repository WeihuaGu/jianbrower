/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.util;


public class UrlUtil {
    public static String [] commonusedurls={"m.baidu.com","www.baidu.com","cn.bing.com"};
    public static String [] getCommonlyUsedUrls(){
        return commonusedurls;
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
}
