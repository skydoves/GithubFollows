package com.skydoves.githubfollows.view.viewholder

import androidx.databinding.DataBindingUtil
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.githubfollows.databinding.LayoutDetailHeaderBinding
import com.skydoves.githubfollows.models.GithubUser
import kotlinx.android.synthetic.main.layout_detail_header.view.*

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserHeaderViewHolder(
  private val view: View,
  private val delegate: Delegate
)
    : BaseViewHolder(view) {

    private lateinit var githubUser: GithubUser
    val binding by lazy { DataBindingUtil.bind<LayoutDetailHeaderBinding>(view) }

    interface Delegate {
        fun onCardClicked(githubUser: GithubUser)
    }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is GithubUser) {
            githubUser = data
            drawUserCard()
        }
    }

    private fun drawUserCard() {
        itemView.run {
            binding?.githubUser = githubUser
            binding?.executePendingBindings()

            Glide.with(context)
                    .load(githubUser.avatar_url)
                    .apply(RequestOptions().circleCrop())
                    .into(detail_header_avatar)
        }
    }

    override fun onClick(view: View) {
        delegate.onCardClicked(githubUser)
    }

    override fun onLongClick(view: View) = false
}
