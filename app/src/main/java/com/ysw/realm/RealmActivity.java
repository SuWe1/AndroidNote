package com.ysw.realm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ysw.R;

import java.util.List;

import io.realm.Realm;

import static com.ysw.realm.ReamlHelper.newRealmInstance;

/**
 * Created by Swy on 2017/3/29.
 */

public class RealmActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RealmActivity";
    Button btn_insert,btn_delete,btn_search,btn_update,btn_searchCondition;
    Realm realm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realm_main);
        initView();
        Realm.init(this);//这一句可以放在自己的app.class中
        realm= ReamlHelper.newRealmInstance();
    }
    private void initView(){
        btn_insert= (Button) findViewById(R.id.btn_insert);
        btn_delete= (Button) findViewById(R.id.btn_delete);
        btn_search= (Button) findViewById(R.id.btn_search);
        btn_update= (Button) findViewById(R.id.btn_update);
        btn_searchCondition= (Button) findViewById(R.id.btn_searchCondition);
        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_searchCondition.setOnClickListener(this);
    }

    private void insert(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i=1;i<10;i++){
                    ResultBean resultsBean= newRealmInstance().createObject(ResultBean.class,i);
                    resultsBean.set_id("_id"+i);
                    resultsBean.setDesc("desc"+i);
                    resultsBean.setUrl("url"+i);
                }
                Toast.makeText(RealmActivity.this,"十条数据添加成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 条件查询，Realm 支持以下查询条件（来源于官网）：
     between()、greaterThan()、lessThan()、greaterThanOrEqualTo() 和 lessThanOrEqualTo()
     equalTo() 和 notEqualTo()
     contains()、beginsWith() 和 endsWith()
     isNull() 和 isNotNull()
     isEmpty() 和 isNotEmpty()
     */
    private void searchAll(){
        List<ResultBean> list=realm.where(ResultBean.class).findAll();
        for (ResultBean resultsBean:list){
            Log.i(TAG, "searchAll--- _id="+resultsBean.get_id()+" desc="+resultsBean.getDesc()+" url="+resultsBean.getUrl());
        }
    }

    private void searchCondition(){
        List<ResultBean> list=realm.where(ResultBean.class).lessThan("id",5).findAll();
        for (ResultBean resultsBean:list){
            Log.i(TAG, "searchCondition--- _id="+resultsBean.get_id()+" desc="+resultsBean.getDesc()+" url="+resultsBean.getUrl());
        }
    }


    private void update(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ResultBean resultsbean=realm.where(ResultBean.class).equalTo("_id","5").findFirst();
                if (resultsbean!=null){
                    resultsbean.setUrl("url更改了");
                    resultsbean.setDesc("desc更新了");
                }
                Toast.makeText(RealmActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void delete(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ResultBean resultsbean=realm.where(ResultBean.class).equalTo("id",9).findFirst();
                if (resultsbean!=null){
                    resultsbean.deleteFromRealm();
                }
                Toast.makeText(RealmActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_search:
                searchAll();
                break;
            case R.id.btn_searchCondition:
                searchCondition();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        realm.close();
    }
}
