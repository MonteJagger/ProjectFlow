package com.example.flow.projectflow;

/**
 * Created by Awesome-O on 6/7/2016.
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class DegreePlanAdapter {

    public static final String KEY_ROWID = "rowid _id,*";
    public static final String KEY_DEPARTMENT = "department";
    public static final String KEY_YEAR = "year";
    public static final String KEY_MAJOR = "major";

    public static final String KEY_SEARCH = "searchData";

    private static final String TAG = "DegreePlanAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "DegreePlanData";
    private static final String FTS_VIRTUAL_TABLE = "DegreePlans";
    private static final int DATABASE_VERSION = 1;

    //Create a FTS3 Virtual Table for fast searches
    private static final String DATABASE_CREATE =
            "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts3(" +
                    //KEY_ROWID + "integer PRIMARY KEY autoincrement," +
                    KEY_DEPARTMENT + "," +
                    KEY_YEAR + "," +
                    KEY_MAJOR + "," +
                    KEY_SEARCH + "," +
                    " UNIQUE (" + KEY_YEAR + "));";


    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }

    public DegreePlanAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DegreePlanAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createDegree(String department, String year, String major) {

        ContentValues initialValues = new ContentValues();
        String searchValue =     department + " " +
                year + " " + major;
        initialValues.put(KEY_DEPARTMENT, department);
        initialValues.put(KEY_YEAR, year);
        initialValues.put(KEY_MAJOR, major);
        initialValues.put(KEY_SEARCH, searchValue);
        return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
    }

public Cursor searchDegree(String inputText) throws SQLException {
    Log.w(TAG, inputText);
    Cursor mCursor = mDb.query(true, FTS_VIRTUAL_TABLE, new String[] {KEY_ROWID,KEY_DEPARTMENT,
                    KEY_YEAR, KEY_MAJOR}, KEY_SEARCH + " like '%" + inputText + "%' " , null,
            KEY_MAJOR, null, KEY_MAJOR,null);
    if (mCursor != null) {
        mCursor.moveToFirst();
    }
    return mCursor;
}

    public Cursor searchYear(String inputMajor) throws SQLException {
        Log.w(TAG, inputMajor);
        String query = "SELECT docid as _id," +
                KEY_YEAR  +
                " from " + FTS_VIRTUAL_TABLE +
                " where " +  KEY_MAJOR + " MATCH '" + inputMajor + "';";
        Log.w(TAG, query);
        Cursor mCursor = mDb.rawQuery(query,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public boolean deleteDegree() {

        int doneDelete = 0;
        doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

}