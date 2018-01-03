package com.slashandhyphen.saplyn_android_arch.view.entry;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mike on 12/31/2017.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private List<Click> clickList;

    public EntryAdapter(List<Click> clickList) {
        this.clickList = clickList;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_entries, parent, false);

        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        Click click = clickList.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(click.time);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        holder.clickTime.setText(format.format(calendar.getTime()));
    }

    @Override
    public int getItemCount() {
        return clickList.size();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        public TextView clickTime;

        public EntryViewHolder(View itemView) {
            super(itemView);
            clickTime = itemView.findViewById(R.id.text_click_time);
        }
    }

}
