package com.ysw.berisaier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 二阶贝塞尔曲线
 * Created by 11033 on 2017/2/27.
 */

public class SecondBezier extends View {
    private static final String TAG = "SecondBezier";
    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;

    private float mAuxiliaryX;
    private float mAuxiliaryY;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private Path mPath=new Path();
    public SecondBezier(Context context) {
        super(context);
    }

    public SecondBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(2);

        mPaintAuxiliary=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);

        mPaintAuxiliaryText=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);
    }

    public SecondBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SecondBezier(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX =w/4;
        mStartPointY=h/2 -200;

        mEndPointX=2/4*3;
        mEndPointY=h/2-300;
    }

    /*二阶贝塞尔曲线在Android中的API为：quadTo()和rQuadTo()，
    这两个API在原理上是可以互相转换的——quadTo是基于绝对坐标，而rQuadTo是基于相对坐标*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);
        //辅助点
        canvas.drawPoint(mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
        canvas.drawText("控制点",mAuxiliaryX,mAuxiliaryY,mPaintAuxiliaryText);
        canvas.drawText("起始点",mStartPointX,mStartPointY,mPaintAuxiliaryText);
        canvas.drawText("终止点",mEndPointX,mEndPointY,mPaintAuxiliaryText);
        //辅助线
        canvas.drawLine(mStartPointX,mStartPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);
        canvas.drawLine(mEndPointX,mEndPointY,mAuxiliaryX,mAuxiliaryY,mPaintAuxiliary);

        //二阶贝塞尔曲线
        mPath.quadTo(mAuxiliaryX,mAuxiliaryY,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryX=event.getX();
                mAuxiliaryY=event.getY();
                invalidate();
        }
        return true;
    }
}
