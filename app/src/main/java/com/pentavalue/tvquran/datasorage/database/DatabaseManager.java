package com.pentavalue.tvquran.datasorage.database;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pentavalue.tvquran.application.ApplicationController;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to manage database operations [Creation - Upgrade] and manage connections to database.
 *
 * @author Morabea
 */
public class DatabaseManager extends SQLiteOpenHelper {

    /**
     * name of the database file
     */
    private static final String DATABASE_NAME = "TVQuranDB.db";
    /**
     * database version that should be incremented if there is any structural changes
     */
    private static final int DATABASE_VERSION = 1;
    private static DatabaseManager instance;
    /**
     * List of registered tables in the application
     */
    private List<AbstractTable> dbTables;
    private SQLiteDatabase db;

    private DatabaseManager() {
        super(ApplicationController.getInstance().getApplicationContext(),
                /*Environment.getExternalStorageDirectory()
                        + File.separator + "EgyServe"
						+ File.separator +*/ DATABASE_NAME, null, DATABASE_VERSION);
        dbTables = new ArrayList<AbstractTable>();
    }

    /**
     * @return An instance of database manager.
     */
    public synchronized static DatabaseManager getInstance() {
        if (instance == null)
            instance = new DatabaseManager();
        return instance;
    }

    public static void dropTable(SQLiteDatabase db, String table) {
        db.execSQL("DROP TABLE IF EXISTS " + table + ";");
    }

    public static void renameTable(SQLiteDatabase db, String table,
                                   String newTable) {
        db.execSQL("ALTER TABLE " + table + " RENAME TO " + newTable + ";");
    }

    public void initializeTables() {
        /*ReasonTable.getInstance();
		PublishMsgTable.getInstance();*/
        HistoryTable.getInstance();
        SearchHistoryTable.getInstance();
        Log.i("DB", "DataBase created");
    }

    public void clearAppDatabase() {
        for (AbstractTable table : dbTables)
            table.clearAll();
    }

    public SQLiteDatabase getDb() {
        if (db == null)
            db = getWritableDatabase();
        return db;
    }

    public void addTable(AbstractTable table) {
        dbTables.add(table);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (AbstractTable table : dbTables)
                table.create(db);
        } catch (SQLException e) {
            Log.i("DatabaseonCreate", "An error has occured while creating database tables");
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < newVersion) {
                for (AbstractTable table : dbTables)
                    table.upgrade(db, newVersion);
            } else {
                for (AbstractTable table : dbTables) {
                    dropTable(db, table.getTableName());
                    onCreate(db);
                }
            }
        } catch (SQLException e) {
            Log.e("DatabaseManagernCreate", "An error has occured while creating database tables");
            e.printStackTrace();
        }
    }

}
