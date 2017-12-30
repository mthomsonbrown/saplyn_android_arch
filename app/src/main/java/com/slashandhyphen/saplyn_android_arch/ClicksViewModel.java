package com.slashandhyphen.saplyn_android_arch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mike on 12/27/2017.
 */

public class ClicksViewModel extends ViewModel {
    LiveData<List<Click>> clicks;
    private ClicksRepository clicksRepository;

    @Inject
    public ClicksViewModel(ClicksRepository clicksRepository) {
        this.clicksRepository = clicksRepository;
    }

    public LiveData<List<Click>> getClicks() {
        return clicksRepository.getClicks();
    }

    public LiveData<String> getStringClicks() {
        return Transformations.map(clicksRepository.getClicks(), clicks -> {
            if(clicks != null) {
                return Integer.toString(clicks.size());
            }
            else return "0";
        });
    }

    public void click() {
        clicksRepository.addClick(new Click(System.currentTimeMillis()));
    }

    public void click(long time) {
        clicksRepository.addClick(new Click(time));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private ClicksRepository clicksRepository;

        public Factory(ClicksRepository clicksRepository) {
            this.clicksRepository = clicksRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ClicksViewModel(clicksRepository);
        }
    }
}
