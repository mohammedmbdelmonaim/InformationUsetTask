package com.mywork.informationusettask.model.entity

import android.os.Build
import android.service.autofill.Validators.and
import android.service.autofill.Validators.or
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.anderscheow.validator.conditions.common.and
import io.github.anderscheow.validator.conditions.common.or
import io.github.anderscheow.validator.rules.common.contain
import io.github.anderscheow.validator.rules.common.endsWith
import io.github.anderscheow.validator.rules.common.minimumLength
import io.github.anderscheow.validator.rules.regex.email
import io.github.anderscheow.validator.validation

@Entity(tableName = "users")
data class User(@PrimaryKey(autoGenerate = true)
                var id:Int? = null,
                val name:String,
                val password:String,
                val address:String,
                val phone:String,
                val country:String,
                val city:String,
                val genderId:Int,
                val admin:Boolean,
                )