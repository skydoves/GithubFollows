package com.skydoves.githubfollows.ui.adapter

import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower

import com.skydoves.githubfollows.ui.viewholder.BaseViewHolder
import com.skydoves.githubfollows.ui.viewholder.GithubUserViewHolder

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserAdapter(val delegate: GithubUserViewHolder.Delegate) : BaseAdapter() {

    init {
        addSection(ArrayList<Follower>())
    }

    fun addGithubUserList(githubUsers: List<Follower>) {
        sections[0].addAll(githubUsers)
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: BaseAdapter.SectionRow): Int {
        return R.layout.item_github_user
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return GithubUserViewHolder(view, delegate)
    }
}
