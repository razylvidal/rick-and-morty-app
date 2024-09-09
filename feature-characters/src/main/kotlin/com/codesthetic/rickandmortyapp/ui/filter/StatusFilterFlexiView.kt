package com.codesthetic.rickandmortyapp.ui.filter

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.feature.characters.R
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.utilsandroid.capitalizeFirstChar
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

/**
 * Created by razylvidal on July 09, 2024
 */
class StatusFilterFlexiView(
    var status: String,
) : BaseFlexiView() {
    var isSelected = false

    override fun equals(other: Any?): Boolean {
        if (other !is StatusFilterFlexiView) return false
        return other.status == status
    }

    override fun getLayoutRes(): Int = R.layout.status_item_layout

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder {
        return StatusFilterViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: BaseFlexiViewHolder?,
        position: Int,
        payloads: MutableList<Any>?,
    ) {
        if (holder is StatusFilterViewHolder) {
            holder.filterName.text = status.capitalizeFirstChar()

            if (isSelected) {
                holder.filterName.setTextColor(Color.WHITE)
                holder.container.setBackgroundResource(R.drawable.filter_selected_background)
            } else {
                holder.filterName.setTextColor(Color.DKGRAY)
                holder.container.setBackgroundResource(R.drawable.filter_unselected_background)
            }
        }
    }

    override fun hashCode(): Int {
        return status.hashCode()
    }

    class StatusFilterViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ) : BaseFlexiViewHolder(view, adapter) {
        val filterName: TextView = view.findViewById(R.id.tvStatus)
        val container: FrameLayout = view.findViewById(R.id.filterContainer)
    }
}
