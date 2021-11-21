package com.mywork.informationusettask.ui.auth.login

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
import com.mywork.informationusettask.databinding.FragmentLoginBinding
import com.mywork.informationusettask.model.locale.SharedPreferenceCache
import com.mywork.informationusettask.ui.auth.AuthActivity
import com.mywork.informationusettask.ui.user.UserActivity
import com.mywork.informationusettask.utils.Resource
import com.yariksoffice.lingver.Lingver
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
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferenceCache: SharedPreferenceCache
    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.login)
        navController = Navigation.findNavController(view)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnAr.setOnClickListener {
            sharedPreferenceCache.saveLanguage("ar")
            Lingver.getInstance().setLocale(requireContext(), "ar")
            reloadActivity()
        }

        binding.btnEn.setOnClickListener {
            sharedPreferenceCache.saveLanguage("en")
            Lingver.getInstance().setLocale(requireContext(), "en")
            reloadActivity()
        }

        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            validationResult()

//

        }

        loginViewModel.loginLiveData.observe(viewLifecycleOwner , Observer {response ->
            if (response.data == null){
                Toast.makeText(activity , getString(R.string.verify_data) , Toast.LENGTH_SHORT).show()
                return@Observer
            }
            when(response){
                is Resource.Success -> {
                    sharedPreferenceCache.saveLoggedPhone(response.data!!.phone)
                    sharedPreferenceCache.saveLoggedPass(response.data!!.password)
                    startActivity(Intent(activity , UserActivity::class.java))
                    activity?.overridePendingTransition(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right)
                    Toast.makeText(activity , getString(R.string.hello)+" "+response.data?.name , Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
                else -> {}
            }
        })
    }

    private fun validatePhone() = validation(binding.txtLayoutPhone) {
        rules {
            +notEmpty(errorMessage = getString(R.string.please_phone))
            +minimumLength(minLength = 11, errorMessage = getString(R.string.valid_phone))
            +maximumLength(maxLength = 11, errorMessage = getString(R.string.valid_phone))
        }
    }

    private fun validatePassword() = validation(binding.txtLayoutPassword) {
        rules {
            +notEmpty(errorMessage = getString(R.string.valid_phone))
            +minimumLength(minLength = 6, errorMessage = getString(R.string.at_least_6_chars))
        }
    }

    private fun validationResult() {
        validator(requireContext().applicationContext) {
            mode = Mode.CONTINUOUS
            listener = object : Validator.OnValidateListener {
                override fun onValidateSuccess(values: List<String>) {
                    loginUser(binding.edtPhone.text.toString() , binding.edtPassword.text.toString())
                }

                override fun onValidateFailed(errors: List<String>) {
                    Log.e("MainActivity", errors.toTypedArray().contentToString())
                }
            }
            validate(validatePhone() , validatePassword())
        }

    }

    private fun reloadActivity() {
        startActivity(Intent(activity, AuthActivity::class.java))
    }

    private fun loginUser(phone:String,pass:String){
        loginViewModel.getUser(phone,pass)
    }
}