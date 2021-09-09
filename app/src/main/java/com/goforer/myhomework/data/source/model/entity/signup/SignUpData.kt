package com.goforer.myhomework.data.source.model.entity.signup

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpData(
    val nickname: String,
    val introduction: String,
    val pwd: String
) : BaseEntity(), Parcelable
