package com.skydoves.githubfollows.ui.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower
import kotlinx.android.synthetic.main.item_github_user.view.*

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(githubUser: Follower)
    }

    private lateinit var githubUser: Follower

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is Follower) {
            githubUser = data
            drawUI()
        }
    }

    private fun drawUI() {
        itemView.run {
            Glide.with(context)
                    .load(githubUser.avatar_url)
                    .apply(RequestOptions().circleCrop().placeholder(R.drawable.placeholder))
                    .into(item_github_user_thumbnail)
            item_github_user_title.text = githubUser.login
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(githubUser)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
