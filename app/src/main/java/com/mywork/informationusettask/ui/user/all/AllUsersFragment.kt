package com.mywork.informationusettask.ui.user.all

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mywork.informationusettask.R
import com.mywork.informationusettask.databinding.FragmentAllUsersBinding
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import com.mywork.informationusettask.ui.auth.AuthActivity
import com.mywork.informationusettask.ui.user.proifle.MyProfileViewModel
import com.mywork.informationusettask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllUsersFragment : Fragment(), UsersAdapter.ClickListener {
    private var _binding: FragmentAllUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    @Inject
    lateinit var adapter: UsersAdapter
    private lateinit var allUsersViewModel: AllUsersViewModel
    @Inject
    lateinit var sharedPreferenceCache: SharedPreferenceCache

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_users, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        activity?.title = resources.getString(R.string.users)
        binding.lifecycleOwner = this
        binding.adapter = adapter
        adapter.setOnItemClickListener(this)

        allUsersViewModel = ViewModelProvider(this)[AllUsersViewModel::class.java]

       getAllUsers()

        allUsersViewModel.deleteLiveData.observe(viewLifecycleOwner , Observer {
            if(it) {
                getAllUsers()
                Toast.makeText(activity , getString(R.string.delete_user) , Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getAllUsers(){
        allUsersViewModel.getUsers().observe(viewLifecycleOwner, Observer { response ->
            if (response != null){
                if (response.isEmpty()){
                    sharedPreferenceCache.saveLoggedPhone(null)
                    sharedPreferenceCache.saveLoggedPass(null)
                    startActivity(Intent(activity, AuthActivity::class.java))
                    activity?.overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                    activity?.finish()
                }


                adapter.setListUsers(response)
            }
        })
    }


    override fun clickDelete(user: User) {
        allUsersViewModel.deleteUser(user)
    }
}