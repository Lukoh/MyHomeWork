package com.goforer.myhomework.data.source.model.entity.user.response

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import com.goforer.myhomework.data.source.model.entity.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val ok: Boolean,
    val user: User
) : BaseEntity(), Parcelable
