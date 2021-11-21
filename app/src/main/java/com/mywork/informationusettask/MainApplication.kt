package com.mywork.informationusettask

import android.app.Application
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application(){
    @Inject
    lateinit var sharedPreferenceCache: SharedPreferenceCache
    override fun onCreate() {
        super.onCreate()
        val lang:String? = sharedPreferenceCache.getLanguage()
        Lingver.init(this, lang!!)

    }
}