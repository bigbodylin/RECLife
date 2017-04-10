package bigbody.project.com.reclife.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shuttlemacmini on 2017/2/15.
 */

public class RECLifeSQLiteHelper extends SQLiteOpenHelper {


    private final static int DB_VERSION = 1;  // first version in 2017/02/15
    private String TAG = "RECLifeSQLiteHelper"; // log string tag
    private Activity mActivity;
    private RECLifeSQLiteStatement mRECLifeSQLiteStatement = new RECLifeSQLiteStatement();


    public RECLifeSQLiteHelper(Context context, String accountName, Activity activity) {
        super(context, accountName, null, DB_VERSION);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Log.e(TAG,"DB onCreate()");
            // create RECLife app database table.
            db.execSQL(mRECLifeSQLiteStatement.create_store_tb);
            db.execSQL(mRECLifeSQLiteStatement.create_store_type_tb);

            db.execSQL("PRAGMA foreign_keys=ON");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // here to upgrade the database version.
        int deltaVersion = 0;
//        for (int i = oldVersion; i < newVersion; i++) {
//
//        }
    }
}
