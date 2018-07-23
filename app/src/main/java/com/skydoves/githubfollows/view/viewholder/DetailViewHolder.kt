package com.skydoves.githubfollows.view.viewholder

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.text.util.Linkify
import android.util.Patterns
import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.databinding.ItemDetailInfoBinding
import com.skydoves.githubfollows.models.ItemDetail
import kotlinx.android.synthetic.main.item_detail_info.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.textColor

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailViewHolder(view: View) : BaseViewHolder(view) {

    private lateinit var itemDetail: ItemDetail
    private val binding by lazy { DataBindingUtil.bind<ItemDetailInfoBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is ItemDetail) {
            itemDetail = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            binding?.itemDetail = itemDetail
            binding?.executePendingBindings()

            when(Patterns.WEB_URL.matcher(itemDetail.content).matches()) {
                true -> {
                    detail_info_content.textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                    Linkify.addLinks(detail_info_content, Linkify.WEB_URLS)
                }
                false -> detail_info_content.textColor = ContextCompat.getColor(context, R.color.white)
            }
        }
    }

    override fun onClick(view: View) {
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
