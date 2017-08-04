package com.ysw.annotations_.myannotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Swy on 2017/8/4.
 */



@IntDef({1,2,3})
@Retention(RetentionPolicy.SOURCE)
public @interface Nums{}
