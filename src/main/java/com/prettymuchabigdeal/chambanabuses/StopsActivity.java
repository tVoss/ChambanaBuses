package com.prettymuchabigdeal.chambanabuses;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StopsActivity extends ActionBarActivity
        implements StopsFragment.OnStopSelectedListener {

    @InjectView(R.id.stops_tabs)
    PagerSlidingTabStrip vStopsTabStrip;

    @InjectView(R.id.stops_pager)
    ViewPager vStopsPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        ButterKnife.inject(this);

        vStopsPager.setAdapter(new StopFragmentPagerAdapter());
        vStopsTabStrip.setViewPager(vStopsPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stops, menu);

        /*
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.stops_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        */

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
    public void onStopSelected(String stopId) {
        Intent intent = new Intent(this, DeparturesActivity.class);
        intent.putExtra(DeparturesActivity.ARG_STOP, stopId);
        startActivity(intent);
    }

    private class StopFragmentPagerAdapter extends FragmentPagerAdapter {

        public StopFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return StopsFragment.newInstance(StopsFragment.Mode.values()[position]);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return StopsFragment.Mode.values()[position].toString();
        }
    }

}
