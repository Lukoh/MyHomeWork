package com.goforer.myhomework.data.source.model.entity.user

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nickname: String,
    val introduction: String,
    val id: Int
) : BaseEntity(), Parcelable
