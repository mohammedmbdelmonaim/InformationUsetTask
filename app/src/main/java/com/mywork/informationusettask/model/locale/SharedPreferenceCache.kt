package com.mywork.informationusettask.model.locale

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceCache @Inject constructor(private val sharedPreferences: SharedPreferences){
    private val editor = sharedPreferences.edit()

    fun saveLanguage(lang:String){
        editor.apply {
            putString("lang" , lang)
            apply()
        }
    }

    fun getLanguage() : String? = sharedPreferences.getString("lang" ,"ar")

    fun saveLoggedPhone(phone:String?){
        editor.apply {
            putString("phone" , phone)
            apply()
        }
    }

    fun getLoggedPhone() : String? = sharedPreferences.getString("phone" , null)

    fun saveLoggedPass(password:String?){
        editor.apply {
            putString("pass" , password)
            apply()
        }
    }

    fun getLoggedPass() : String? = sharedPreferences.getString("pass" , null)

}