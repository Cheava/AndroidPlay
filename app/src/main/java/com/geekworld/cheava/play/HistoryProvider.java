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
    public static final int HISTORY_DIR = 0;
    public static final int HISTORY_ITEM = 1;

    public static final String AUTHORITY = "com.geekworld.cheava.provider";

    private static UriMatcher uriMatcher;
    private DatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"history",HISTORY_DIR);
        uriMatcher.addURI(AUTHORITY,"history/#",HISTORY_ITEM);
    }

    @Override
    public boolean onCreate(){
        dbHelper =  new DatabaseHelper(getContext(),"History.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case HISTORY_DIR:
                cursor = db.query("history",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case HISTORY_ITEM:
                String msgId = uri.getPathSegments().get(1);
                cursor = db.query("history",projection,"id = ?", new String[]{msgId},null,null,sortOrder);
                break;

            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case HISTORY_DIR:
            case HISTORY_ITEM:
                long newBookId = db.insert("history", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/history/" + newBookId);
                break;

            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case HISTORY_DIR:
                updatedRows = db.update("history",values,selection,selectionArgs);
                break;

            case HISTORY_ITEM:
                String msgId = uri.getPathSegments().get(1);
                updatedRows = db.update("history",values,"id = ?", new String[]{msgId});
                break;

        }
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case HISTORY_DIR:
                deletedRows = db.delete("history",selection,selectionArgs);
                break;

            case HISTORY_ITEM:
                String msgId = uri.getPathSegments().get(1);
                deletedRows = db.delete("history","id = ?", new String[]{msgId});
                break;

        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case HISTORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.geekworld.cheva.provider.history";
            case HISTORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.geekworld.cheva.provider.history";

        }
        return null;
    }
}
