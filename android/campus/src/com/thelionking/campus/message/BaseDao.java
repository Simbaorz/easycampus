package com.thelionking.campus.message;

import java.util.List;

import android.content.Context;

/**
 * Created by the lion king on 2014/9/27.
 */
public abstract class BaseDao<T> {

    protected DBHelper helper = null;

    protected Context context = null;

    public BaseDao(Context context) {
        this.helper = new DBHelper(context);
        this.context = context;
    }

    /**
     * 将一批数据存入数据库
     * @param data
     */
    public abstract boolean save(List<T> data, String tableName);

    public abstract List<T> getAll(String tableName);

}
