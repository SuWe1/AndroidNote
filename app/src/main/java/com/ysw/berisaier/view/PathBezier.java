package com.ysw.berisaier.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ysw.berisaier.evaluate.BezierEvaluator;

/**
 * Created by 11033 on 2017/2/28.
 */

public class PathBezier extends View implements View.OnClickListener {
    private Paint mPathPaint;
    private Paint mCriclePaint;

    private int mStartPointX;
    private int mStartPointY;
    private int mEndPointX;
    private int mEndPointY;

    private int mMovePointX;
    private int mMovePointY;

    private int mControlPointX;
    private int mControlPointY;

    private Path mPath=new Path();
    public PathBezier(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PathBezier(Context context) {
        super(context);
    }

    public PathBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(5);
        mCriclePaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPointX=100;
        mStartPointY=100;
        mEndPointX=600;
        mEndPointY=600;
        mMovePointX=mStartPointX;
        mMovePointY=mStartPointY;
        mControlPointX =500;
        mControlPointY =0;
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        canvas.drawCircle(mStartPointX,mStartPointY,30,mCriclePaint);
        canvas.drawCircle(mEndPointX,mEndPointY,30,mCriclePaint);
        mPath.moveTo(mStartPointX,mStartPointY);
        mPath.quadTo(mControlPointX, mControlPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mPathPaint);
        canvas.drawCircle(mMovePointX,mMovePointY,30,mCriclePaint);
    }

    @Override
    public void onClick(View v) {
        final BezierEvaluator bezierEvaluator=new BezierEvaluator(new PointF(mControlPointX,mControlPointY));
        ValueAnimator animator=ValueAnimator.ofObject(bezierEvaluator,new PointF(mStartPointX,mStartPointY),
                new PointF(mEndPointX,mEndPointY));
        animator.setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF= (PointF) animation.getAnimatedValue();
                mMovePointX= (int) pointF.x;
                mMovePointY= (int) pointF.y;
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
}
