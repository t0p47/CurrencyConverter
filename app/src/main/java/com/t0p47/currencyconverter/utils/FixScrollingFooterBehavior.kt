package com.t0p47.currencyconverter.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class FixScrollingFooterBehavior : AppBarLayout.ScrollingViewBehavior {

    private var appBarLayout: AppBarLayout? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var isBottomAdsEnabled = true

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        if (appBarLayout == null) {
            appBarLayout = dependency as AppBarLayout
        }

        val result = super.onDependentViewChanged(parent, child, dependency)
        val bottomPadding = calculateBottomPadding(appBarLayout!!)
        val paddingChanged = bottomPadding != child.paddingBottom
        if (paddingChanged) {
            child.setPadding(
                child.paddingLeft,
                child.paddingTop,
                child.paddingRight,
                if(isBottomAdsEnabled) bottomPadding else 0
            )
            child.requestLayout()
        }
        return paddingChanged || result
    }


    // Calculate the padding needed to keep the bottom of the view pager's content at the same location on the screen.
    private fun calculateBottomPadding(dependency: AppBarLayout): Int {
        val totalScrollRange = dependency.totalScrollRange
        return totalScrollRange + dependency.top
    }
}