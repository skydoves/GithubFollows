package com.skydoves.githubfollows.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.githubfollows.extension.gone
import com.skydoves.githubfollows.extension.visible
import com.skydoves.githubfollows.models.GithubUser
import kotlinx.android.synthetic.main.layout_detail_header.view.*

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserHeaderViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private lateinit var githubUser: GithubUser

    interface Delegate {
        fun onCardClicked(githubUser: GithubUser)
    }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is GithubUser) {
            githubUser = data
            drawUserCard()
        }
    }

    private fun drawUserCard() {
        itemView.run {
            Glide.with(context)
                    .load(githubUser.avatar_url)
                    .apply(RequestOptions().circleCrop())
                    .into(detail_header_avatar)
            detail_login.text = githubUser.login
            detail_name.text = githubUser.name
            githubUser.bio?.let {
                detail_bio.visible()
                detail_bio.text = it
            } ?: let { detail_bio.gone() }
        }
    }

    override fun onClick(view: View) {
        delegate.onCardClicked(githubUser)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
