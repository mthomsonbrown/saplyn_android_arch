package com.slashandhyphen.saplyn_android_arch.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySet;
import com.slashandhyphen.saplyn_android_arch.model.EntrySet.EntrySetRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mike on 1/16/2018.
 */

public class EntrySetViewModel extends ViewModel {

    private EntrySetRepository entrySetRepository;

    @Inject
    public EntrySetViewModel(EntrySetRepository entrySetRepository) {
        this.entrySetRepository = entrySetRepository;
    }

    public LiveData<List<EntrySet>> getEntrySets() {
        return Transformations.map(entrySetRepository.getEntrySets(), entrySets -> {
            if(entrySets != null) {
                return entrySets;
            }
            else return new ArrayList<EntrySet>();
        });
    }

    public void createEntrySet(String name) {
        entrySetRepository.addEntrySet(new EntrySet(name));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private EntrySetRepository entrySetRepository;

        public Factory(EntrySetRepository entrySetRepository) {
            this.entrySetRepository = entrySetRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new EntrySetViewModel(entrySetRepository);
        }
    }
}
