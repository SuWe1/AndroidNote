package com.ysw.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Swy on 2017/3/29.
 */

public class ReamlHelper {
    public static final String DATABASE_NAME="androidnote_datebase";

    public static Realm newRealmInstance(){
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(ReamlHelper.DATABASE_NAME)
                .build());
    }
}
