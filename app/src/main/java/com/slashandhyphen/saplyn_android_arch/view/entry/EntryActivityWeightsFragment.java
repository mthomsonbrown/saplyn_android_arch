package com.slashandhyphen.saplyn_android_arch.view.entry;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.Database;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySetRepository;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;
import com.slashandhyphen.saplyn_android_arch.view_model.EntrySetViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntryActivityWeightsFragment extends Fragment implements View.OnClickListener {
    RecyclerView recycler;
    List<Click> clickList;
    ClickViewModel clickViewModel;
    EntrySetViewModel entrySetViewModel;
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
        entrySetViewModel = getEntrySetViewModel();

        // Grab Handles: View Elements
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.fragment_weights_entry, container, false);
        recycler = layout.findViewById(R.id.recycler_view_entries);
        clickList = new ArrayList<>();
        Button clickButton = layout.findViewById(R.id.button_add_set);
        clickButton.setOnClickListener(this);

        TextView textEntryName = layout.findViewById(R.id.text_title);
        TextView textClicksDaily = layout.findViewById(R.id.text_daily_weight);
        TextView textClicksYesterday = layout.findViewById(R.id.text_seven_day_average);
        TextView textClicksTotal = layout.findViewById(R.id.text_total_weight);

//        entrySetViewModel.getName(entrySetId).observe(this, textEntryName::setText);
        clickViewModel.getStringWeightForDay(entrySetId, 0).observe(this, textClicksDaily::setText);
        clickViewModel.getStringClicksForDay(entrySetId, 1).observe(this, textClicksYesterday::setText);
        clickViewModel.getStringClicksTotal(entrySetId).observe(this, textClicksTotal::setText);

        Toast.makeText(
                getContext(),
                "got a set: " + entrySetId,
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

    private EntrySetViewModel getEntrySetViewModel() {
        Database database = Database.getInstance(this.getActivity());
        EntrySetRepository entrySetRepository = new EntrySetRepository(database);
        EntrySetViewModel.Factory factory = new EntrySetViewModel.Factory(entrySetRepository);
        return ViewModelProviders.of(this, factory).get(EntrySetViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_add_set) {
            FragmentManager fm = getFragmentManager();
            WeightsSetDialogFragment dialogFragment = new WeightsSetDialogFragment();
            Bundle args = new Bundle();
            args.putInt("entrySetId", entrySetId);
            dialogFragment.setArguments(args);
            dialogFragment.show(fm, "WeightSetDialogFragment");
        }
    }
}
