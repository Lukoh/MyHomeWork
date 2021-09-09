package com.goforer.myhomework.data.source.model.entity.login

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogInData(
    val nickname: String,
    val pwd: String
) : BaseEntity(), Parcelable
