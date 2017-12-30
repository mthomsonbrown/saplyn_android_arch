package com.slashandhyphen.saplyn_android_arch.view.home;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.slashandhyphen.saplyn_android_arch.model.entry.EntryDatabase;
import com.slashandhyphen.saplyn_android_arch.model.model.click.ClickRepository;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;

import com.slashandhyphen.saplyn_android_arch.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements View.OnClickListener {
    private EntryDatabase database;
    private ClickRepository clickRepository;
    private ClickViewModel clickViewModel;

    private ConstraintLayout layout;
    private TextView textClicks;
    private Button buttonClick;

    public HomeActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = EntryDatabase.getInstance(this.getContext());

        clickRepository = new ClickRepository(database);
        ClickViewModel.Factory factory = new ClickViewModel.Factory(clickRepository);
        clickViewModel = ViewModelProviders.of(this, factory)
                .get(ClickViewModel.class);

        layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_home, container, false);
        textClicks = layout.findViewById(R.id.text_clicks);
        buttonClick = layout.findViewById(R.id.button);
        buttonClick.setOnClickListener(this);
        clickViewModel = ViewModelProviders.of(this).get(ClickViewModel.class);
        clickViewModel.getStringClicks().observe(this, clicks -> {
            textClicks.setText(clicks);
        });
        return layout;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            clickViewModel.click();
        }
    }
}
