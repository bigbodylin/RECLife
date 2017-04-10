package bigbody.project.com.reclife.database;

import bigbody.project.com.reclife.database.datatable.StoreTable;
import bigbody.project.com.reclife.database.datatable.StoreTypeTable;

/**
 * Created by shuttlemacmini on 2017/2/15.
 */

public class RECLifeSQLiteStatement {

    // new table class.
    private StoreTable mStoreTable = new StoreTable();
    private StoreTypeTable mStoreTypeTable = new StoreTypeTable();


    /**
     * Create database table statement
     */
    public String create_store_tb = "CREATE TABLE IF NOT EXISTS "
            + mStoreTable.getTableName() + "("
            + "_id INTEGER PRIMARY KEY NOT NULL,"
            + mStoreTable.getColumnNameForPlaceID() + " CHAR(64) UNIQUE,"
            + mStoreTable.getColumnNameForName() + " CHAR(64),"
            + mStoreTable.getColumnNameForAddress() + " VARCHAR(256),"
            + mStoreTable.getColumnNameForPhone() + " CHAR(30),"
            + mStoreTable.getColumnNameForLatitude() + " CHAR(32),"
            + mStoreTable.getColumnNameForLongitude() + " CHAR(32),"
            + mStoreTable.getColumnNameForNotes() + " VARCHAR(256),"
            + mStoreTable.getColumnNameForVisited() + " CHAR(4),"
            + mStoreTable.getColumnNameForWebsite() + " CHAR(128),"
            + mStoreTable.getColumnNameForScore() + " CHAR(32),"
            + mStoreTable.getColumnNameForType() + " CHAR(32),"
            + mStoreTable.getColumnNameForPhotos() + " VARCHAR(256),"
            + mStoreTable.getColumnNameForUpdateTime() + " DATETIME,"
            + mStoreTable.getColumnNameForUploadStatus() + " INT,"
            + "FOREIGN KEY(" + mStoreTable.getColumnNameForType()
            + ") REFERENCES " + mStoreTypeTable.getTableName() + "("
            + mStoreTypeTable.getColumnNameForID() + "))";

    public String create_store_type_tb = "CREATE TABLE IF NOT EXISTS "
            + mStoreTypeTable.getTableName() + "("
            + "_id INTEGER PRIMARY KEY NOT NULL,"
            + mStoreTypeTable.getColumnNameForID() + " INT UNIQUE,"
            + mStoreTypeTable.getColumnNameForName() + " CHAR(64))";

}
