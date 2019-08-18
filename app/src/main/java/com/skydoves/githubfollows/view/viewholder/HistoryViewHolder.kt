/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.githubfollows.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.githubfollows.databinding.ItemHistoryBinding
import com.skydoves.githubfollows.models.History
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class HistoryViewHolder(
  private val view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

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

  override fun onLongClick(view: View) = false
}
