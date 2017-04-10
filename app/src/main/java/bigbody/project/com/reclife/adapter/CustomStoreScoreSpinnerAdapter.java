package bigbody.project.com.reclife.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bigbody.project.com.reclife.R;

/**
 * Created by shuttlemacmini on 2017/2/22.
 */

public class CustomStoreScoreSpinnerAdapter extends ArrayAdapter{

    private Context mContext;
    private String TAG = "CustomStoreScoreSpinnerAdapter";


    public CustomStoreScoreSpinnerAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return mContext.getResources().getStringArray(R.array.score_spinner_wording).length;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.custom_score_spinner_layout, parent, false);

        String[] mStoreScoreArray = mContext.getResources().getStringArray(R.array.score_spinner_wording);

        TextView mScoreWording =(TextView)convertView.findViewById(R.id.view_for_score_spinner_wording);
        ImageView mScoreView = (ImageView)convertView.findViewById(R.id.view_for_score_spinner);

        mScoreWording.setText(mStoreScoreArray[position]);

        switch (position){
            case 0:
                mScoreView.setVisibility(View.INVISIBLE);
                break;

            case 1:
                mScoreView.setImageResource(R.mipmap.non_visited);
                break;

            case 2:
                mScoreView.setBackgroundColor(Color.RED);
                break;

            case 3:
                mScoreView.setBackgroundColor(Color.parseColor("#FFBB00"));
                break;

            case 4:
                mScoreView.setBackgroundColor(Color.YELLOW);
                break;

            case 5:
                mScoreView.setBackgroundColor(Color.GREEN);
                break;

            case 6:
                mScoreView.setBackgroundColor(Color.BLUE);
                break;


        }
        return convertView;
    }

 }
