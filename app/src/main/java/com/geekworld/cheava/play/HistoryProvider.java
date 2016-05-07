package com.geekworld.cheava.play;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by wangzh on 2016/5/6.
 */
public class HistoryProvider extends ContentProvider {
    public static final int MSG_DIR = 0;
    public static final int MSG_ITEM = 1;
    public static final int TIME_DIR = 2;
    public static final int TIME_ITEM = 3;

    public static final String AUTHORITY = "com.geekworld.cheava.provider";

    private static UriMatcher uriMatcher;
    private DatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"msg",MSG_DIR);
        uriMatcher.addURI(AUTHORITY,"msg/#",MSG_ITEM);
        uriMatcher.addURI(AUTHORITY,"time",TIME_DIR);
        uriMatcher.addURI(AUTHORITY,"time/#",TIME_ITEM);
    }

    @Override
    public boolean onCreate(){
        dbHelper =  new DatabaseHelper(getContext(),"History.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case MSG_DIR:
                cursor = db.query("msg",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case MSG_ITEM:
                String msgId = uri.getPathSegments().get(1);
                cursor = db.query("msg",projection,"id = ?", new String[]{msgId},null,null,sortOrder);
                break;

            case TIME_DIR:
                cursor = db.query("time",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case TIME_ITEM:
                String timeId = uri.getPathSegments().get(1);
                cursor = db.query("time",projection,"id = ?",new String[]{timeId},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case MSG_DIR:
                updatedRows = db.update("msg",values,selection,selectionArgs);
                break;

            case MSG_ITEM:
                String msgId = uri.getPathSegments().get(1);
                updatedRows = db.update("msg",values,"id = ?", new String[]{msgId});
                break;

            case TIME_DIR:
                updatedRows = db.update("time",values,selection,selectionArgs);
                break;

            case TIME_ITEM:
                String timeId = uri.getPathSegments().get(1);
                updatedRows = db.update("time",values,"id = ?",new String[]{timeId});
                break;
        }
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case MSG_DIR:
                deletedRows = db.delete("msg",selection,selectionArgs);
                break;

            case MSG_ITEM:
                String msgId = uri.getPathSegments().get(1);
                deletedRows = db.delete("msg","id = ?", new String[]{msgId});
                break;

            case TIME_DIR:
                deletedRows = db.delete("time",selection,selectionArgs);
                break;

            case TIME_ITEM:
                String timeId = uri.getPathSegments().get(1);
                deletedRows = db.delete("time","id = ?",new String[]{timeId});
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case MSG_DIR:
                return "vnd.android.cursor.dir/vnd.com.geekworld.cheva.provider.msg";
            case MSG_ITEM:
                return "vnd.android.cursor.item/vnd.com.geekworld.cheva.provider.msg";
            case TIME_DIR:
                return "vnd.android.cursor.dir/vnd.com.geekworld.cheva.provider.time";
            case TIME_ITEM:
                return "vnd.android.cursor.item/vnd.com.geekworld.cheva.provider.time";
        }
        return null;
    }
}
