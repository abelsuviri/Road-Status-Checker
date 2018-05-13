package com.abelsuviri.roadstatus.di.module;

import com.abelsuviri.data.network.IRoadService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Abel Suviri
 */

@Module
public class ServiceModule {
    @Provides
    @Singleton
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.tfl.gov.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public IRoadService roadService(Retrofit retrofit) {
        return retrofit.create(IRoadService.class);
    }
}