package com.slashandhyphen.saplyn_android_arch.view.entry;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slashandhyphen.saplyn_android_arch.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntryActivityFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab Handles: View Elements
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.fragment_entry, container, false);

        // Return
        return layout;
    }

    @Override
    public void onClick(View v) {

    }
}
