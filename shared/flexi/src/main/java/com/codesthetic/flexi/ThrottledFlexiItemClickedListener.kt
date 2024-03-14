package com.codesthetic.flexi

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter

/**
 * Created by edwinmartinez on May 24, 2021
 */
abstract class ThrottledFlexiItemClickedListener : FlexibleAdapter.OnItemClickListener {
    private var lastClickTime = System.currentTimeMillis()

    companion object {
        private const val CLICK_TIME_INTERVAL: Long = 200
    }

    abstract fun onSingleClicked(
        view: View?,
        position: Int,
    )

    override fun onItemClick(
        view: View?,
        position: Int,
    ): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= CLICK_TIME_INTERVAL) {
            onSingleClicked(view, position)
            lastClickTime = now
            return true
        }
        return false
    }
}
