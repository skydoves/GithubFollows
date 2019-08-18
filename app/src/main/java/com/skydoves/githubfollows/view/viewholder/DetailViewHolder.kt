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

import android.text.util.Linkify
import android.util.Patterns
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.databinding.ItemDetailInfoBinding
import com.skydoves.githubfollows.models.ItemDetail
import kotlinx.android.synthetic.main.item_detail_info.view.*
import org.jetbrains.anko.textColor

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailViewHolder(view: View) :
  BaseViewHolder(view) {

  private lateinit var itemDetail: ItemDetail
  private val binding by lazy { DataBindingUtil.bind<ItemDetailInfoBinding>(view) }

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is ItemDetail) {
      itemDetail = data
      drawItem()
    }
  }

  private fun drawItem() {
    itemView.run {
      binding?.itemDetail = itemDetail
      binding?.executePendingBindings()
      if (Patterns.WEB_URL.matcher(itemDetail.content).matches()) {
        detail_info_content.textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        Linkify.addLinks(detail_info_content, Linkify.WEB_URLS)
      } else {
        detail_info_content.textColor = ContextCompat.getColor(context, R.color.white)
      }
    }
  }

  override fun onClick(view: View) = Unit

  override fun onLongClick(view: View) = false
}
