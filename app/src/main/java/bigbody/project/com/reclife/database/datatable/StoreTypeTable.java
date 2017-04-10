package bigbody.project.com.reclife.database.datatable;

/**
 * Created by shuttlemacmini on 2017/2/16.
 */

public class StoreTypeTable {

    // Store Type Table Name
    private String mTableName = "storetype";
    // Store Table Name
    private String mColumnNameForID = "id";
    private String mColumnNameForName = "typename";


    private String[] mColumnNameForALL =
            {mColumnNameForID, mColumnNameForName};


    public String getTableName() {
        return mTableName;
    }

    public String[] getTableALLColumn() {
        return mColumnNameForALL;
    }

    public String getColumnNameForID() {
        return mColumnNameForID;
    }

    public String getColumnNameForName() {
        return mColumnNameForName;
    }



}
