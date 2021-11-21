package com.mywork.informationusettask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}