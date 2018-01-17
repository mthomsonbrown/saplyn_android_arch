package com.slashandhyphen.saplyn_android_arch.view.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.slashandhyphen.saplyn_android_arch.model.Database;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySet;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySetRepository;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.view.entry.EntryActivity;
import com.slashandhyphen.saplyn_android_arch.view_model.EntrySetViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements View.OnClickListener {
    private HomeActivity activity;

    RecyclerView recycler;
    EntrySetAdapter adapter;
    List<EntrySet> entrySetList;
    EntrySetViewModel entrySetViewModel;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab Handles: Framework
        activity = (HomeActivity) getActivity();
        Database database = Database.getInstance(this.getActivity());
        EntrySetRepository entrySetRepository = new EntrySetRepository(database);
        EntrySetViewModel.Factory factory = new EntrySetViewModel.Factory(entrySetRepository);
        entrySetViewModel = ViewModelProviders.of(this, factory).get(EntrySetViewModel.class);

        // Grab Handles: View Elements
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.fragment_home, container, false);
        Button buttonEntries = layout.findViewById(R.id.button_entries);
        buttonEntries.setOnClickListener(this);
        Button buttonAddEntrySet = layout.findViewById(R.id.button_new_entry_set);
        buttonAddEntrySet.setOnClickListener(this);
        recycler = layout.findViewById(R.id.recycler_view_entry_sets);
        entrySetList = new ArrayList<>();

        // Prepare RecyclerView
        adapter = new EntrySetAdapter(entrySetList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        // Populate views
        entrySetViewModel.getEntrySets().observe(this, entrySets -> {

            // TODO: Use more efficient method
            entrySetList.clear();
            entrySetList.addAll(entrySets);
            adapter.notifyDataSetChanged();
        });

        // Return
        return layout;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_entries) {
            Intent myIntent = new Intent(activity, EntryActivity.class);
            startActivity(myIntent);
        }

        if(v.getId() == R.id.button_new_entry_set) {
            entrySetViewModel.createEntrySet("New Entry Set");
        }
    }
}
