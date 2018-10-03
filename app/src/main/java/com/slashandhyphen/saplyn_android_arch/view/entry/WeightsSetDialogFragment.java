package com.slashandhyphen.saplyn_android_arch.view.entry;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.slashandhyphen.saplyn_android_arch.R;
import com.slashandhyphen.saplyn_android_arch.model.Database;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;
import com.slashandhyphen.saplyn_android_arch.view_model.ClickViewModel;

public class WeightsSetDialogFragment extends DialogFragment implements View.OnClickListener {
    ClickViewModel clickViewModel;
    EditText textWeight;
    EditText textReps;
    int entrySetId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        entrySetId = getArguments().getInt("entrySetId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Grab Handles: Framework
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.dialog_fragment_weights_set,
                container,
                false
        );
        clickViewModel = ViewModelProviders.of(
                this,
                new ClickViewModel.Factory(
                        new ClickRepository(Database.getInstance(this.getActivity()))
                )
        ).get(ClickViewModel.class);

        // Grab Handles: View Elements
        textWeight = layout.findViewById(R.id.edit_text_weight);
        textReps = layout.findViewById(R.id.edit_text_reps);
        Button addSetButton = layout.findViewById(R.id.button_add_set);
        addSetButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        clickViewModel.click(
                entrySetId,
                Integer.parseInt(textWeight.getText().toString()),
                Integer.parseInt(textReps.getText().toString())
        );
        dismiss();
    }
}
