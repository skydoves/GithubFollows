package com.skydoves.githubfollows.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.githubfollows.models.Follower
import kotlinx.android.synthetic.main.item_github_user.view.*

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(githubUser: Follower, view: View)
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
                    .apply(RequestOptions().circleCrop().override(100))
                    .into(item_user_avatar)
            item_user_name.text = githubUser.login
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(githubUser, itemView.item_user_avatar)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
