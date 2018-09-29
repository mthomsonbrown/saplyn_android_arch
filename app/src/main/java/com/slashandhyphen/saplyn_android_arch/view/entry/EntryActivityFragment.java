package com.slashandhyphen.saplyn_android_arch.view.entry;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.Database;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntryActivityFragment extends Fragment implements View.OnClickListener {
    RecyclerView recycler;
    EntryAdapter adapter;
    List<Click> clickList;
    ClickViewModel clickViewModel;
    int entrySetId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        entrySetId = getEntrySetId();
        clickViewModel = getClickViewModel();

        // Grab Handles: View Elements
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.fragment_entry, container, false);
        recycler = layout.findViewById(R.id.recycler_view_entries);
        clickList = new ArrayList<>();
        Button clickButton = layout.findViewById(R.id.button_click);
        clickButton.setOnClickListener(this);
        TextView textClicksDaily = layout.findViewById(R.id.text_daily_clicks_number);
        TextView textClicksYesterday = layout.findViewById(R.id.text_yesterday_clicks_number);
        TextView textClicksTotal = layout.findViewById(R.id.text_total_clicks_number);

        // Prepare RecyclerView
        adapter = new EntryAdapter(clickList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        // Populate views
        clickViewModel.getClicks(entrySetId).observe(this, clicks -> {

            // TODO: Use more efficient method
            clickList.clear();
            clickList.addAll(clicks);
            adapter.notifyDataSetChanged();
        });

        clickViewModel.getStringClicksForDay(entrySetId, 0).observe(this, textClicksDaily::setText);
        clickViewModel.getStringClicksForDay(entrySetId, 1).observe(this, textClicksYesterday::setText);
        clickViewModel.getStringClicksTotal(entrySetId).observe(this, textClicksTotal::setText);

        Toast.makeText(
                getContext(),
                "got a click: " + entrySetId,
                Toast.LENGTH_SHORT
        ).show();
        // Return
        return layout;
    }

    private int getEntrySetId() {
        Intent myIntent = getActivity().getIntent();
        return myIntent.getIntExtra("entrySetId", -1);
    }

    private ClickViewModel getClickViewModel() {
        Database database = Database.getInstance(this.getActivity());
        ClickRepository clickRepository = new ClickRepository(database);
        ClickViewModel.Factory factory = new ClickViewModel.Factory(clickRepository);
        return ViewModelProviders.of(this, factory).get(ClickViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_click) {
            clickViewModel.click(entrySetId);
        }
    }
}
