package com.slashandhyphen.saplyn_android_arch.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.slashandhyphen.saplyn_android_arch.model.entry.click.Click;
import com.slashandhyphen.saplyn_android_arch.model.entry.click.ClickRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public LiveData<Integer> getIntegerWeightInRange(int foreignId, long timeStart, long timeEnd) {
        return Transformations.map(clickRepository.getClicksInRange(foreignId, timeStart, timeEnd), clicks -> {
            Integer weight = 0;
            if(clicks != null) {
                for(Click click : clicks) {
                    weight += (click.weight * click.reps);
                }
                return weight;
            }
            else return null;
        });
    }

    /**
     * TODO: Have this consume getIntegerWeightInRange
     *
     * @param foreignId
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public LiveData<String> getStringWeightInRange(int foreignId, long timeStart, long timeEnd) {
        return Transformations.map(clickRepository.getClicksInRange(foreignId, timeStart, timeEnd), clicks -> {
            Integer weight = 0;
            if(clicks != null) {
                for(Click click : clicks) {
                    weight += (click.weight * click.reps);
                }
                return weight.toString();
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

    public void click(int entrySetId, Integer weight, Integer reps) {
        clickRepository.addClick(new Click(entrySetId, System.currentTimeMillis(), weight, reps));
    }

    public LiveData<String> getStringWeightForDay(int foreignId, int daysBeforeToday) {
        long timeStart = getTimeStart(daysBeforeToday);
        long timeEnd = getTimeEnd(daysBeforeToday);
        return getStringWeightInRange(foreignId, timeStart, timeEnd);
    }

    /**
     * Get average of weight lifted for the past seven days.
     *
     * @param foreignId the id of the entry set to query.
     * @param daysBeforeToday the last day to include in the seven day set.  Set to zero to start
     *                        the average calculation on the current day.
     * @return A string casted integer of the average weight lifted.
     */
    public LiveData<String> getStringWeightSevenDayAverage(int foreignId, int daysBeforeToday) {
        int seven = 7;
        long timeStart = getTimeStart(daysBeforeToday + seven);
        long timeEnd = getTimeEnd(daysBeforeToday);
        return Transformations.map(clickRepository.getClicksInRange(
                    foreignId, timeStart, timeEnd), clicks -> {

            Integer weight = 0;
            if(clicks != null) {
                for(Click click : clicks) {
                    weight += (click.weight * click.reps);
                }
                Integer avg = weight / seven;
                return avg.toString();
            }
            else return "0";
        });
    }

    /**
     * STUB!!!
     *
     * @param foreignId
     * @return
     */
    public LiveData<String> getStringWeightTotal(int foreignId) {
        return Transformations.map(clickRepository.getClicksInRange(foreignId, 0, 0), clicks -> {
            return "not implemented";
        });
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
