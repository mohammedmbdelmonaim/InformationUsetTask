package com.mywork.informationusettask.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mywork.informationusettask.BaseActivity
import com.mywork.informationusettask.R
import com.mywork.informationusettask.databinding.ActivityAuthBinding
import com.mywork.informationusettask.databinding.ActivityUserBinding
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import com.mywork.informationusettask.ui.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity:BaseActivity() {
    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var sharedPreferenceCache: SharedPreferenceCache
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val phone = sharedPreferenceCache.getLoggedPhone()
        if (phone != null){
            startActivity(Intent(this , UserActivity::class.java))
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}