/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by root on 17-5-20.
 */
public class HttpCodeTask extends AsyncTask<String, Void, String> {
    public void setReturncoderesponse(HttpCodeResponse<String> returncoderesponse) {
        this.returncoderesponse = returncoderesponse;
    }

    public HttpCodeResponse<String> returncoderesponse=null;

    @Override
    protected String doInBackground(String... urlpath) {
        try {
            String httpcode=null;
            Log.v("httpcodeurl",urlpath[0]);
            URL url = new URL(urlpath[0]);
            int code;
            if(url.getProtocol().toLowerCase().equals("https")){
                HttpsURLConnection httpUrlConn=(HttpsURLConnection) url.openConnection();
                code=httpUrlConn.getResponseCode();
                if(code==200){
                    Boolean skip=new UrlSkipValid(urlpath[0]).validSkip();
                    if(skip){
                        Log.v("alterskiptocode","301http");
                        return "301";

                    }


                }

                httpcode=String.valueOf(code);
                return httpcode;

            }else{
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                code = conn.getResponseCode();

                if(code==200 ){

                    Boolean skip=new UrlSkipValid(urlpath[0]).validSkip();
                    if(skip){
                        Log.v("alterskiptocode","301http");
                        return "301";
                    }

                }

                httpcode=String.valueOf(code);
                return httpcode;

            }

        }catch (IOException e){
            Log.v("gethttpcode","ioexception"+e.getMessage());
        }catch (NullPointerException e){
            Log.v("gethttpcode","nullexception"+e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String httpcode) {
        super.onPostExecute(httpcode);
        if (httpcode != null)
        {
            this.returncoderesponse.onHttpCodeReturnSuccess(httpcode);//将结果传给回调接口中的函数
        }
        else {
            this.returncoderesponse.onHttpCodeReturnFailed();
        }

    }
}
