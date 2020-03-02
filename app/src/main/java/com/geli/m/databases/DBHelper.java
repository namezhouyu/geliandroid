package com.geli.m.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pks on 2017/6/15.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "geli.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //搜索过的城市
//        db.execSQL("CREATE TABLE IF NOT EXISTS historyCity(cityId INTEGER,cityName VARCHAR)");

        //搜索过的县区 countyId 县区id，countyName：县区名称
        db.execSQL("CREATE TABLE IF NOT EXISTS historyCounty(countyId INTEGER PRIMARY KEY,countyName VARCHAR)");

        //搜索输入历史， createTime 插入时间
        db.execSQL("CREATE TABLE IF NOT EXISTS searchHistory(id INTEGER PRIMARY KEY AUTOINCREMENT, input_content TEXT,createTime TIMESTAMP)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE historyCounty ADD COLUMN other STRING");
        db.execSQL("ALTER TABLE searchHistory ADD COLUMN other STRING");
    }
}
