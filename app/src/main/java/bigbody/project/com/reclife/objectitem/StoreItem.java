package bigbody.project.com.reclife.objectitem;

import android.util.Log;

/**
 * Created by shuttlemacmini on 2017/2/16.
 */

public class StoreItem {

    private String mPlaceID;
    private String mStoreName;
    private String mStoreAddress;
    private double mStoreLat;
    private double mStoreLong;
    private int mStoreType;
    private int mStoreVisited;
    private int mUploadStatus;
    private float mScore;

    private String mStorePhone;
    private String mStoreNotes;
    private String mUpdateTime;



    private static String TAG = "StoreSimpleItem";

    public String getPlaceID() {
        return this.mPlaceID;
    }

    public void setPlaceID(String placeid) {
        Log.e(TAG, "StoreSimpleItem set place id = " + placeid);
        this.mPlaceID = placeid;
    }


    public String getStoreName() {
        return this.mStoreName;
    }

    public void setStoreName(String storename) {
        Log.e(TAG, "StoreSimpleItem set place name = " + storename);
        this.mStoreName = storename;
    }

    public String getStoreAddress() {
        return this.mStoreAddress;
    }

    public void setStoreAddress(String storeaddress) {
        Log.e(TAG, "StoreSimpleItem set place address = " + storeaddress);
        this.mStoreAddress = storeaddress;
    }

    public double getStoreLat() {
        return this.mStoreLat;
    }

    public void setStoreLat(double storelat) {
        Log.e(TAG, "StoreSimpleItem set place lat = " + storelat);
        this.mStoreLat = storelat;
    }

    public double getStoreLong() {
        return this.mStoreLong;
    }

    public void setStoreLong(double storelong) {
        Log.e(TAG, "StoreSimpleItem set place long = " + storelong);
        this.mStoreLong = storelong;
    }

    public int getStoreType() {
        return this.mStoreType;
    }

    public void setStoreType(int storetype) {
        Log.e(TAG, "StoreSimpleItem set place type = " + storetype);

        this.mStoreType = storetype;
    }

    public int getStoreVisited() {
        return this.mStoreVisited;
    }

    public void setStoreVisited(int storevisited) {
        Log.e(TAG, "StoreSimpleItem set place visited = " + storevisited);
        this.mStoreVisited = storevisited;
    }

    public int getStoreUploadStatus() { return this.mUploadStatus;}

    public void setStoreUploadStatus(int uploadstatus) {
        Log.e(TAG, "StoreSimpleItem set place uploadstatus = " + uploadstatus);
        this.mUploadStatus = uploadstatus;
    }

    public float getStoreScore() {
        return this.mScore;
    }

    public void setStoreScore(float storescore) {
        Log.e(TAG, "StoreSimpleItem set place score = " + storescore);
        this.mScore = storescore;
    }


    public String getStorePhone() {
        return this.mStorePhone;
    }

    public void setStorePhone(String storephone) {
        Log.e(TAG, "StoreSimpleItem set place phone = " + storephone);
        this.mStorePhone = storephone;
    }

    public String getStoreNotes() {
        return this.mStoreNotes;
    }

    public void setStoreNotes(String storenote) {
        Log.e(TAG, "StoreSimpleItem set place note = " + storenote);
        this.mStoreNotes = storenote;
    }


    public String getUpdateTime() {
        return this.mUpdateTime;
    }

    public void setUpdateTime(String updatetime) {
        Log.e(TAG, "StoreSimpleItem set place update time = " + mUpdateTime);
        this.mUpdateTime = mUpdateTime;
    }




}
