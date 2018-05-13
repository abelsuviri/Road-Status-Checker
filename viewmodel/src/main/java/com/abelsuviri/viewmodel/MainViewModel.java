package com.abelsuviri.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.abelsuviri.data.model.Road;
import com.abelsuviri.data.network.IRoadService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author Abel Suviri
 */

public class MainViewModel extends ViewModel {
    private IRoadService roadService;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFailed = new MutableLiveData<>();
    private MutableLiveData<Boolean> exists = new MutableLiveData<>();

    @Inject
    public MainViewModel(IRoadService roadService) {
        this.roadService = roadService;
        isLoading.setValue(false);
    }

    /**
     * This method makes the call to the server to retrieve the road info.
     * @return road info
     */
    @SuppressLint("CheckResult")
    public LiveData<List<Road>> getRoadInfo(String roadId) {
        MutableLiveData<List<Road>> road = new MutableLiveData<>();
        isLoading.setValue(true);
        isFailed.setValue(false);
        roadService.getRoads(roadId, BuildConfig.APP_ID, BuildConfig.APP_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    isLoading.setValue(false);
                    exists.setValue(true);
                    road.setValue(response);
                }, error -> {
                    if (((HttpException) error).code() == 404) {
                        exists.setValue(false);
                    } else {
                        isFailed.setValue(true);
                    }

                    isLoading.setValue(false);
                });

        return road;
    }

    /**
     * This method will trigger the error message when the road does not exists.
     * @return boolean exists
     */
    public MutableLiveData<Boolean> getExists() {
        return exists;
    }

    /**
     * This method will trigger the loading layer in the view
     * @return boolean loading
     */
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * This method will trigger the retry dialog in the view
     * @return boolean isFailed
     */
    public MutableLiveData<Boolean> getIsFailed() {
        return isFailed;
    }
}
