package com.lidan.xiao.danquestion.hepler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/7.
 */

public class ToolHelper {
    public static List<Map<String,String>> CursorToList(Cursor cursor){
        List<Map<String,String>> list=new ArrayList<>();
        int colums = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            Map<String,String> map=new HashMap<>();
            for (int i = 0; i < colums; i++) {
                String columname = cursor.getColumnName(i);//获取每列的列名
                String columvalue = cursor.getString(cursor.getColumnIndex(columname));//获取每列的值
                map.put(columname,columvalue);
                if (columvalue == null) {
                    columvalue = "";
                }
            }
            list.add(map);
        }
        return list;
    }

    public static Cursor loadDB(Context context, String sql) {
        SQLiteRelease sqLiteRelease = new SQLiteRelease(context);
        SQLiteDatabase db = sqLiteRelease.OpenDataBase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
    public static boolean excuteDB(Context context, String sql) {
        SQLiteRelease sqLiteRelease = new SQLiteRelease(context);
        SQLiteDatabase db = sqLiteRelease.OpenDataBase();
        db.execSQL(sql);
        return true;
    }


}
