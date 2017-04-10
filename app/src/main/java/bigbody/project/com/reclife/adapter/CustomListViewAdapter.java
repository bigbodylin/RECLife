package bigbody.project.com.reclife.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bigbody.project.com.reclife.R;
import bigbody.project.com.reclife.objectitem.StoreItem;

/**
 * Created by shuttlemacmini on 2017/2/24.
 */

public class CustomListViewAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<StoreItem> mStoreItemList;

    public CustomListViewAdapter(Context context, ArrayList<StoreItem> storeitemlist) {
        this.mContext = context;
        this.mStoreItemList = storeitemlist;
    }

    @Override
    public int getCount() {
        return mStoreItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStoreItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_layout, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.mTxtStoreName = (TextView) convertView.findViewById(R.id.listview_txt_for_store_name);
            mViewHolder.mTxtStoreAddress = (TextView) convertView.findViewById(R.id.listview_txt_for_store_address);
            mViewHolder.mTxtStoreScore = (TextView) convertView.findViewById(R.id.txt_for_store_score);
            mViewHolder.mImageViewStoreType = (ImageView) convertView.findViewById(R.id.imageview_for_type_icon);
            mViewHolder.mScoreBackground = (RelativeLayout) convertView.findViewById(R.id.relativelayout_for_score_info);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mTxtStoreName.setText(mStoreItemList.get(position).getStoreName());
        mViewHolder.mTxtStoreAddress.setText(mStoreItemList.get(position).getStoreAddress());

        mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        if (mStoreItemList.get(position).getStoreScore() >= 0.0 && mStoreItemList.get(position).getStoreScore() < 1.0) {
            mViewHolder.mScoreBackground.setBackgroundResource(R.mipmap.non_visited);
            mViewHolder.mTxtStoreScore.setVisibility(View.GONE);
        } else if (mStoreItemList.get(position).getStoreScore() >= 1.0 && mStoreItemList.get(position).getStoreScore() < 2.0) {
            mViewHolder.mScoreBackground.setBackgroundColor(Color.RED);
            mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        } else if (mStoreItemList.get(position).getStoreScore() >= 2.0 && mStoreItemList.get(position).getStoreScore() < 3.0) {
            mViewHolder.mScoreBackground.setBackgroundColor(Color.parseColor("#FFAA33"));
            mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        } else if (mStoreItemList.get(position).getStoreScore() >= 3.0 && mStoreItemList.get(position).getStoreScore() < 4.0) {
            mViewHolder.mScoreBackground.setBackgroundColor(Color.YELLOW);
            mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        } else if (mStoreItemList.get(position).getStoreScore() >= 4.0 && mStoreItemList.get(position).getStoreScore() < 5.0) {
            mViewHolder.mScoreBackground.setBackgroundColor(Color.GREEN);
            mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        } else if (mStoreItemList.get(position).getStoreScore() == 5.0) {
            mViewHolder.mScoreBackground.setBackgroundColor(Color.BLUE);
            mViewHolder.mTxtStoreScore.setText(String.valueOf(mStoreItemList.get(position).getStoreScore()));
        }





        return convertView;
    }

    private static class ViewHolder {
        TextView mTxtStoreName;
        TextView mTxtStoreAddress;
        TextView mTxtStoreScore;
        ImageView mImageViewStoreType;
        RelativeLayout mScoreBackground;
    }


}
