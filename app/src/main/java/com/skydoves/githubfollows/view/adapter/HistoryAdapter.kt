package com.skydoves.githubfollows.view.adapter

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.view.viewholder.HistoryViewHolder

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("PrivatePropertyName")
class HistoryAdapter(
  private val delegate: HistoryViewHolder.Delegate
) : BaseAdapter() {

  private val section_history = 0

  init {
    addSection(ArrayList<History>())
  }

  fun updateItemList(histories: List<History>) {
    sections()[section_history].clear()
    sections()[section_history].addAll(histories)
    notifyDataSetChanged()
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_history

  override fun viewHolder(layout: Int, view: View) = HistoryViewHolder(view, delegate)
}
