package com.goforer.myhomework.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.goforer.base.extension.loadPhotoUrl
import com.goforer.base.extension.margin
import com.goforer.base.extension.setAspectRatio
import com.goforer.base.view.holder.BaseViewHolder
import com.goforer.myhomework.R
import com.goforer.myhomework.data.source.model.entity.card.Card
import com.goforer.myhomework.databinding.ItemFeedBinding

class FeedAdapter(
    val doOnClick: (view: View, card: Card) -> Unit
) : PagingDataAdapter<Card, BaseViewHolder<Card>>(DIFF_CALLBACK) {
    companion object {
        private val PAYLOAD_TITLE = Any()

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: Card, newItem: Card): Any? {
                return if (sameExceptTitle(oldItem, newItem)) {
                    PAYLOAD_TITLE
                } else {
                    null
                }

            }
        }

        private fun sameExceptTitle(oldItem: Card, newItem: Card): Boolean {
            return oldItem.copy(id = newItem.id) == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Card> {
        val contextThemeWrapper =
            ContextThemeWrapper(parent.context.applicationContext, R.style.AppTheme)

        val binding =
            ItemFeedBinding.inflate(LayoutInflater.from(contextThemeWrapper), parent, false)

        return FeedItemHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Card>, position: Int) {
        val item = getItem(position)

        item?.let {
            if (holder is FeedItemHolder) {
                holder.bindItemHolder(holder, it, position)
            }
        }
    }

    class FeedItemHolder(
        private val binding: ItemFeedBinding,
        private val adapter: FeedAdapter
    ) : BaseViewHolder<Card>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bindItemHolder(holder: BaseViewHolder<Card>, item: Card, position: Int) {
            with(binding) {
                itemView.margin(bottom = itemView.resources.getDimensionPixelSize(R.dimen.dp_12))

                ivPhoto.setAspectRatio(1, 1)
                ivPhoto.loadPhotoUrl(item.img_url)
                tvDescription.text = item.description
                holder.itemView.setOnClickListener {
                    adapter.doOnClick(holder.view, item)
                }
            }
        }

        override fun onItemSelected() {
            view.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            view.setBackgroundColor(0)
        }
    }
}
