package com.prettymuchabigdeal.chambanabuses.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orm.StringUtil;
import com.prettymuchabigdeal.chambanabuses.R;
import com.prettymuchabigdeal.chambanabuses.StopsFragment;
import com.prettymuchabigdeal.chambanabuses.model.Stop;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tyler on 2/3/15.
 */
public class StopCardAdapter extends RecyclerView.Adapter<StopCardAdapter.StopCardViewHolder>
        implements View.OnClickListener {

    private List<Stop> mStops = Collections.emptyList();
    private StopsFragment.OnStopSelectedListener mListener;
    private RecyclerView mRecyclerView;

    public StopCardAdapter(StopsFragment.OnStopSelectedListener listener,
                           RecyclerView view) {
        mListener = listener;
        mRecyclerView = view;
    }

    public void setStops(List<Stop> stops) {
        mStops = stops;
        notifyDataSetChanged();
    }

    @Override
    public StopCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop_card, parent, false);

        view.setOnClickListener(this);
        return new StopCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopCardViewHolder holder, int position) {
        Stop s = mStops.get(position);

        holder.vStopName.setText(s.getStopName());
        holder.vStopCode.setText(s.getCode());

    }

    @Override
    public int getItemCount() {
        return mStops.size();
    }

    @Override
    public void onClick(View v) {
        int position = mRecyclerView.getChildPosition(v);
        Stop stop = mStops.get(position);
        Long id;
        if((id = stop.getId()) != null){
            mListener.onStopSelected(id);
            return;
        }

        stop = Stop.find(Stop.class, StringUtil.toSQLName("code") + " = ?", stop.getCode()).get(0);
        mListener.onStopSelected(stop.getId());

    }

    static class StopCardViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.stop_name)
        TextView vStopName;
        @InjectView(R.id.stop_code)
        TextView vStopCode;

        public StopCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
