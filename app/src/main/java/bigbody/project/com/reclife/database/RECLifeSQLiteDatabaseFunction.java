package bigbody.project.com.reclife.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import bigbody.project.com.reclife.database.datatable.StoreTable;
import bigbody.project.com.reclife.database.datatable.StoreTypeTable;

/**
 * Created by shuttlemacmini on 2017/2/16.
 */

public class RECLifeSQLiteDatabaseFunction {
    // new table class.
    private StoreTable mStoreTable = new StoreTable();
    private StoreTypeTable mStoreTypeTable = new StoreTypeTable();

    private RECLifeSQLiteHelper mRECLifeSQLiteHelper;

    private static SQLiteDatabase SQLdb;
    private Cursor searchResult;
    private String TAG = "RECLifeSQLiteDatabaseFunction";

    /**
     * create HomeCare Database,if it is not exist,it will be create.
     *
     * @param context Input context from your application.
     */
    public void createHomeCareDB(Context context, String id,
                                 Activity activity) {

        mRECLifeSQLiteHelper = new RECLifeSQLiteHelper(context, id, activity); // new database
        // helper
        mRECLifeSQLiteHelper.getWritableDatabase(); // if it is not exist,database
        // will be created
        mRECLifeSQLiteHelper.close();
    }

    /**
     * If you open or use the database, you should close this database in the end.
     */
    public void closeHomeCareDB() {
        mRECLifeSQLiteHelper.close();
    }

    /**
     * If you want to write data to database,you should get writable from database.
     */
    public void getWriteHomeCareDB() {
        mRECLifeSQLiteHelper.getWritableDatabase();
    }

    /**
     * If you want to read or cursor data from database,you should get readable from database.
     */
    public void getReadHomeCareDB() {
        mRECLifeSQLiteHelper.getReadableDatabase();
    }

