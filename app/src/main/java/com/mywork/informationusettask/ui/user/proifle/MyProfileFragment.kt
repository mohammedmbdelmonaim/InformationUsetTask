package com.mywork.informationusettask.ui.user.proifle

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.mywork.informationusettask.databinding.FragmentMyprofileBinding
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import com.mywork.informationusettask.ui.auth.AuthActivity
import com.mywork.informationusettask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.rules.common.maximumLength
import io.github.anderscheow.validator.rules.common.minimumLength
import io.github.anderscheow.validator.rules.common.notEmpty
import io.github.anderscheow.validator.validation
import io.github.anderscheow.validator.validator
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : Fragment() {
    private var _binding: FragmentMyprofileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceCache: SharedPreferenceCache
    private lateinit var navController: NavController
    private lateinit var myProfileViewModel: MyProfileViewModel
    private lateinit var user: User
    companion object {
         var isUserLoggedAdmin: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_myprofile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.my_profile)
        navController = Navigation.findNavController(view)
        myProfileViewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]
        //data binding to view
        binding.lifecycleOwner = this;
        val phone = sharedPreferenceCache.getLoggedPhone()
        val pass = sharedPreferenceCache.getLoggedPass()
        loadUserData(phone!!,pass!!)

        binding.btnEdit.setOnClickListener {
            validationResult()
        }

        binding.btnLogout.setOnClickListener {
           logout()
        }

        myProfileViewModel.loadLiveData.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    if (response.data == null){
                        logout()
                        return@Observer
                    }

                    user = response.data!!
                    isUserLoggedAdmin = user.admin
                    binding.user = user
                }
                else -> {}
            }
        })

        myProfileViewModel.editLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    sharedPreferenceCache.saveLoggedPhone(response.data?.phone)
                    sharedPreferenceCache.saveLoggedPass(response.data?.password)
                    Toast.makeText(activity, getString(R.string.edit_success), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        })

    }

    private fun loadUserData(phone: String,pass:String) {
        myProfileViewModel.getUser(phone,pass)

    }

    private fun logout(){
        sharedPreferenceCache.saveLoggedPhone(null)
        sharedPreferenceCache.saveLoggedPass(null)
        startActivity(Intent(activity, AuthActivity::class.java))
        activity?.overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        activity?.finish()
    }

    private fun validateName() = validation(binding.txtLayoutName) {
        rules {
            +notEmpty(errorMessage = getString(R.string.please_name))
            +minimumLength(minLength = 3, errorMessage = getString(R.string.at_least_3_chars))
        }
    }

    private fun validatePhone() = validation(binding.txtLayoutPhone) {
        rules {
            +notEmpty(errorMessage = getString(R.string.please_phone))
            +minimumLength(minLength = 11, errorMessage = getString(R.string.valid_phone))
            +maximumLength(maxLength = 11, errorMessage = getString(R.string.valid_phone))
        }
    }

    private fun validateAddress() = validation(binding.txtLayoutAddress) {
        rules {
            +notEmpty(errorMessage = getString(R.string.enter_address))
            +minimumLength(minLength = 3, errorMessage = getString(R.string.at_least_3_chars))
        }
    }

    private fun validateCountry() = validation(binding.txtLayoutCountry) {
        rules {
            +notEmpty(errorMessage = getString(R.string.enter_country))
            +minimumLength(minLength = 3, errorMessage = getString(R.string.at_least_3_chars))
        }
    }

    private fun validateCity() = validation(binding.txtLayoutCity) {
        rules {
            +notEmpty(errorMessage = getString(R.string.nter_city))
            +minimumLength(minLength = 3, errorMessage = getString(R.string.at_least_3_chars))
        }
    }

    private fun validatePassword() = validation(binding.txtLayoutPassword) {
        rules {
            +notEmpty(errorMessage = getString(R.string.enter_pass))
            +minimumLength(minLength = 6, errorMessage = getString(R.string.at_least_6_chars))
        }
    }

    private fun validationResult() {
        validator(requireContext().applicationContext) {
            mode = Mode.SINGLE
            listener = object : Validator.OnValidateListener {
                override fun onValidateSuccess(values: List<String>) {
                    val checkedGenderId = binding.radioGender.checkedRadioButtonId
                    val genderID: Int
                    if (checkedGenderId != -1) {
                        genderID = if (binding.rbMale.isChecked) 0 else 1
                        editUser(genderID)
                    } else
                        Toast.makeText(
                            context,
                            getString(R.string.choose_gender),
                            Toast.LENGTH_SHORT
                        ).show()

                }

                override fun onValidateFailed(errors: List<String>) {
                    Log.e("MainActivity", errors.toTypedArray().contentToString())
                }
            }
            validate(
                validateName(),
                validatePhone(),
                validateAddress(),
                validateCountry(),
                validateCity(),
                validatePassword()
            )
        }

    }

    private fun editUser(genderID: Int) {
        val user = User(
            this.user.id,
            binding.edtName.text.toString(),
            binding.edtPassword.text.toString(),
            binding.edtAddress.text.toString(),
            binding.edtPhone.text.toString(),
            binding.edtCountry.text.toString(),
            binding.edtCity.text.toString(),
            genderID,
            binding.switchAdmin.isChecked
        )
        myProfileViewModel.updateUser(user)
    }
}