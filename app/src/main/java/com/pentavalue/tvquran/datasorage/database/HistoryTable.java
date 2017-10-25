package com.pentavalue.tvquran.datasorage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.pentavalue.tvquran.model.Entries;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

/**
 * Created by Passant on 6/8/2017.
 */

public class HistoryTable extends AbstractTable {

    private static final String TABLE_NAME = "HistoryTable";

    private static HistoryTable instance;
    private String[] projection = {Fields.SURA_ID, Fields.TITLE, Fields.AUTHOR_ID, Fields.CATEGORY_DESC,
            Fields.CONTENT, Fields.VIEWCOUNTS, Fields.PATH, Fields.RECITER_NAME, Fields.RECITER_NAME, Fields.RECITER_PHOTO
            , Fields.UP_VOTES, Fields.CATEGORY_NAME, Fields.IS_DOWNLOADED, Fields.DOWNLOADED_PATH,
            Fields.IS_HISTORY};

    private HistoryTable() {
        DatabaseManager.getInstance().addTable(this);
    }

    public static HistoryTable getInstance() {
        if (instance == null)
            instance = new HistoryTable();
        return instance;
    }

    public void deleteFromHistory(int itemId) {
        DatabaseManager.getInstance().getWritableDatabase().delete(TABLE_NAME, "is_Downloaded=1 and " + Fields.SURA_ID + "=" + itemId, null);
    }

    @Override
    protected void create(SQLiteDatabase db) {
        String createSql = "CREATE TABLE " + getTableName() +
                " (" + _ID + " INTEGER PRIMARY KEY " + AUTOINCREMENT + COMMA_SEP +
                Fields.TITLE + TEXT_TYPE + COMMA_SEP +
                Fields.RECITER_NAME + TEXT_TYPE + COMMA_SEP +
                Fields.RECITER_PHOTO + TEXT_TYPE + COMMA_SEP +
                Fields.CATEGORY_DESC + TEXT_TYPE + COMMA_SEP +
                Fields.PATH + TEXT_TYPE + COMMA_SEP +
                Fields.DOWNLOADED_PATH + TEXT_TYPE + COMMA_SEP +
                Fields.CONTENT + TEXT_TYPE + COMMA_SEP +
                Fields.SURA_ID + NUMBER_TYPE + COMMA_SEP +
                Fields.UP_VOTES + NUMBER_TYPE + COMMA_SEP +
                Fields.VIEWCOUNTS + NUMBER_TYPE + COMMA_SEP +
                Fields.AUTHOR_ID + NUMBER_TYPE + COMMA_SEP +
                Fields.IS_DOWNLOADED + NUMBER_TYPE + COMMA_SEP +
                Fields.IS_HISTORY + NUMBER_TYPE + COMMA_SEP +
                Fields.CATEGORY_NAME + TEXT_TYPE + ")";
        db.execSQL(createSql);
    }

    private ContentValues getContentValues(Entries item) {
        ContentValues values = new ContentValues();
        values.put(Fields.TITLE, item.getTitle());
        values.put(Fields.SURA_ID, item.getId());
        values.put(Fields.PATH, item.getSoundPath());
        values.put(Fields.RECITER_NAME, item.getReciter_name());
        values.put(Fields.RECITER_PHOTO, item.getReciter_photo());
        values.put(Fields.CATEGORY_DESC, item.getCategory_desc());
        values.put(Fields.CATEGORY_NAME, item.getCategory_name());
        values.put(Fields.AUTHOR_ID, item.getAuthor_id());
        values.put(Fields.CONTENT, item.getContent());
        values.put(Fields.UP_VOTES, item.getUpVotes());
        values.put(Fields.VIEWCOUNTS, item.getViews_count());
        values.put(Fields.IS_DOWNLOADED, item.getIsDownloaded());
        values.put(Fields.IS_HISTORY, item.getIsHistory());
        values.put(Fields.DOWNLOADED_PATH, item.getDownloadedPath());

        return values;
    }

    public void insertDownLoadyModel(Entries item) {

        //  if(getHistoryByID(item.getId())==null)
        DatabaseManager.getInstance().getWritableDatabase()
                .insert(getTableName(), null, getContentValues(item));
        //MemberNewsTable.getInstance().insertNewsItem( item);
    }

