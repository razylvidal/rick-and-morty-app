package com.codesthetic.flexi

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.viewholders.FlexibleViewHolder

abstract class BaseFlexiView : AbstractFlexibleItem<BaseFlexiView.BaseFlexiViewHolder>() {
    abstract class BaseFlexiViewHolder : FlexibleViewHolder {
        constructor(view: View, adapter: FlexibleAdapter<*>) : super(view, adapter)

        constructor(
            view: View,
            adapter: FlexibleAdapter<*>,
            stickyHeader: Boolean,
        ) : super(view, adapter, stickyHeader)
    }
}
