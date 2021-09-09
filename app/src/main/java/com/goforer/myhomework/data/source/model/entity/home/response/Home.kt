package com.goforer.myhomework.data.source.model.entity.home.response

import android.os.Parcelable
import com.goforer.myhomework.data.source.model.entity.BaseEntity
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.data.source.model.entity.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    val ok: Boolean,
    val popular_cards: List<Card>,
    val popular_users: List<User>
) : BaseEntity(), Parcelable {
    companion object {
        fun init() = Home(
            ok = true,
            mutableListOf(),
            mutableListOf()
        )
    }

    sealed class Model(val type: DataType) {
        data class Header(val popular_users: List<User>, val userCount: Int) :
            Model(DataType.HEADER)

        data class Item(val card: Card) : Model(DataType.ITEM)

        enum class DataType { HEADER, ITEM }
    }

    private fun getHeaderItem(): Model.Header {
        return Model.Header(popular_users, popular_users.size)
    }

    fun getModelList(): MutableList<Model> {
        val list = mutableListOf<Model>()

        list.add(getHeaderItem())
        list.addAll(popular_cards.map { Model.Item(it) })

        return list
    }
}
