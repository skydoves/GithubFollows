package com.skydoves.githubfollows.view.adapter

import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.view.viewholder.BaseViewHolder
import com.skydoves.githubfollows.view.viewholder.GithubUserHeaderViewHolder
import com.skydoves.githubfollows.view.viewholder.GithubUserViewHolder

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserAdapter(val delegate_header: GithubUserHeaderViewHolder.Delegate,
                        val delegate: GithubUserViewHolder.Delegate) : BaseAdapter() {

    private val section_header = 0
    private val section_follower = 1

    init {
        addSection(ArrayList<GithubUser>())
        addSection(ArrayList<Follower>())
    }

    fun updateHeader(githubUser: Resource<GithubUser>) {
        githubUser.data?.let {
            sections[section_header].clear()
            sections[section_header].add(it)
            notifyDataSetChanged()
        }
    }

    fun addFollowList(followers: List<Follower>) {
        sections[section_follower].addAll(followers)
        notifyDataSetChanged()
    }

    fun clearAll() {
        sections[section_header].clear()
        sections[section_follower].clear()
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: BaseAdapter.SectionRow): Int {
        when(sectionRow.section()) {
            section_header -> return R.layout.layout_detail_header
            else -> return R.layout.item_github_user
        }
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        when(layout) {
            R.layout.layout_detail_header -> return GithubUserHeaderViewHolder(view, delegate_header)
            else -> return GithubUserViewHolder(view, delegate)
        }
    }
}
