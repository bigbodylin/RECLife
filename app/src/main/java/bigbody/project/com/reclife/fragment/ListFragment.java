package bigbody.project.com.reclife.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import bigbody.project.com.reclife.R;
import bigbody.project.com.reclife.RECLifeMainActivity;
import bigbody.project.com.reclife.adapter.CustomListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private View mView;
    private ListView mListView;
    private CustomListViewAdapter mCustomListViewAdapter;

    private static String TAG = "ListFragment";

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_list, container, false);
        mListView = (ListView) mView.findViewById(R.id.list);

        mCustomListViewAdapter = new CustomListViewAdapter(getContext(),((RECLifeMainActivity)getActivity()).getArrayListForStoreItem());
        mListView.setAdapter(mCustomListViewAdapter);
        mListView.setOnItemClickListener(mListViewOnItemClickListener);
        return mView;

    }

    private AdapterView.OnItemClickListener mListViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG,"position = "+position);


        }
    };






}