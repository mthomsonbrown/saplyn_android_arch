package com.slashandhyphen.saplyn_android_arch.view.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.slashandhyphen.saplyn_android_arch.model.Database;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;
import com.slashandhyphen.saplyn_android_arch.view.entry.EntryActivity;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;

import com.slashandhyphen.saplyn_android_arch.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements View.OnClickListener {
    private HomeActivity activity;

    private ClickViewModel clickViewModel;

    private TextView textClicks;
    private TextView textClicksTotal;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab Handles: Framework
        activity = (HomeActivity) getActivity();
        Database database = Database.getInstance(this.getContext());
        ClickRepository clickRepository = new ClickRepository(database);
        ClickViewModel.Factory factory = new ClickViewModel.Factory(clickRepository);
        clickViewModel = ViewModelProviders.of(this, factory)
                .get(ClickViewModel.class);
        clickViewModel = ViewModelProviders.of(this).get(ClickViewModel.class);

        // Grab Handles: View Elements
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.fragment_home, container, false);
        Button buttonClick = layout.findViewById(R.id.button_click);
        buttonClick.setOnClickListener(this);
        Button buttonEntries = layout.findViewById(R.id.button_entries);
        buttonEntries.setOnClickListener(this);
        textClicks = layout.findViewById(R.id.text_clicks);
        textClicksTotal = layout.findViewById(R.id.text_total_clicks);

        // Populate View
        clickViewModel.getStringClicksPerDay().observe(this, clicks -> {
            textClicks.setText(clicks);
        });
        clickViewModel.getStringClicks().observe(this, clicks -> {
            textClicksTotal.setText(clicks);
        });

        // Return
        return layout;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_click) {
            clickViewModel.click();
        }

        if(v.getId() == R.id.button_entries) {
            Intent myIntent = new Intent(activity, EntryActivity.class);
            startActivity(myIntent);
        }
    }
}
