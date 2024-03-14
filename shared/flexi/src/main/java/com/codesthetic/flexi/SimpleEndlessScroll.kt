package com.codesthetic.flexi

import eu.davidea.flexibleadapter.FlexibleAdapter

interface SimpleEndlessScroll : FlexibleAdapter.EndlessScrollListener {
    override fun noMoreLoad(newItemsSize: Int) {
        // such emptiness
    }
}
