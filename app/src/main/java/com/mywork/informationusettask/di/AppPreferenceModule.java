package com.mywork.informationusettask.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.preference.PreferenceManager;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppPreferenceModule {

    @Provides
    @Singleton
    public SharedPreferences providePreference(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
