package com.skydoves.githubfollows.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.ItemDetail
import com.skydoves.githubfollows.view.viewholder.DetailViewHolder

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("PrivatePropertyName")
class DetailAdapter : BaseAdapter() {

    private val section_itemDetail = 0

    fun addItemDetailList(itemDetail: List<ItemDetail>) {
        clearSections()
        addSection(ArrayList<ItemDetail>())
        for (item in itemDetail) {
            if (item.content.isNotEmpty()) {
                addItemOnSection(section_itemDetail, item)
            }
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_detail_info
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DetailViewHolder(view)
    }
}
