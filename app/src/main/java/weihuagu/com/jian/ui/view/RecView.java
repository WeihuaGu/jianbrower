/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package weihuagu.com.jian.ui.view;

/**
 * Created by root on 17-5-7.
 */


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
public class RecView extends View {

    private Paint mPaint;
    private float recmagin;
    private float dist;
    private float centerx;
    private float centery;
    private static final float BODY_SCALE = 0.6f;//身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; //        身体的比例设定为 w:h = 3:5
    public RecView(Context context) {
        super(context);
    }

    public RecView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initParams() {
        float viewwidth=getWidth();
        recmagin=(float) (viewwidth*0.24);
        dist=(getRight()-recmagin)-(getLeft()+recmagin);
        centerx=(getRight()-getLeft())/2+getLeft();
        centery=(getBottom()-getTop())/2+getTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initParams();
        initPaint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(centerx-dist/2, centery-dist/2,centerx+dist/2, centery-dist/2,mPaint);
        canvas.drawLine(centerx+dist/2, centery-dist/2,centerx+dist/2, centery+dist/2,mPaint);
        canvas.drawLine(centerx+dist/2, centery+dist/2,centerx-dist/2, centery+dist/2,mPaint);
        canvas.drawLine(centerx-dist/2, centery+dist/2,centerx-dist/2, centery-dist/2,mPaint);



    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        } else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);//边缘无锯齿
    }



}
