package bigbody.project.com.reclife.database.datatable;

/**
 * Created by shuttlemacmini on 2017/2/15.
 */

public class StoreTable {

    // Store Table Name
    private String mTableName = "store";
    // Store Table Name
    private String mColumnNameForPlaceID = "placeid";
    private String mColumnNameForName = "name";
    private String mColumnNameForAddress = "address";
    private String mColumnNameForPhone = "phone";
    private String mColumnNameForNotes = "notes";
    private String mColumnNameForScore = "score";
    private String mColumnNameForType = "type";
    private String mColumnNameForVisited = "visited";
    private String mColumnNameForLatitude = "latitude";
    private String mColumnNameForLongitude = "longitude";
    private String mColumnNameForWebsite = "website";
    private String mColumnNameForPhotos = "photos";
    private String mColumnNameForUpdateTime = "updatetime";
    private String mColumnNameForUploadStatus = "uploadstatus";


    private String[] mColumnNameForALL =
            {mColumnNameForPlaceID, mColumnNameForName, mColumnNameForAddress,
                    mColumnNameForPhone, mColumnNameForNotes,
                    mColumnNameForScore, mColumnNameForType,
                    mColumnNameForVisited, mColumnNameForLatitude, mColumnNameForLongitude, mColumnNameForWebsite, mColumnNameForPhotos,mColumnNameForUploadStatus,mColumnNameForUpdateTime};


    public String getTableName() {
        return mTableName;
    }

    public String[] getTableALLColumnName() {
        return mColumnNameForALL;
    }

    public String getColumnNameForPlaceID() {
        return mColumnNameForPlaceID;
    }

    public String getColumnNameForName() {
        return mColumnNameForName;
    }

    public String getColumnNameForAddress() {
        return mColumnNameForAddress;
    }

    public String getColumnNameForPhone() {
        return mColumnNameForPhone;
    }

    public String getColumnNameForNotes() {
        return mColumnNameForNotes;
    }

    public String getColumnNameForScore() {
        return mColumnNameForScore;
    }

    public String getColumnNameForType() {
        return mColumnNameForType;
    }

    public String getColumnNameForVisited() {
        return mColumnNameForVisited;
    }

    public String getColumnNameForLatitude() {
        return mColumnNameForLatitude;
    }

    public String getColumnNameForLongitude() {
        return mColumnNameForLongitude;
    }
    public String getColumnNameForWebsite() {
        return mColumnNameForWebsite;
    }

    public String getColumnNameForPhotos() {
        return mColumnNameForPhotos;
    }
    public String getColumnNameForUploadStatus() { return mColumnNameForUploadStatus;}

    public String getColumnNameForUpdateTime() { return mColumnNameForUpdateTime;}

}
