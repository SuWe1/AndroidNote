package com.ysw.berisaier.evaluate;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.ysw.berisaier.util.BezierUtil;

/**
 * Created by 11033 on 2017/2/28.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {
    private  PointF mControlPoint;

    public BezierEvaluator(PointF mControlPoint) {
        this.mControlPoint = mControlPoint;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(fraction,startValue,mControlPoint,endValue);
    }
}
