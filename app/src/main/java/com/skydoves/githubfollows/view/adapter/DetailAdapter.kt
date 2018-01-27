package com.skydoves.githubfollows.view.adapter

import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.ItemDetail

import com.skydoves.githubfollows.view.viewholder.BaseViewHolder
import com.skydoves.githubfollows.view.viewholder.DetailViewHolder

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailAdapter : BaseAdapter() {

    private val section_itemDetail = 0

    init {
        addSection(ArrayList<ItemDetail>())
    }

    fun addItemDetail(itemDetail: ItemDetail) {
        sections[section_itemDetail].add(itemDetail)
        notifyItemChanged(sections[section_itemDetail].size)
    }

    override fun layout(sectionRow: BaseAdapter.SectionRow): Int {
        return R.layout.item_detail_info
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DetailViewHolder(view)
    }
}
