package bigbody.project.com.reclife.fragment;


import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import bigbody.project.com.reclife.R;
import bigbody.project.com.reclife.RECLifeMainActivity;
import bigbody.project.com.reclife.adapter.CustomStoreScoreSpinnerAdapter;
import bigbody.project.com.reclife.adapter.PlaceAutoCompleteAdapter;
import bigbody.project.com.reclife.objectitem.StoreItem;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private View mView;
    private GoogleMap mMap;
    private MapView mMapView;
    private String TAG = "MapFragment";
    private GoogleApiClient mGoogleApiClient;

    private RelativeLayout mAddNewPlaceBtn;
    private PlaceAutoCompleteAdapter mAdapter;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Dialog mSearchPlaceDialog, mAddPlaceDialog, mNoteEditDialog;
    private Place place = null;
    private Spinner mStoreScoreSpinner;

    private EditText mNoteEdittext;
    private TextView mNoteTextview, mShowWordingCountTextView;

    private CustomStoreScoreSpinnerAdapter mCustomStoreScoreSpinnerAdapter;

//    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
//            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(22.000000, 120.000000), new LatLng(25.000000, 122.000000)); //southwest and northeast Taiwan

    private StoreItem mStoreSimpleItem;
    private ArrayList<Marker> mMarkerArrayList = new ArrayList<Marker>();
    private Marker mMarker;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView()");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        // init Google api service.
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // map fragment layout initial
        initLayout();

        // initial mapview data.
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        //set button click event
        mAddNewPlaceBtn.setOnClickListener(addPlaceClickListener);
        //mStoreSpinner.setOnI
        mStoreScoreSpinner.setOnItemSelectedListener(mScoreStoreItemClickListener);

        return mView;
    }

    private void initLayout() {
        Log.e(TAG, "initLayout()");
        mMapView = (MapView) mView.findViewById(R.id.map);
        mAddNewPlaceBtn = (RelativeLayout) mView.findViewById(R.id.linearlayout_btn_add);

        mStoreScoreSpinner = (Spinner) mView.findViewById(R.id.spinner_for_store_score);
        mCustomStoreScoreSpinnerAdapter = new CustomStoreScoreSpinnerAdapter(getActivity(), R.layout.custom_score_spinner_layout);
        mStoreScoreSpinner.setAdapter(mCustomStoreScoreSpinnerAdapter);
    }

    private View.OnClickListener addPlaceClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSearchPlaceDialog();
        }
    };

    private AdapterView.OnItemSelectedListener mScoreStoreItemClickListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.e(TAG, "position = " + position);
            switch (position) {

                case 0:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (!marker.isVisible())
                                marker.setVisible(true);
                        }
                    }
                    break;

                case 1:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() == 0.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;

                case 2:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() >= 1.0 && ((StoreItem) marker.getTag()).getStoreScore() < 2.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;

                case 3:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() >= 2.0 && ((StoreItem) marker.getTag()).getStoreScore() < 3.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;

                case 4:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() >= 3.0 && ((StoreItem) marker.getTag()).getStoreScore() < 4.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;

                case 5:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() >= 4.0 && ((StoreItem) marker.getTag()).getStoreScore() < 5.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;

                case 6:
                    if (mMap != null && mMarkerArrayList.size() > 0) {
                        for (Marker marker : mMarkerArrayList) {
                            Log.e(TAG, "marker = " + marker);
                            if (((StoreItem) marker.getTag()).getStoreScore() == 5.0) {
                                marker.setVisible(true);
                            } else {
                                marker.setVisible(false);
                            }
                        }
                    }
                    break;


            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void showSearchPlaceDialog() {
        mSearchPlaceDialog = new Dialog(getActivity(), R.style.MyCustomDialog);
        mSearchPlaceDialog
                .setContentView(R.layout.dialog_for_search_place);

        Window dialogWindow = mSearchPlaceDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        AutoCompleteTextView mAutoCompleteTextView =
                (AutoCompleteTextView) mSearchPlaceDialog.findViewById(
                        R.id.search_place_autocomplete_input);

        mAdapter = new PlaceAutoCompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        mAutoCompleteTextView.setAdapter(mAdapter);
        mAutoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);


        RelativeLayout mSearchDialogCancelBtn = (RelativeLayout) mSearchPlaceDialog.findViewById(R.id.search_dialog_for_cancel_button);

        mSearchDialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchPlaceDialog != null && mSearchPlaceDialog.isShowing()) {
                    mSearchPlaceDialog.dismiss();
                }
            }
        });

        RelativeLayout mSearchDialogConfirmBtn = (RelativeLayout) mSearchPlaceDialog.findViewById(R.id.search_dialog_for_confirm_button);
        mSearchDialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show new dialog
                if (mSearchPlaceDialog != null && mSearchPlaceDialog.isShowing()) {
                    mSearchPlaceDialog.dismiss();
                }

                if (mStoreSimpleItem != null && mStoreSimpleItem.getPlaceID() != null) {
                    showAddPlaceDialog();
                }

            }
        });


        mSearchPlaceDialog.setCanceledOnTouchOutside(false);
        mSearchPlaceDialog.show();
    }


    private void showAddPlaceDialog() {
        mAddPlaceDialog = new Dialog(getActivity(), R.style.MyCustomDialog);
        mAddPlaceDialog
                .setContentView(R.layout.dialog_for_add_new_place);

        Window dialogWindow = mAddPlaceDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        //mStoreSimpleItem = new StoreItem();

        // layout init
        TextView mStoreTitle = (TextView) mAddPlaceDialog.findViewById(R.id.store_name_title_txt);
        TextView mStoreAddress = (TextView) mAddPlaceDialog.findViewById(R.id.store_address_title_txt);
        final TextView mStoreScore = (TextView) mAddPlaceDialog.findViewById(R.id.store_score_title_txt);
        RadioGroup mStoreVisitedGroup = (RadioGroup) mAddPlaceDialog.findViewById(R.id.radiogroup_store_visited);
        RadioButton mStoreVisitedNO = (RadioButton) mAddPlaceDialog.findViewById(R.id.store_visited_no);
        RadioButton mStoreVisitedYES = (RadioButton) mAddPlaceDialog.findViewById(R.id.store_visited_yes);
        RelativeLayout mAddPlaceDialogCancelBtn = (RelativeLayout) mAddPlaceDialog.findViewById(R.id.add_store_dialog_for_cancel_button);
        RelativeLayout mAddPlaceDialogConfirmBtn = (RelativeLayout) mAddPlaceDialog.findViewById(R.id.add_store_dialog_for_confirm_button);
        final RelativeLayout mScoreLayout = (RelativeLayout) mAddPlaceDialog.findViewById(R.id.relativelayout_for_store_score);
        SeekBar mStoreScoreSeekBar = (SeekBar) mAddPlaceDialog.findViewById(R.id.seekbar_for_store_score);
        mNoteTextview = (TextView) mAddPlaceDialog.findViewById(R.id.txt_for_edit_notes);
        ImageView mEditBtn = (ImageView) mAddPlaceDialog.findViewById(R.id.icon_store_notes);

        mStoreTitle.setText(mStoreSimpleItem.getStoreName());
        mStoreAddress.setText(mStoreSimpleItem.getStoreAddress());

        if (mStoreVisitedNO.isChecked()) {
            mStoreSimpleItem.setStoreVisited(0);
            mScoreLayout.setVisibility(View.GONE);
            mStoreSimpleItem.setStoreScore(0.0f);
        } else if (mStoreVisitedYES.isChecked()) {
            mStoreSimpleItem.setStoreVisited(1);
            mStoreSimpleItem.setStoreScore(1.0f);
        }

        mStoreVisitedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int visitedstatus = group.indexOfChild((RadioButton) mAddPlaceDialog.findViewById(checkedId));

                switch (checkedId) {
                    case R.id.store_visited_no:
                        Log.e(TAG, "visited status = " + visitedstatus);
                        mStoreSimpleItem.setStoreVisited(visitedstatus);
                        mStoreSimpleItem.setStoreScore(0.0f);
                        mScoreLayout.setVisibility(View.GONE);
                        break;

                    case R.id.store_visited_yes:
                        Log.e(TAG, "visited status = " + visitedstatus);
                        mStoreSimpleItem.setStoreVisited(visitedstatus);
                        mScoreLayout.setVisibility(View.VISIBLE);
                        mStoreSimpleItem.setStoreScore(1.0f);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mStoreScore.setText(String.format(getActivity().getResources().getString(R.string.store_score_text), mStoreSimpleItem.getStoreScore()));
                            }
                        });
                        break;


                }
            }
        });

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });


        mStoreScoreSeekBar.setMax(8);
        mStoreScoreSeekBar.setProgress(0);


        mStoreScoreSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStoreSimpleItem.setStoreScore(progress * 0.5f + 1.0f);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStoreScore.setText(String.format(getActivity().getResources().getString(R.string.store_score_text), mStoreSimpleItem.getStoreScore()));
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mAddPlaceDialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddPlaceDialog != null && mAddPlaceDialog.isShowing()) {
                    mAddPlaceDialog.dismiss();
                }
            }
        });


        mAddPlaceDialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // write to database.
                    ((RECLifeMainActivity) getActivity()).mRECLifeSQLiteDBFunc.insertStoreSimpleData(mStoreSimpleItem.getPlaceID(),
                            mStoreSimpleItem.getStoreName(), mStoreSimpleItem.getStoreAddress(), mStoreSimpleItem.getStoreLat(), mStoreSimpleItem.getStoreLong(),
                            mStoreSimpleItem.getStoreVisited(), mStoreSimpleItem.getStoreUploadStatus(), mStoreSimpleItem.getStoreScore(), mStoreSimpleItem.getStoreNotes(), mStoreSimpleItem.getUpdateTime());

                    // create new place lan/long.
                    LatLng newAddStorelocation = new LatLng(mStoreSimpleItem.getStoreLat(), mStoreSimpleItem.getStoreLong());

                    // create new marker
                    MarkerOptions mMarkerOptions = new MarkerOptions();
                    mMarkerOptions.title(mStoreSimpleItem.getStoreName());
                    mMarkerOptions.snippet(mStoreSimpleItem.getStoreAddress());
                    mMarkerOptions.position(newAddStorelocation);

                    if (mStoreSimpleItem.getStoreScore() >= 0.0 && mStoreSimpleItem.getStoreScore() < 1.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.non_visited));
                    } else if (mStoreSimpleItem.getStoreScore() >= 1.0 && mStoreSimpleItem.getStoreScore() < 2.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    } else if (mStoreSimpleItem.getStoreScore() >= 2.0 && mStoreSimpleItem.getStoreScore() < 3.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    } else if (mStoreSimpleItem.getStoreScore() >= 3.0 && mStoreSimpleItem.getStoreScore() < 4.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    } else if (mStoreSimpleItem.getStoreScore() >= 4.0 && mStoreSimpleItem.getStoreScore() < 5.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    } else if (mStoreSimpleItem.getStoreScore() == 5.0) {
                        mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    }

                    mMarker = mMap.addMarker(mMarkerOptions);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newAddStorelocation, 13));

                    mMarker.setTag(mStoreSimpleItem);
                    mMarkerArrayList.add(mMarker);

                    // add arraylist for place
                    ((RECLifeMainActivity) getActivity()).addArrayListForStoreItem(mStoreSimpleItem);


                    if (mAddPlaceDialog != null && mAddPlaceDialog.isShowing()) {
                        mStoreSimpleItem = null;
                        mAddPlaceDialog.dismiss();

                    }

                } catch (Exception e) {
                    Log.e(TAG, "insert error");

                    e.printStackTrace();
                }
            }
        });


        mAddPlaceDialog.setCanceledOnTouchOutside(false);
        mAddPlaceDialog.show();
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            final CharSequence primaryText = item.getPrimaryText(null);
            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

        }
    };


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            place = places.get(0);

            mStoreSimpleItem = new StoreItem();
            if (places != null) {
                mStoreSimpleItem.setPlaceID(place.getId());
                mStoreSimpleItem.setStoreName(place.getName().toString());
                mStoreSimpleItem.setStoreAddress(place.getAddress().toString());
                mStoreSimpleItem.setStoreLat(place.getLatLng().latitude);
                mStoreSimpleItem.setStoreLong(place.getLatLng().longitude);
            }

            places.release();
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        Log.e(TAG, "onResume()");
        Log.e(TAG, "Build version = " + Build.VERSION.SDK_INT);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        10000);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        Log.e(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        mMapView.onStop();
        super.onStop();
        Log.e(TAG, "onStop()");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();

        Log.e(TAG, "onStop()");

    }


    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady");
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }

        // init DB data marker.
        if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem() != null && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().size() > 0) {
            Log.e(TAG, "mStoreItemArrayList.size() = " + ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().size());

            for (int i = 0; i < ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().size(); i++) {

                LatLng newAddStorelocation = new LatLng(((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreLat(), ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreLong());

                MarkerOptions mMarkerOptions = new MarkerOptions();
                mMarkerOptions.title(((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreName());
                mMarkerOptions.snippet(((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreAddress());
                mMarkerOptions.position(newAddStorelocation);

                if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() >= 0.0 && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() < 1.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.non_visited));
                } else if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() >= 1.0 && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() < 2.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                } else if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() >= 2.0 && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() < 3.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                } else if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() >= 3.0 && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() < 4.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                } else if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() >= 4.0 && ((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() < 5.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i).getStoreScore() == 5.0) {
                    mMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                }

                mMarker = mMap.addMarker(mMarkerOptions);
                mMarker.setTag(((RECLifeMainActivity) getActivity()).getArrayListForStoreItem().get(i));

                mMarkerArrayList.add(mMarker);


            }

        }


        // Add a marker in Sydney and move the camera
        LatLng here = new LatLng(25.0713188, 121.581059);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 13));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker arg0) {

                Log.e(TAG, "getID = " + arg0.getId());
                Log.e(TAG, "getTitle = " + arg0.getTitle());
                Log.e(TAG, "marker getPlace ID = " + ((StoreItem) arg0.getTag()).getPlaceID());
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker arg0) {

                Log.e(TAG, "marker = " + arg0);
                Log.e(TAG, "marker ID = " + arg0.getId());
                Log.e(TAG, "marker getPlace ID = " + ((StoreItem) arg0.getTag()).getPlaceID());

            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.e(TAG, "onConnected()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed()");
    }


    private void showEditDialog() {
        mNoteEditDialog = new Dialog(getActivity(), R.style.MyCustomDialog);
        mNoteEditDialog
                .setContentView(R.layout.dialog_for_edit_note);


        Window dialogWindow = mNoteEditDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        RelativeLayout btn_ok = (RelativeLayout) mNoteEditDialog
                .findViewById(R.id.edit_note_for_ok_button);

        mNoteEdittext = (EditText) mNoteEditDialog
                .findViewById(R.id.edittext_for_care_note);

        mShowWordingCountTextView = (TextView) mNoteEditDialog
                .findViewById(R.id.txt_for_word_count_title);

        mNoteEdittext
                .setText(mNoteTextview.getText().toString());

        if (mNoteEdittext.getText().length() >= 0) {
            String mWordCounts = String.format(
                    getResources().getString(
                            R.string.edit_note_word_count),
                    String.valueOf(mNoteEdittext.getText().length()));

            mShowWordingCountTextView.setText(mWordCounts);
        }

        mNoteEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                Log.e(TAG, "count = " + count);
                String mWordCounts = String.format(
                        getResources().getString(
                                R.string.edit_note_word_count),
                        String.valueOf(s.length()));
                mShowWordingCountTextView.setText(mWordCounts);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteEditDialog.dismiss();
                mNoteTextview.setText(
                        mNoteEdittext.getText().toString());

            }
        });

        RelativeLayout btn_cancel = (RelativeLayout) mNoteEditDialog
                .findViewById(R.id.edit_note_for_cancel_button);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNoteEditDialog != null
                        && mNoteEditDialog.isShowing()) {
                    mNoteEditDialog.dismiss();
                }
            }
        });
        mNoteEditDialog.setCanceledOnTouchOutside(false);
        mNoteEditDialog.show();
    }
}
