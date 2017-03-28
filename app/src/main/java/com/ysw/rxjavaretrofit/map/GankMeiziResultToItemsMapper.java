package com.ysw.rxjavaretrofit.map;

import android.util.Log;

import com.ysw.rxjavaretrofit.MeiziEntity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by Swy on 2017/3/28.
 * 和 ActionX 一样， FuncX 也有多个，用于不同参数个数的方法。FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
 */

//第一个为输入参数类型 第二个为还回参数类型
public class GankMeiziResultToItemsMapper  implements Func1<MeiziEntity,List<Item>>{
    private static final String TAG = "GankMeiziResultToItemsM";
    private static GankMeiziResultToItemsMapper gankMeiziResultToItemsMapper;
    public GankMeiziResultToItemsMapper() {
    }
    public static GankMeiziResultToItemsMapper getInstance(){return gankMeiziResultToItemsMapper;}


    /**
     * 这里值是为了演示怎么用所有重新用list装载图片url 平常使用一般是直接通过MeiziEntity.ResultsBean获取单个数据所有信息
     * @param meiziEntity 输入参数类型
     * @return   还回参数类型
     */
    @Override
    public List<Item> call(MeiziEntity meiziEntity) {
        List<MeiziEntity.ResultsBean> meiziList=meiziEntity.getResults();
        List<Item> items=new ArrayList<>(meiziList.size());
        Log.i(TAG, "call: "+meiziList.size());
        for (MeiziEntity.ResultsBean resultsBean: meiziList){
            Item item=new Item();
            item.imageUrl=resultsBean.getUrl();
            items.add(item);
        }
        return items;
    }
}
