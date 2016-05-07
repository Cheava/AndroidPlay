package com.geekworld.cheava.play;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangzh on 2016/5/7.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public static final String CREATE_HISTORY = "create table history ("
            + "id integer primary key autoincrement, "
            + "time string, "
            + "msg string)";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //db.execSQL(CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists History");
        onCreate(db);
    }
}
