package com.slashandhyphen.saplyn_android_arch;

import android.arch.lifecycle.ViewModelProviders;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment implements View.OnClickListener {
    private EntryDatabase database;
    private ClicksRepository clicksRepository;
    private ClicksViewModel clicksViewModel;

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

        clicksRepository = new ClicksRepository(database);
        ClicksViewModel.Factory factory = new ClicksViewModel.Factory(clicksRepository);
        clicksViewModel = ViewModelProviders.of(this, factory)
                .get(ClicksViewModel.class);

        layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_home, container, false);
        textClicks = layout.findViewById(R.id.text_clicks);
        buttonClick = layout.findViewById(R.id.button);
        buttonClick.setOnClickListener(this);
        clicksViewModel = ViewModelProviders.of(this).get(ClicksViewModel.class);
        clicksViewModel.getClicks().observe(this, clicks -> {
            if(clicks != null)
                textClicks.setText(Integer.toString(clicks.size()));
        });
        return layout;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            clicksViewModel.click();
        }
    }
}
