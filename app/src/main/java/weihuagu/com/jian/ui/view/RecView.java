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
        recmagin=getWidth()*(2/5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initParams();
        initPaint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAlpha(60);
        // 设置填充颜色
        Path path = new Path();
        path.moveTo(getLeft()+recmagin, getTop()+recmagin);                           //起始点
        path.lineTo(getRight()-recmagin, getTop()+recmagin);                           //连线到下一点
        path.lineTo(getRight()-recmagin, getBottom()-recmagin);                      //连线到下一点
        path.lineTo(getLeft()+recmagin, getBottom()-recmagin);
        canvas.drawPath(path,mPaint);

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
