package com.goforer.myhomework.data.source.model.entity.home.response

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import com.goforer.myhomework.data.source.model.entity.card.Card
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feed(
    val ok: Boolean,
    val cards: List<Card>
) : BaseEntity(), Parcelable
