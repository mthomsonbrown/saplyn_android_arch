package com.slashandhyphen.saplyn_android_arch.view.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mike on 12/31/2017.
 */

public class EntrySetAdapter extends RecyclerView.Adapter<EntrySetAdapter.EntrySetViewHolder> {

    private List<EntrySet> entrySetList;

    public EntrySetAdapter(List<EntrySet> entrySetList) {
        this.entrySetList = entrySetList;
    }

    @Override
    public EntrySetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_entry_sets, parent, false);

        return new EntrySetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EntrySetViewHolder holder, int position) {
        EntrySet entrySet = entrySetList.get(position);

        holder.entrySetName.setText(entrySet.name);
    }

    @Override
    public int getItemCount() {
        return entrySetList.size();
    }

    public class EntrySetViewHolder extends RecyclerView.ViewHolder {
        public TextView entrySetName;

        public EntrySetViewHolder(View itemView) {
            super(itemView);
            entrySetName = itemView.findViewById(R.id.text_entry_set_name);
        }
    }

}
