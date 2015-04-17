package com.prettymuchabigdeal.chambanabuses;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.prettymuchabigdeal.chambanabuses.adapter.DepartureCardAdapter;
import com.prettymuchabigdeal.chambanabuses.model.Departure;
import com.prettymuchabigdeal.chambanabuses.mtd.MTDService;
import com.prettymuchabigdeal.chambanabuses.mtd.response.DepartureResponse;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DeparturesActivity extends ActionBarActivity
        implements SwipeRefreshLayout.OnRefreshListener, Callback<DepartureResponse> {

    private static final String STATE_STOP = "stateStop";
    public static final String ARG_STOP = "argStop";

    @InjectView(R.id.card_swipe_container)
    SwipeRefreshLayout vSwipeRefreshLayout;

    @InjectView(R.id.card_list)
    RecyclerView vDepartureCardList;

    private DepartureCardAdapter mCardAdapter;

    private String mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_card_layout);
        ButterKnife.inject(this);

        if(savedInstanceState == null){
            mStop = getIntent().getStringExtra(ARG_STOP);
        } else {
            mStop = savedInstanceState.getString(STATE_STOP);
        }

        setTitle(mStop);

        vDepartureCardList.setHasFixedSize(true);
        vDepartureCardList.setLayoutManager(new LinearLayoutManager(this));

        mCardAdapter = new DepartureCardAdapter(this);

        vSwipeRefreshLayout.setOnRefreshListener(this);
        vSwipeRefreshLayout.setColorSchemeResources(R.color.bold_orange, R.color.bold_blue);
        vDepartureCardList.setAdapter(mCardAdapter);

        vSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(mCardAdapter.getItemCount() == 0)
                    vSwipeRefreshLayout.setRefreshing(true);
            }
        });

        onRefresh();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_STOP, mStop);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_departures, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        MTDService.Helper.create().getDeparturesByStop(mStop, null, 60, this);
    }

    @Override
    public void success(DepartureResponse departureResponse, Response response) {
        Departure[] departures = departureResponse.departures;
        if(departures.length == 0) {
            departures = new Departure[]{Departure.NONE};
        }
        mCardAdapter.setDepartures(Arrays.asList(departures));
        vSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failure(RetrofitError error) {
        vSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, error.getResponse().getReason(), Toast.LENGTH_LONG).show();
    }

}
