@file:Suppress("MagicNumber")

package com.codesthetic.rickandmortyapp.ui.season

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.feature.episodes.R
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.shared.theme.R.color
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

class SeasonFlexiView(
    val season: Int,
) : BaseFlexiView() {
    var isSelected = false

    override fun equals(other: Any?): Boolean {
        if (other !is SeasonFlexiView) return false
        return season == other.season
    }

    override fun getLayoutRes(): Int = R.layout.season_item_layout

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder {
        return SeasonViewHolder(view, adapter)
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: BaseFlexiViewHolder?,
        position: Int,
        payloads: MutableList<Any>?,
    ) {
        if (holder is SeasonViewHolder) {
            holder.season.text = "Season $season"
            holder.season.setTextColor(
                ContextCompat.getColor(
                    holder.season.context,
                    if (isSelected) color.color_primary_variant else color.color_unselected
                )
            )
            holder.season.setTextSize(TypedValue.COMPLEX_UNIT_SP, if (isSelected) 20.0f else 18.0f)
            holder.season.setTypeface(
                holder.season.typeface,
                if (isSelected) Typeface.BOLD else Typeface.NORMAL
            )
        }
    }

    class SeasonViewHolder(
        view: View,
        adapter: FlexibleAdapter<*>,
    ) : BaseFlexiViewHolder(view, adapter) {
        val season: TextView = view.findViewById(R.id.tvSeason)
    }

    override fun hashCode(): Int = season.hashCode()
}
