package bigbody.project.com.reclife;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import bigbody.project.com.reclife.adapter.CustomFragmentPageAdapter;
import bigbody.project.com.reclife.database.RECLifeSQLiteDatabaseFunction;
import bigbody.project.com.reclife.database.datatable.StoreTable;
import bigbody.project.com.reclife.database.datatable.StoreTypeTable;
import bigbody.project.com.reclife.fragment.ListFragment;
import bigbody.project.com.reclife.fragment.MapFragment;
import bigbody.project.com.reclife.objectitem.StoreItem;

public class RECLifeMainActivity extends FragmentActivity {


    // Log TAG.
    private String TAG = "RECLifeMainActivity";


    // Data container.
    private StoreItem mStoreAllItem;
    private ArrayList<StoreItem> mStoreItemArrayList = new ArrayList<StoreItem>();
    private ArrayList<String> mStoreTypeArrayList = new ArrayList<String>();

    // Table column name.
    private StoreTypeTable mStoreTypeTable = new StoreTypeTable();
    private StoreTable mStoreTable = new StoreTable();

    // Frame container.
    private TabLayout mMainTabLayout;
    private ViewPager mMainViewPager;
    private String[] mTabTitle;
    private ArrayList<Fragment> mAllPageFragment = new ArrayList<Fragment>();
    private CustomFragmentPageAdapter mCustomFragmentPageAdapter;

    // Database name.
    private static String RECLIFE_DATABASE_NAME = "RECLifeDatabase";

    // DataBase function.
    public RECLifeSQLiteDatabaseFunction mRECLifeSQLiteDBFunc = new RECLifeSQLiteDatabaseFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclife_main);

        initLayout(); //initial layout parameter
        if (doesDatabaseExist(getApplicationContext(), RECLIFE_DATABASE_NAME)) {
            Log.e(TAG,"database is exist ");
            initDB();
            getDBData();
        }else{
            Log.e(TAG,"database is not exist ");
            initDB();
        }





        initMainViewPager();
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void initLayout() {
        Log.e(TAG,"initLayout()");
        mMainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mMainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
    }

    private void initDB() {
        Log.e(TAG, "initDB()");
        mRECLifeSQLiteDBFunc.createHomeCareDB(getApplicationContext(), RECLIFE_DATABASE_NAME, this);
    }

    private void getDBData() {
        Log.e(TAG, "getDBData()");
        try {
            Cursor mGetStoreData = mRECLifeSQLiteDBFunc.searchAllStoreData();

            if (mGetStoreData != null && mGetStoreData.getCount() > 0) {
                mGetStoreData.moveToFirst();

                Log.e(TAG, "mGetStoreData.getCount()" + mGetStoreData.getCount());
                for (int i = 0; i < mGetStoreData.getCount(); i++) {
                    mStoreAllItem = new StoreItem();
                    mStoreAllItem.setPlaceID(mGetStoreData.getString(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForPlaceID())));
                    mStoreAllItem.setStoreName(mGetStoreData.getString(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForName())));
                    mStoreAllItem.setStoreAddress(mGetStoreData.getString(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForAddress())));
                    mStoreAllItem.setStoreLat(mGetStoreData.getDouble(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForLatitude())));
                    mStoreAllItem.setStoreLong(mGetStoreData.getDouble(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForLongitude())));
                    mStoreAllItem.setStoreVisited(mGetStoreData.getInt(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForVisited())));
                    mStoreAllItem.setStoreScore(mGetStoreData.getFloat(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForScore())));
                    mStoreAllItem.setStoreType(mGetStoreData.getInt(mGetStoreData.getColumnIndex(mStoreTable.getColumnNameForType())));
                    mStoreItemArrayList.add(mStoreAllItem);
                    mGetStoreData.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMainViewPager() {
        Log.e(TAG, "initMainViewPager()");
        mTabTitle = getResources().getStringArray(R.array.tab_title);
        mMainTabLayout.setupWithViewPager(mMainViewPager);

        for (String title : mTabTitle) {
            mMainTabLayout.addTab(mMainTabLayout.newTab().setText(title));
        }

        mAllPageFragment.add(new MapFragment());
        mAllPageFragment.add(new ListFragment());

        mCustomFragmentPageAdapter = new CustomFragmentPageAdapter(getSupportFragmentManager(), mAllPageFragment, mTabTitle);
        mMainViewPager.setAdapter(mCustomFragmentPageAdapter);
        mMainViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mMainTabLayout));

    }

    private TabLayout.OnTabSelectedListener mTabTitleSelectecListner = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mMainViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    public ArrayList<StoreItem> getArrayListForStoreItem() {
        return mStoreItemArrayList;
    }
    public void addArrayListForStoreItem(StoreItem storeItem) { this.mStoreItemArrayList.add(storeItem); }
    public ArrayList<String> getArrayListForStoreType(){ return mStoreTypeArrayList;}

    public boolean isHasPlaceID(String placeid){
         for(int i=0;i<mStoreItemArrayList.size();i++){
             if(mStoreItemArrayList.get(i).getPlaceID().equals(placeid)){
                 return true;
             }
         }
        return false;
    }

    public int getPositionForStoreItem(String placeid){
        int position = -1;
        for(int i=0;i<mStoreItemArrayList.size();i++){
            if(mStoreItemArrayList.get(i).getPlaceID().equals(placeid)){
                position = i;
                return position;
            }
        }
        return position;
    }
}
