package com.mywork.informationusettask.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mywork.informationusettask.R
import com.mywork.informationusettask.databinding.FragmentLoginBinding
import com.mywork.informationusettask.databinding.FragmentRegisterBinding
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.ui.auth.login.LoginViewModel
import com.mywork.informationusettask.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.rules.common.maximumLength
import io.github.anderscheow.validator.rules.common.minimumLength
import io.github.anderscheow.validator.rules.common.notEmpty
import io.github.anderscheow.validator.validation
import io.github.anderscheow.validator.validator

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =  Navigation.findNavController(view)
        activity?.title = resources.getString(R.string.register)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            validationResult()

        }

        registerViewModel.registerLiveData.observe(viewLifecycleOwner , Observer {response ->
            when(response){
                is Resource.Success -> {
                    navController.navigateUp()
                    Toast.makeText(activity , getString(R.string.added_success) , Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })

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
                    val genderID:Int
                    if (checkedGenderId != -1) {
                        genderID = if (binding.rbMale.isChecked) 0 else 1
                        registerUser(genderID)
                    }else
                        Toast.makeText(context , getString(R.string.choose_gender), Toast.LENGTH_SHORT).show()

                }

                override fun onValidateFailed(errors: List<String>) {
                    Log.e("MainActivity", errors.toTypedArray().contentToString())
                }
            }
            validate(validateName() , validatePhone() , validateAddress() , validateCountry() , validateCity() , validatePassword())
        }

    }

    private fun registerUser(genderID:Int) {
        val user = User(null,binding.edtName.text.toString() , binding.edtPassword.text.toString() , binding.edtAddress.text.toString() , binding.edtPhone.text.toString(), binding.edtCountry.text.toString() , binding.edtCity.text.toString() , genderID , binding.switchAdmin.isChecked)
        registerViewModel.insertUser(user)
    }


}