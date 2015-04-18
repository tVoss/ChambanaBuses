package com.prettymuchabigdeal.chambanabuses;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.prettymuchabigdeal.chambanabuses.adapter.StopCardAdapter;
import com.prettymuchabigdeal.chambanabuses.model.Stop;
import com.prettymuchabigdeal.chambanabuses.mtd.MTDService;
import com.prettymuchabigdeal.chambanabuses.mtd.response.StopResponse;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStopSelectedListener} interface
 * to handle interaction events.
 * Use the {@link StopsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopsFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, Callback<StopResponse>,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String ARG_MODE = "mode";

    public enum Mode {
        ALL, NEARBY, FAVORITE,
    }


    @InjectView(R.id.card_swipe_container)
    SwipeRefreshLayout vSwipeRefreshLayout;
    @InjectView(R.id.card_list)
    RecyclerView vStopCardList;

    private Mode mMode;
    private MTDService mMTDService;
    private GoogleApiClient mClient;
    private StopCardAdapter mCardAdapter;
    private OnStopSelectedListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mode The stop display mode.
     * @return A new instance of fragment StopsFragment.
     */
    public static StopsFragment newInstance(Mode mode) {
        StopsFragment fragment = new StopsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODE, mode.ordinal());
        fragment.setArguments(args);

        return fragment;
    }

    public StopsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = Mode.values()[getArguments().getInt(ARG_MODE)];
        }

        mMTDService = MTDService.Helper.create();
        mClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_card_layout, container, false);
        ButterKnife.inject(this, view);

        vStopCardList.setHasFixedSize(true);
        vStopCardList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCardAdapter = new StopCardAdapter(mListener, vStopCardList);

        vSwipeRefreshLayout.setOnRefreshListener(this);
        vSwipeRefreshLayout.setColorSchemeResources(R.color.bold_orange, R.color.bold_blue);
        vStopCardList.setAdapter(mCardAdapter);

        vSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(mCardAdapter.getItemCount() == 0)
                    vSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStopSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

        List<Stop> stops = null;

        switch (mMode) {
            case ALL:
                stops = Stop.listAll(Stop.class);
                if(stops.isEmpty()){
                    mMTDService.getStops(this);
                    return;
                }
                break;
            case FAVORITE:
                //TODO
                break;
            case NEARBY:
                mClient.connect();
                return;
        }

        mCardAdapter.setStops(stops);
        vSwipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void success(StopResponse stopResponse, Response response) {
        if(mMode == Mode.ALL){
            for(Stop s : stopResponse.stops)
                s.save();
        }

        mCardAdapter.setStops(Arrays.asList(stopResponse.stops));
        vSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failure(RetrofitError error) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(mClient);
        if(loc != null) {
            mMTDService.getStopsByLatLon((float)loc.getLatitude(), (float)loc.getLongitude(),
                    10, this);
        } else {
            vSwipeRefreshLayout.setRefreshing(false);
        }

        mClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        vSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStopSelectedListener {
        void onStopSelected(String stopId);
    }

}
