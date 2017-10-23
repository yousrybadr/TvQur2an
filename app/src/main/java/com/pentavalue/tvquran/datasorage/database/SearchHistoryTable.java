package com.pentavalue.tvquran.datasorage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.pentavalue.tvquran.model.MapModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by Passant on 6/8/2017.
 */

public class SearchHistoryTable extends AbstractTable {

    private static final String TABLE_NAME = "SearchHistory";

    private static SearchHistoryTable instance;
    private String[] projection = {Fields.KEY, Fields.VALUE};

    private SearchHistoryTable() {
        DatabaseManager.getInstance().addTable(this);
    }

    public static SearchHistoryTable getInstance() {
        if (instance == null)
            instance = new SearchHistoryTable();
        return instance;
    }

    @Override
    protected void create(SQLiteDatabase db) {
        String createSql = "CREATE TABLE " + getTableName() +
                " (" + _ID + " INTEGER PRIMARY KEY " + AUTOINCREMENT + COMMA_SEP +
                Fields.VALUE + TEXT_TYPE + COMMA_SEP +
                Fields.KEY + TEXT_TYPE + ")";
        db.execSQL(createSql);
    }

    private ContentValues getContentValues(MapModel item) {
        ContentValues values = new ContentValues();
        values.put(Fields.VALUE, item.getValue());
        values.put(Fields.KEY, item.getKey());

        return values;
    }

    public void insertHistoryModel(MapModel item) {
        DatabaseManager.getInstance().getWritableDatabase()
                .insert(getTableName(), null, getContentValues(item));
        //MemberNewsTable.getInstance().insertNewsItem( item);
    }

    private MapModel returnHistoryModel(Cursor cursor) {

        MapModel GModel = null;
        GModel = new MapModel(cursor.getString(cursor.getColumnIndex(Fields.KEY))
                , cursor.getString(cursor.getColumnIndex(Fields.VALUE)));

        return GModel;
    }

    public ArrayList<MapModel> GetHistoryList() {
        Cursor cursor = DatabaseManager.getInstance().getWritableDatabase().query(getTableName(), getProjection(), null, null, null, null, null);
        ArrayList<MapModel> mList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            mList = new ArrayList<>(cursor.getCount());
            do {
                MapModel nModel = returnHistoryModel(cursor);

                mList.add(nModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    @Override
    protected void upgrade(SQLiteDatabase db, int newVersion) {

    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String[] getProjection() {
        return projection;
    }

    final static class Fields implements BaseColumns {
        public static final String KEY = "key";
        public static final String VALUE = "search_item";

    }
}
