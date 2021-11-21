package com.mywork.informationusettask.ui.user


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mywork.informationusettask.BaseActivity
import com.mywork.informationusettask.R
import com.mywork.informationusettask.databinding.ActivityUserBinding
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity : BaseActivity() {
    private var _binding:ActivityUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //initialize bottom navigation with navigation component
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.user_nav) as NavHostFragment?
        val navCo = navHostFragment!!.navController
        binding.bottomNavigation.setupWithNavController(navCo)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}