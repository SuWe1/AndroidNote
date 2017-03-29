package com.ysw.rxjavaretrofit.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ysw.R;
import com.ysw.rxjavaretrofit.Network;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Swy on 2017/3/28.
 */

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";
    private RecyclerView recyclerView;
    private ItemAdapter adapter=new ItemAdapter();

    private int page=1;
    Observer<List<Item>> observer=new Observer<List<Item>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG, "onError: "+e.getLocalizedMessage());
            Toast.makeText(MapActivity.this,"fail",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Item> items) {
            adapter.setItems(items);
            Toast.makeText(MapActivity.this,"success",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvjava_retrofit_map);
        recyclerView= (RecyclerView) findViewById(R.id.recycleView_map);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private void load(int page){
        Network.getMeiziEntity().getMeizi(10,page)
                .map(GankMeiziResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadImg(View view) {
        load(1);
    }

    public void loadNext(View view) {
        page++;
        load(page);
    }

    public void loadprevious(View view) {
        if ((page-1)>0){
            page--;
            load(page);
        }
        load(1);
    }
}
