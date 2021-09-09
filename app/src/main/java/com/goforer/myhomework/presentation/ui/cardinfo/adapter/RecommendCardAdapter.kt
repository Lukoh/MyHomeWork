package com.goforer.myhomework.presentation.ui.cardinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import com.goforer.base.extension.loadPhotoUrl
import com.goforer.base.extension.margin
import com.goforer.base.extension.setAspectRatio
import com.goforer.myhomework.R
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.databinding.ItemItemBinding

class RecommendCardAdapter(
    val doOnClick: (view: View, card: Card) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cards: List<Card> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        val binding =
            ItemItemBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val card = cards[position]

        if (holder is ItemHolder) {
            with(holder.binding) {
                holder.itemView.margin(bottom = holder.itemView.resources.getDimensionPixelSize(com.goforer.myhomework.R.dimen.dp_12))

                ivPhoto.setAspectRatio(1, 1)
                ivPhoto.loadPhotoUrl(card.img_url)
                tvDescription.text = card.description
                holder.itemView.setOnClickListener {
                    doOnClick(holder.itemView, card)
                }
            }
        }
    }

    override fun getItemCount() = cards.size

    fun setCards(cardList: List<Card>) {
        cards = cardList
    }

    class ItemHolder(val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root)
}
