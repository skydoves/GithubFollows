package com.skydoves.githubfollows.utils

import androidx.lifecycle.LifecycleOwner
import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat

import com.skydoves.githubfollows.R
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem

/**
 * Developed by skydoves on 2018-01-24.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object PowerMenuUtils {
    fun getOverflowPowerMenu(context: Context, lifecycleOwner: LifecycleOwner, onMenuItemClickListener: OnMenuItemClickListener<PowerMenuItem>): PowerMenu {
        return PowerMenu.Builder(context)
                .addItem(PowerMenuItem("Following", true))
                .addItem(PowerMenuItem("Followers", false))
                .setLifecycleOwner(lifecycleOwner)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(Color.WHITE)
                .setSelectedTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setMenuColor(ContextCompat.getColor(context, R.color.background800))
                .setSelectedMenuColor(ContextCompat.getColor(context, R.color.background800))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build()
    }
}
