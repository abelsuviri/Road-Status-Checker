package com.abelsuviri.roadstatus.di.module;

import com.abelsuviri.roadstatus.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Abel Suviri
 */

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
