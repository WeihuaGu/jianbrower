/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian;

import android.content.Intent;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

public class ScanActivity extends AppCompatActivity implements OnQRCodeReadListener{

    private TextView resultTextView;
    private QRCodeReaderView  mydecoderview;
    private static final String ACTION_OPENURL = "com.weihuagu.jian.action.OPENURL";
    private static final String EXTRA_URL = "com.weihuagu.jian.extra.url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView=(TextView) findViewById(R.id.result_text_view);

        mydecoderview.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mydecoderview.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        mydecoderview.setTorchEnabled(true);

        // Use this function to set front camera preview
        mydecoderview.setFrontCamera();

        // Use this function to set back camera preview
        mydecoderview.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        Log.v("QRcode","read");
        if(text!=null){
            Log.v("QRcode text",text);
            resultTextView.setText(text);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }

    public void openScanedUrl(View v){
        Log.i("Qrcode","open url");
        String url=resultTextView.getText().toString();

        if(!url.equals("")){
            Log.i("Qrcode",url);
            Intent send=new Intent(getApplicationContext(), BrowserActivity.class);
            send.setAction(ACTION_OPENURL);
            send.putExtra(EXTRA_URL,url);
            startActivity(send);

        }

    }

}
