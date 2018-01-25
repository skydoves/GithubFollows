package com.skydoves.githubfollows.utils;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.Color;

import com.skydoves.githubfollows.R;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

/**
 * Developed by skydoves on 2018-01-24.
 * Copyright (c) 2018 skydoves rights reserved.
 */

public class PowerMenuUtils {
    public static PowerMenu getOverflowPowerMenu(Context context, LifecycleOwner lifecycleOwner, OnMenuItemClickListener onMenuItemClickListener) {
        return new PowerMenu.Builder(context)
                .addItem(new PowerMenuItem("Following", true))
                .addItem(new PowerMenuItem("Followers", false))
                .setLifecycleOwner(lifecycleOwner)
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f)
                .setMenuShadow(10f)
                .setTextColor(Color.WHITE)
                .setSelectedTextColor(context.getResources().getColor(R.color.colorPrimary))
                .setMenuColor(context.getResources().getColor(R.color.background800))
                .setSelectedMenuColor(context.getResources().getColor(R.color.background800))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();
    }
}
