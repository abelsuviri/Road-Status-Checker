package com.abelsuviri.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.abelsuviri.data.model.Road;
import com.abelsuviri.data.network.IRoadService;
import com.abelsuviri.viewmodel.mock.MockJson;
import com.abelsuviri.viewmodel.rule.RxSchedulerRule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Abel Suviri
 */
public class MainViewModelTest {

    @Rule
    public final RxSchedulerRule rxSchedulerRule = new RxSchedulerRule();

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private IRoadService roadService;

    @Mock
    private Observer<List<Road>> observer;

    @Mock
    private MainViewModel mainViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(roadService);
    }

    @Test
    public void test_get_road_successfully() {
        Gson gson = new Gson();
        TypeToken<List<Road>> token = new TypeToken<List<Road>>(){};
        List<Road> roadList = gson.fromJson(MockJson.VALID_ROAD, token.getType());
        Mockito.when(roadService.getRoads("a1", BuildConfig.APP_ID, BuildConfig.APP_KEY)).thenReturn(Observable.just(roadList));
        mainViewModel.getRoadInfo("a1").observeForever(observer);
        Road road = mainViewModel.getRoadInfo("a1").getValue().get(0);
        Assert.assertEquals(road, roadList.get(0));
    }
}