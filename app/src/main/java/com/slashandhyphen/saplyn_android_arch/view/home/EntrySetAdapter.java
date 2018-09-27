package com.slashandhyphen.saplyn_android_arch.view.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mike on 12/31/2017.
 *
 * Forensics (9/23/2018): This is where the content from an entry set object is selected to be
 * presented to the view. Currently, this is just presenting the name of the set as seen
 * in onBindViewHolder.
 */

public class EntrySetAdapter extends RecyclerView.Adapter<EntrySetAdapter.EntrySetViewHolder> {

    private List<EntrySet> entrySetList;
    private View.OnClickListener listener;

    public EntrySetAdapter(List<EntrySet> entrySetList, View.OnClickListener listener) {
        this.entrySetList = entrySetList;
        this.listener = listener;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                Toast.makeText(
                            itemView.getContext(),
                        "got a click: " + holder.entrySetName.getText(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return entrySetList.size();
    }

    public class EntrySetViewHolder extends RecyclerView.ViewHolder {
        public TextView entrySetName;
        public View itemView;

        public EntrySetViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            entrySetName = itemView.findViewById(R.id.text_entry_set_name);
        }
    }

}