    /**
     * close cursor and database if you use cursor or database.
     *
     * @throws Exception
     */
    public void closeCursorAndDatabases() throws Exception {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        try {
            if (searchResult != null) {
                searchResult.close();
            }
            if (SQLdb != null) {
                SQLdb.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // insert function
    public void insertStoreSimpleData(String id, String name, String address, double latitude, double longitude, int visited, int uploadstatus, float score, String notes, String addtime) throws Exception {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        SQLdb.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mStoreTable.getColumnNameForPlaceID(), id);
        contentValues.put(mStoreTable.getColumnNameForName(), name);
        contentValues.put(mStoreTable.getColumnNameForAddress(), address);
        contentValues.put(mStoreTable.getColumnNameForLatitude(), latitude);
        contentValues.put(mStoreTable.getColumnNameForLongitude(), longitude);
        contentValues.put(mStoreTable.getColumnNameForVisited(), visited);
        contentValues.put(mStoreTable.getColumnNameForUploadStatus(), uploadstatus);
        contentValues.put(mStoreTable.getColumnNameForNotes(), notes);
        contentValues.put(mStoreTable.getColumnNameForUpdateTime(), addtime);
        contentValues.put(mStoreTable.getColumnNameForScore(), score);
        SQLdb.insert(mStoreTable.getTableName(), "", contentValues);
        SQLdb.setTransactionSuccessful();
        SQLdb.endTransaction();
        SQLdb.close();
    }

    public void insertStoreTypeData(int id, String name) {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        SQLdb.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(mStoreTypeTable.getColumnNameForID(), id);
        contentValues.put(mStoreTypeTable.getColumnNameForName(), name);
        SQLdb.insert(mStoreTypeTable.getTableName(), "", contentValues);
        SQLdb.setTransactionSuccessful();
        SQLdb.endTransaction();
        SQLdb.close();
    }


    // update function
    public void updateDatabase(String placeid, String name, String address, String phone, String latitude, String longitude,
                               String notes, String visited, String website, String score, String type,
                               String photos, String updatetime, String uploadstatus) throws Exception {
        String SQLiteString = null;
        String setSQLiteStringForLocation = null;
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        SQLdb.beginTransaction();
        try {
            SQLiteString = mStoreTable.getColumnNameForName() + " = '" + name + "',"
                    + mStoreTable.getColumnNameForAddress() + " = '" + address + "',"
                    + mStoreTable.getColumnNameForPhone() + " = '" + phone + "',"
                    + mStoreTable.getColumnNameForLatitude() + " = '" + latitude + "',"
                    + mStoreTable.getColumnNameForLongitude() + " = '" + longitude + "',"
                    + mStoreTable.getColumnNameForNotes() + " = '" + notes + "',"
                    + mStoreTable.getColumnNameForVisited() + " = '" + visited + "',"
                    + mStoreTable.getColumnNameForWebsite() + " = '" + website + "',"
                    + mStoreTable.getColumnNameForScore() + " = '" + score + "',"
                    + mStoreTable.getColumnNameForType() + " = '" + type + "',"
                    + mStoreTable.getColumnNameForPhotos() + " = '" + photos + "',"
                    + mStoreTable.getColumnNameForUpdateTime() + " = '" + updatetime + "',"
                    + mStoreTable.getColumnNameForUploadStatus() + " = '" + uploadstatus + "'";

            SQLdb.execSQL("UPDATE " + mStoreTable.getTableName() + " SET "
                    + SQLiteString + " WHERE "
                    + mStoreTable.getColumnNameForPlaceID() + " = '"
                    + placeid + "'");
            Log.e(TAG,
                    "UPDATE " + mStoreTable.getTableName() + " SET "
                            + SQLiteString + " WHERE "
                            + mStoreTable.getColumnNameForPlaceID() + " = '"
                            + placeid + "'");

            SQLdb.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            SQLdb.endTransaction();
            SQLdb.close();
        }
    }

    // delete function
    public void removeData(String placeid) {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        try {
            SQLdb.execSQL("DELETE FROM " + mStoreTable.getTableName() + " WHERE " + mStoreTable.getColumnNameForPlaceID() + " = '"
                    + placeid + "'");
            Log.e(TAG, "DELETE FROM " + mStoreTable.getTableName() + " WHERE " + mStoreTable.getColumnNameForPlaceID() + " = '"
                    + placeid + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // search
    public Cursor searchAllStoreData(String placeid)
            throws Exception {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        try {
            searchResult = null;
            searchResult = SQLdb.rawQuery("SELECT * FROM "
                    + mStoreTable.getTableName() + " WHERE "
                    + mStoreTable.getColumnNameForPlaceID() + " = '"
                    + placeid + "'", null);

            Log.e(TAG, "SELECT * FROM "
                    + mStoreTable.getTableName() + " WHERE "
                    + mStoreTable.getColumnNameForPlaceID() + " = '"
                    + placeid + "'");

        } catch (Exception e) {
            throw e;
        }
        return searchResult;
    }


    // search
    public Cursor searchAllStoreData()
            throws Exception {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        try {
            searchResult = null;
            searchResult = SQLdb.rawQuery("SELECT * FROM "
                    + mStoreTable.getTableName(), null);

            Log.e(TAG, "SELECT * FROM "
                    + mStoreTable.getTableName());

        } catch (Exception e) {
            throw e;
        }
        return searchResult;
    }


    // search
    public Cursor searchAllStoreTypeData(Context context)
            throws Exception {
        SQLdb = mRECLifeSQLiteHelper.getWritableDatabase();
        try {
            searchResult = null;
            searchResult = SQLdb.rawQuery("SELECT * FROM "
                    + mStoreTypeTable.getTableName(), null);

            Log.e(TAG, "SELECT * FROM "
                    + mStoreTypeTable.getTableName());

        } catch (Exception e) {
            throw e;
        }
        return searchResult;
    }


}
