package com.slashandhyphen.saplyn_android_arch.view.entry;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;

public class WeightsSetDialogFragment extends DialogFragment implements View.OnClickListener {
    ClickViewModel clickViewModel;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Grab Handles: Framework
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.dialog_fragment_weights_set, container, false);

        // Grab Handles: View Elements

        return layout;
    }

    @Override
    public void onClick(View view) {

    }
}
