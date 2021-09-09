package com.goforer.myhomework.data.source.model.entity.card

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    val user_id: Long,
    val img_url: String,
    val description: String,
    val id: Int
) : BaseEntity(), Parcelable
