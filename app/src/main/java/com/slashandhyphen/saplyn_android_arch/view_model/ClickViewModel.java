package com.slashandhyphen.saplyn_android_arch.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

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

    int offset = 5;  // Number of hours past midnight to include in a day report

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

    /**
     *
     * @param timeStart Beginning of range to report in epoch milliseconds
     * @param timeEnd End of range to report in epoch milliseconds
     * @return The number of clicks in the time frame
     */
    public LiveData<String> getStringClicksInRange(long timeStart, long timeEnd) {
        return Transformations.map(clickRepository.getClicksInRange(timeStart, timeEnd), clicks -> {
            if(clicks != null) {
                return Integer.toString(clicks.size());
            }
            else return "0";
        });
    }

    /**
     *
     * @param numDaysBefore Number of days before today to return the start time of
     * @return Epoch milliseconds of the start time
     */
    private long getTimeStart(int numDaysBefore) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, -numDaysBefore);
        if(calendar.get(Calendar.HOUR_OF_DAY) > offset) {
            calendar.set(Calendar.HOUR_OF_DAY, offset);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTimeInMillis();
        }
        calendar.set(Calendar.HOUR_OF_DAY, offset);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        return calendar.getTimeInMillis();
    }

    /**
     *
     * @param numDaysBefore Number of days before today to return the end time of
     * @return Epoch milliseconds of the end times
     */
    private long getTimeEnd(int numDaysBefore) {
        long timeStart = getTimeStart(numDaysBefore);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStart);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTimeInMillis();
    }

    public LiveData<String> getStringClicksYesterday() {
        long timeStart = getTimeStart(1);
        long timeEnd = getTimeEnd(1);
        return getStringClicksInRange(timeStart, timeEnd);
    }

    public LiveData<String> getStringClicksToday() {

        long timeStart = getTimeStart(0);
        long timeEnd = getTimeEnd(0);
        return getStringClicksInRange(timeStart, timeEnd);
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