    public void insertHistoryModel(Entries item) {

        if (getHistoryByID(item.getId()) == null)
            DatabaseManager.getInstance().getWritableDatabase()
                    .insert(getTableName(), null, getContentValues(item));
        //MemberNewsTable.getInstance().insertNewsItem( item);
    }

    public Entries getHistoryByID(int id) {
        Cursor cursor = DatabaseManager.getInstance().getWritableDatabase().query(getTableName(), getProjection(), " is_History=1 and " + Fields.SURA_ID + "=" + id, null, null, null, null);
        Entries model = null;
        if (cursor.moveToFirst()) {

            model = returnHistoryModel(cursor);
        }
        cursor.close();
        return model;
    }

    public Entries getDownloadByID(int id) {
        Cursor cursor = DatabaseManager.getInstance().getWritableDatabase().query(getTableName(), getProjection(), " is_Downloaded=1 and " + Fields.SURA_ID + "=" + id, null, null, null, null);
        Entries model = null;
        if (cursor.moveToFirst()) {

            model = returnHistoryModel(cursor);
        }
        cursor.close();
        return model;
    }

    public void updateDownloadItem(int id, String downloadPath) {

        ContentValues cv = new ContentValues();
        cv.put(Fields.SURA_ID, id);
        cv.put(Fields.DOWNLOADED_PATH, downloadPath);
        DatabaseManager.getInstance().getWritableDatabase().update(TABLE_NAME, cv, Fields.SURA_ID + "=" + id + " and is_Downloaded=1  ", null);

    }

    private Entries returnHistoryModel(Cursor cursor) {

        Entries GModel = null;
        GModel = new Entries(cursor.getInt(cursor.getColumnIndex(Fields.SURA_ID)), cursor.getInt(cursor.getColumnIndex(Fields.AUTHOR_ID)),
                cursor.getString(cursor.getColumnIndex(Fields.TITLE)), cursor.getString(cursor.getColumnIndex(Fields.CONTENT)),
                cursor.getInt(cursor.getColumnIndex(Fields.VIEWCOUNTS)), cursor.getString(cursor.getColumnIndex(Fields.RECITER_NAME)),
                cursor.getString(cursor.getColumnIndex(Fields.RECITER_PHOTO)),
                cursor.getString(cursor.getColumnIndex(Fields.CATEGORY_NAME)),
                cursor.getString(cursor.getColumnIndex(Fields.CATEGORY_DESC)), cursor.getString(cursor.getColumnIndex(Fields.PATH)),
                cursor.getInt(cursor.getColumnIndex(Fields.UP_VOTES)), cursor.getInt(cursor.getColumnIndex(Fields.UP_VOTES)), cursor.getInt(cursor.getColumnIndex(Fields.UP_VOTES)),
                cursor.getString(cursor.getColumnIndex(Fields.DOWNLOADED_PATH)));

        return GModel;
    }


    public ArrayList<Entries> GetHistoryList() {
        Cursor cursor = DatabaseManager.getInstance().getWritableDatabase().query(getTableName(), getProjection(), " is_History=1", null, null, null, _ID + " DESC");
        ArrayList<Entries> mList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            mList = new ArrayList<>(cursor.getCount());
            do {
                Entries nModel = returnHistoryModel(cursor);

                mList.add(nModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public ArrayList<Entries> GetDownloadedList() {
        Cursor cursor = DatabaseManager.getInstance().
                getWritableDatabase().query(getTableName(), getProjection(), " is_Downloaded=1 ", null, null, null, "_id  DESC");
        ArrayList<Entries> mList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            mList = new ArrayList<>(cursor.getCount());
            do {
                Entries nModel = returnHistoryModel(cursor);

                mList.add(nModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return mList;
    }

    public void deleteHistory() {

        DatabaseManager.getInstance()
                .getWritableDatabase().delete(getTableName(),
                "is_History=1 ", null);
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
        public static final String SURA_ID = "id";
        public static final String TITLE = "client_address";
        public static final String AUTHOR_ID = "author_id";
        public static final String CONTENT = "content";
        public static final String VIEWCOUNTS = "views_count";
        public static final String RECITER_NAME = "reciter_name";
        public static final String RECITER_PHOTO = "reciter_photo";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CATEGORY_DESC = "category_desc";
        public static final String PATH = "path";
        public static final String UP_VOTES = "up_votes";
        public static final String IS_HISTORY = "is_History";
        public static final String IS_DOWNLOADED = "is_Downloaded";
        public static final String DOWNLOADED_PATH = "downloaded_path";

    }

}
