package com.slashandhyphen.saplyn_android_arch.view.home;

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
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySetRepository;
import com.slashandhyphen.saplyn_android_arch.view_model.EntrySetViewModel;

/**
 * Created by Mike on 1/16/2018.
 *
 * Displays a form to create an entry set.
 */

public class EntrySetDialogFragment extends DialogFragment implements View.OnClickListener {
    EntrySetViewModel entrySetViewModel;
    EditText textName;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab Handles: Framework
        Database database = Database.getInstance(this.getActivity());
        EntrySetRepository entrySetRepository = new EntrySetRepository(database);
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(
                R.layout.dialog_fragment_entry_set, container, false);
        EntrySetViewModel.Factory factory = new EntrySetViewModel.Factory(entrySetRepository);
        entrySetViewModel = ViewModelProviders.of(this, factory).get(EntrySetViewModel.class);

        // Grab Handles: View Elements
        textName = layout.findViewById(R.id.text_name_entry);
        Button buttonAccept = layout.findViewById(R.id.button_add_set);
        buttonAccept.setOnClickListener(this);

        // Return
        return layout;
    }

    @Override
    public void onClick(View v) {
        entrySetViewModel.createEntrySet(textName.getText().toString());
        dismiss();
    }
}
