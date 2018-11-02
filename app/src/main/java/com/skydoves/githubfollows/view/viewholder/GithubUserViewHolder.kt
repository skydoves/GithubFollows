package com.skydoves.githubfollows.view.viewholder

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.databinding.ItemGithubUserBinding
import com.skydoves.githubfollows.models.Follower
import kotlinx.android.synthetic.main.item_github_user.view.*

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GithubUserViewHolder(view: View, private val delegate: Delegate) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(githubUser: Follower, view: View)
    }

    private lateinit var githubUser: Follower
    private val binding by lazy { DataBindingUtil.bind<ItemGithubUserBinding>(view) }

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is Follower) {
            githubUser = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.follower = githubUser
        binding?.executePendingBindings()
        itemView.run {
            item_user_veilLayout.veil()
            Glide.with(context)
                    .load(githubUser.avatar_url)
                    .apply(RequestOptions().circleCrop().override(100).placeholder(R.drawable.ic_account))
                    .listener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            item_user_veilLayout.unVeil()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            item_user_veilLayout.unVeil()
                            return false
                        }
                    })
                    .into(item_user_avatar)
        }
    }

    override fun onClick(view: View) {
        delegate.onItemClick(githubUser, itemView.item_user_avatar)
    }

    override fun onLongClick(view: View): Boolean {
        return false
    }
}
