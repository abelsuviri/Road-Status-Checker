package com.abelsuviri.roadstatus.di.component;

import android.app.Application;

import com.abelsuviri.roadstatus.RoadApp;
import com.abelsuviri.roadstatus.di.module.AppModule;
import com.abelsuviri.roadstatus.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author Abel Suviri
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ServiceModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(RoadApp app);
}
