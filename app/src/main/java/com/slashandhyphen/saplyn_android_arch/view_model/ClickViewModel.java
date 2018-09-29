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

    public LiveData<List<Click>> getClicks(int foreignId) {
        return Transformations.map(clickRepository.getClicks(foreignId), clicks -> {
            if(clicks != null) {
                return clicks;
            }
            else return new ArrayList<Click>();
        });
    }

    /**
     *
     * @return The total number of clicks.
     */
    public LiveData<String> getStringClicksTotal(int foreignId) {
        return Transformations.map(clickRepository.getClicks(foreignId), clicks -> {
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
    public LiveData<String> getStringClicksInRange(int foreignId, long timeStart, long timeEnd) {
        return Transformations.map(clickRepository.getClicksInRange(foreignId, timeStart, timeEnd), clicks -> {
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

    /**
     * Gather how many clicks happened in a day.
     *
     * @param daysBeforeToday Number of days before today to return the number of clicks of.
     *                        Set to 0 for number of clicks today.
     * @return Number of clicks that happened on that day.
     */
    public LiveData<String> getStringClicksForDay(int foreignId, int daysBeforeToday) {
        long timeStart = getTimeStart(daysBeforeToday);
        long timeEnd = getTimeEnd(daysBeforeToday);
        return getStringClicksInRange(foreignId, timeStart, timeEnd);
    }

    public void click(int entrySetId) {
        clickRepository.addClick(new Click(entrySetId, System.currentTimeMillis()));
    }

    public void click(int entrySetId, long time) {
        clickRepository.addClick(new Click(entrySetId, time));
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
