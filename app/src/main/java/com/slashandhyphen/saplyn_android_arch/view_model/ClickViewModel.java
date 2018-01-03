package com.slashandhyphen.saplyn_android_arch.view_model;

import android.support.annotation.NonNull;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Mike on 12/27/2017.
 */

public class ClickViewModel extends ViewModel {

    private ClickRepository clickRepository;

    @Inject
    public ClickViewModel(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    public LiveData<List<Click>> getClicks() {
        return Transformations.map(clickRepository.getClicks(), clicks -> {
            if(clicks != null) {
                return clicks;
            }
            else return new ArrayList<Click>();
        });
    }

    public LiveData<String> getStringClicks() {
        return Transformations.map(clickRepository.getClicks(), clicks -> {
            if(clicks != null) {
                return Integer.toString(clicks.size());
            }
            else return "0";
        });
    }

    public LiveData<String> getStringClicksPerDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long timeStart = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        long timeEnd = calendar.getTimeInMillis();

        return Transformations.map(clickRepository.getClicksInRange(timeStart, timeEnd), clicks -> {
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
