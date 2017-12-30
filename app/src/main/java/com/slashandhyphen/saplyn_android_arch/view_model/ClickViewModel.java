package com.slashandhyphen.saplyn_android_arch.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.slashandhyphen.saplyn_android_arch.model.model.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.model.click.ClickRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Mike on 12/27/2017.
 */

public class ClickViewModel extends ViewModel {
    LiveData<List<Click>> clicks;
    private ClickRepository clickRepository;

    @Inject
    public ClickViewModel(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    public LiveData<List<Click>> getClicks() {
        return clickRepository.getClicks();
    }

    public LiveData<String> getStringClicks() {
        return Transformations.map(clickRepository.getClicks(), clicks -> {
            if(clicks != null) {
                return Integer.toString(clicks.size());
            }
            else return "0";
        });
    }

    public void click() {
        clickRepository.addClick(new Click(System.currentTimeMillis()));
    }

    public void click(long time) {
        clickRepository.addClick(new Click(time));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private ClickRepository clickRepository;

        public Factory(ClickRepository clickRepository) {
            this.clickRepository = clickRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ClickViewModel(clickRepository);
        }
    }
}
