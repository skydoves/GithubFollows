package com.skydoves.githubfollows.view.viewholder

import androidx.databinding.DataBindingUtil
import android.view.View
import com.skydoves.githubfollows.databinding.ItemHistoryBinding
import com.skydoves.githubfollows.models.History
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class HistoryViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClicked(history: History)
        fun onDeleteHistory(history: History)
    }

    private lateinit var history: History
    private val binding by lazy { DataBindingUtil.bind<ItemHistoryBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is History) {
            history = data
            drawItemView()
        }
    }

    private fun drawItemView() {
        itemView.run {
            binding?.history = history
            binding?.executePendingBindings()
            item_history_delete.setOnClickListener { delegate.onDeleteHistory(history) }
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClicked(history)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
