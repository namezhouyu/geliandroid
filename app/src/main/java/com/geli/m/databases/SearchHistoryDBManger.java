package com.geli.m.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.geli.m.bean.SearchEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pks on 2017/6/15.
 * 数据库，帮助类
 */
public class SearchHistoryDBManger {
    private DBHelper helper;
    private SQLiteDatabase db;

    public SearchHistoryDBManger(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }


    /**
     * @param searchEntity
     */
    public synchronized void add(SearchEntity searchEntity) {
        if (searchEntity == null && searchEntity.getInputContent().isEmpty()) {
            return;
        }
        List<SearchEntity> list = new ArrayList<>();
        list = getHistoryList();
        String inputS = searchEntity.getInputContent();
        for (SearchEntity s : list) {
            if (inputS.equals(s.getInputContent())) { // 删除旧的（内容一样的），加入新的记录
                // deleteOldRecord(s.getId());
                // break;
                return;
            }
        }
        db.beginTransaction();  // 开始事务
        try {
            // db.execSQL("INSERT INTO  searchHistory VALUES(null, ?)", new Object[]{inputContent});
            db.execSQL("REPLACE INTO  searchHistory VALUES(null, ?,?)",
                    new Object[]{searchEntity.getInputContent(), searchEntity.getCreateTime()});
            db.setTransactionSuccessful();  // 设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }


    /**
     * 清空历史表中的全部历史记录
     */
    public void deleteAllHistory() {
        db.execSQL("DELETE FROM searchHistory");
    }

    /**
     * 删除表中已经存在的记录
     *
     * @param recordId
     */
    public void deleteOldRecord(String recordId) {
        db.delete("searchHistory", "id = ?", new String[]{recordId});
    }


    /**
     * @return 搜索过集合，如果数据大于10条，只取最新的10条
     */
    public List<SearchEntity> getHistoryList() {
        ArrayList<SearchEntity> strings = new ArrayList<SearchEntity>();

        // Cursor c = queryTheCursor();
        Cursor c = db.rawQuery("SELECT * FROM searchHistory ", null);
        while (c.moveToNext()) {
            SearchEntity entity = new SearchEntity();
            entity.setInputContent(c.getString(c.getColumnIndex("input_content")));
            strings.add(0, entity);
        }
        c.close();
        if (strings.size() > 10) { // 如果数据大于5条，只取最新的5条
            strings.subList(0, 10);
        }
        return strings;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM searchHistory", null);
        return c;
    }


    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }


}
