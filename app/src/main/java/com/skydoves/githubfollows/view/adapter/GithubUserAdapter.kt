package com.skydoves.githubfollows.view.adapter

import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower

import com.skydoves.githubfollows.view.viewholder.BaseViewHolder
import com.skydoves.githubfollows.view.viewholder.GithubUserViewHolder

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserAdapter(val delegate: GithubUserViewHolder.Delegate) : BaseAdapter() {

    private val section_follower = 0

    init {
        addSection(ArrayList<Follower>())
    }

    fun addFollowList(followers: List<Follower>) {
        sections[section_follower].addAll(followers)
        notifyDataSetChanged()
    }

    fun clearAll() {
        sections[section_follower].clear()
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: BaseAdapter.SectionRow): Int {
        return R.layout.item_github_user
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return GithubUserViewHolder(view, delegate)
    }
}
