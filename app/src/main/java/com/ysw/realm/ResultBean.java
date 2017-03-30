package com.ysw.realm;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Swy on 2017/3/29.
 * RealmClass不支持嵌套类
 */

public class ResultBean extends RealmObject {
    /**
     * _id : 58d4e454421aa93abd1fd15a
     * createdAt : 2017-03-24T17:18:12.745Z
     * desc : RecyclerView侧滑菜单
     * images : ["http://img.gank.io/99a9d510-195d-4d50-a310-13b098c0c776"]
     * publishedAt : 2017-03-29T11:48:49.343Z
     * source : web
     * type : Android
     * url : http://www.jianshu.com/p/af9f940d8d1c
     * used : true
     * who : pss
     */
    /**
     * @PrimaryKey用来标识主键
    默认的所有的字段都会被存储
    如果某个字段不需要被存储到本地，则需在在这个字段上面加上@Ignore注解

    Primary keys are immutable since Realm 2.0.0, which means you cannot modify them after the object has been created.
     */
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String _id;
    private String desc;
    private String url;
    @Ignore
    private boolean used;
    @Ignore
    private String who;
    @Ignore
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}