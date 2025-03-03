package com.example.testapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import android.database.Cursor;
import android.util.Log;

public class DatabaseManager {

    private static final String ROW_ID = "_id";
    private static final String ROW_NAMA = "nama";
    private static final String ROW_TELP = "telp";
    private static final String NAMA_DB = "database1";
    private static final String NAMA_TABEL = "tblpelanggan";
    private static final int DB_VERSION = 3;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + NAMA_TABEL +
            " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ROW_NAMA + " TEXT, " + ROW_TELP + " TEXT)";

    private final Context context;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(context);
        setDb(dbHelper.getWritableDatabase());
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_TABEL);
            onCreate(db);
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void UpdateRecord(int iId, String sName, String sTelp) {
        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, sName);
        values.put(ROW_TELP, sTelp);

        try {
            db.update(NAMA_TABEL, values, ROW_ID + "=" + iId, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void DeleteRecord(int iId) {
        try {
            db.delete(NAMA_TABEL, ROW_ID + "=" + iId, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void addRow(int iId, String aNama, String aTelp) {
        ContentValues values = new ContentValues();
        values.put(ROW_NAMA, aNama);
        values.put(ROW_TELP, aTelp);

        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<>();
        Cursor cur;

        try {
            cur = db.query(NAMA_TABEL, new String[]{ROW_ID, ROW_NAMA, ROW_TELP},
                    null, null, null, null, null);

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                ArrayList<Object> dataList = new ArrayList<>();
                dataList.add(cur.getLong(0));
                dataList.add(cur.getString(1));
                dataList.add(cur.getString(2));
                dataArray.add(dataList);
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Database ERROR", e.toString());
            e.printStackTrace();
        }
        return dataArray;
    }
}

