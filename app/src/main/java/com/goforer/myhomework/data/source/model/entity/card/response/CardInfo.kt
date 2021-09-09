package com.goforer.myhomework.data.source.model.entity.card.response

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardInfo(
    val ok: Boolean,
    val card: Card,
    val user: User,
    val recommend_cards: List<Card>
) : BaseEntity(), Parcelable
