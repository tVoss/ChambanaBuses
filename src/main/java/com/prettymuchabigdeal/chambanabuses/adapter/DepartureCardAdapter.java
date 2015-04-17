package com.prettymuchabigdeal.chambanabuses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prettymuchabigdeal.chambanabuses.R;
import com.prettymuchabigdeal.chambanabuses.model.Departure;
import com.prettymuchabigdeal.chambanabuses.model.Route;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tyler on 2/1/15.
 */
public class DepartureCardAdapter
        extends RecyclerView.Adapter<DepartureCardAdapter.DepartureCardViewHolder> {

    private Context mContext;
    private List<Departure> mDepartures = Collections.EMPTY_LIST;


    public DepartureCardAdapter(Context context) {
        mContext = context;
    }

    public void setDepartures(List<Departure> departures) {
        mDepartures = departures;
        notifyDataSetChanged();
    }

    @Override
    public DepartureCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.departure_card, parent, false);
        return new DepartureCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DepartureCardViewHolder holder, int position) {
        Departure d = mDepartures.get(position);
        String[] headsign = d.getHeadsign().split(" ");

        String tripHeadsign = d.getTrip() == null ? null : d.getTrip().getTripHeadsign();

        holder.vNumber.setText(headsign[0]);

        if(tripHeadsign != null) {
            if (tripHeadsign.contains("-")) {
                headsign[1] += WordUtils.capitalizeFully(
                        tripHeadsign.substring(tripHeadsign.indexOf('-') - 1),
                        new char[]{'/', ' ', '-'});
            } else {
                headsign[1] += " - " + WordUtils.capitalizeFully(tripHeadsign, new char[]{'/', ' ', '-'});
            }
        }
        holder.vColor.setText(headsign[1]);

        int mins = d.getExpectedMins();
        if(mins != Integer.MIN_VALUE) {
            holder.vTime.setText(mContext.getResources().getQuantityString(R.plurals.minutes,
                    d.getExpectedMins(), d.getExpectedMins()));
        } else {
            holder.vTime.setText(null);
        }

        holder.vIstop.setVisibility(d.isIstop() ? View.VISIBLE : View.GONE);

        Route r = d.getRoute();

        try {
            int textColor = 0xff000000 | Integer.parseInt(r.getRouteTextColor(), 16);
            int backColor = 0xff000000 | Integer.parseInt(r.getRouteColor(), 16);

            holder.vNumber.setTextColor(textColor);
            holder.vNumber.setBackgroundColor(backColor);

            holder.vDivider.setBackgroundColor(backColor);
        } catch (Exception e) {
            //meh
        }

    }

    @Override
    public int getItemCount() {
        return mDepartures.size();
    }

    static class DepartureCardViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.departure_number)
        TextView vNumber;
        @InjectView(R.id.departure_color)
        TextView vColor;
        @InjectView(R.id.departure_time)
        TextView vTime;
        @InjectView(R.id.departure_divider)
        View vDivider;
        @InjectView(R.id.departure_istop)
        View vIstop;

        public DepartureCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
