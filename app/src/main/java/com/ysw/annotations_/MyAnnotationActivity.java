package com.ysw.annotations_;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.ysw.R;
import com.ysw.annotations_.myannotation.Nums;
import com.ysw.annotations_.myannotation.getViewTo;

import java.lang.reflect.Field;

/**
 * Created by Swy on 2017/8/4.
 *
 *
 */

public class MyAnnotationActivity extends AppCompatActivity {

    private int num;

    /**
     * IntDef和StringDef 是两个魔术变量注解. 使用这个两个来替代之前使用的Enum.
     * 它将帮助我们在编译代码时期像Enum那样选择变量的功能
     *
     声明一些必要的 int 常量
     声明一个注解为 Nums
     使用 @IntDef 修饰 Nums,参数设置为待枚举的集合
     使用 @Retention(RetentionPolicy.SOURCE) 指定注解仅存在与源码中,不加入到 class 文件中
     */



    @Nums
    int currentNum=1;


    private void setNum(@Nums int num){
        this.num=num;
    }




    @getViewTo(R.id.annotationBtn)
    private Button annotationBtn;

    @getViewTo(R.id.annotationTV)
    private TextView annotationTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_annotation_main);
        setNum(1);
        try {
            getAllAnnotationView();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //注解解析  获取控件
    private void getAllAnnotationView() throws IllegalAccessException {
        //反射获取成员变量
        Field [] fields= this.getClass().getDeclaredFields();
        for (Field field : fields) {
            //判断注解
            if (field.getAnnotations()!=null){
                //判断注解类型
                if (field.isAnnotationPresent(getViewTo.class)){
                    field.setAccessible(true);//允许修改反射属性
                    getViewTo getViewTo=field.getAnnotation(com.ysw.annotations_.myannotation.getViewTo.class);
                    field.set(this,findViewById(getViewTo.value()));
                }
            }
        }
    }








}
