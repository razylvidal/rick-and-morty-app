package com.codesthetic.rickandmortyapp.ui.filter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.engine.core.gender.domain.Gender
import com.codesthetic.feature_characters.R
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.utilsandroid.capitalizeFirstChar
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible

/**
 * Created by razylvidal on July 09, 2024
 */
class GenderFilterFlexiView(
    var genderOption: String,
) : BaseFlexiView() {
    var isSelected = false

    override fun equals(other: Any?): Boolean {
        if (other !is GenderFilterFlexiView) return false
        return other.genderOption == genderOption
    }

    override fun getLayoutRes(): Int = R.layout.gender_item_layout

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder {
        return GenderFilterViewHolder(view, adapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: BaseFlexiViewHolder?,
        position: Int,
        payloads: MutableList<Any>?,
    ) {
        if (holder is GenderFilterViewHolder) {
            holder.filterName.text = genderOption.capitalizeFirstChar()
            holder.filterIcon.visibility = View.VISIBLE
            holder.filterName.textSize = DEFAULT_FILTER_NAME_SIZE

            when (genderOption.capitalizeFirstChar()) {
                Gender.Option.ALL.value -> {
                    holder.filterIcon.visibility = View.GONE
                    holder.filterName.textSize = ADJUSTED_FILTER_NAME_SIZE
                }

                Gender.Option.FEMALE.value -> {
                    holder.filterIcon.setBackgroundResource(com.codesthetic.shared.theme.R.drawable.baseline_female_24)
                }

                Gender.Option.MALE.value -> {
                    holder.filterIcon.setBackgroundResource(com.codesthetic.shared.theme.R.drawable.baseline_male_24)
                }

                Gender.Option.GENDERLESS.value -> {
                    holder.filterIcon.setBackgroundResource(
                        com.codesthetic.shared.theme.R.drawable.outline_genderless_24
                    )
                }

                Gender.Option.UNKNOWN.value -> {
                    holder.filterIcon.setBackgroundResource(com.codesthetic.shared.theme.R.drawable.outline_unknown_24)
                }
            }

            if (isSelected) {
                holder.filterIcon.setColorFilter(Color.WHITE)
                holder.filterIcon.imageTintMode = PorterDuff.Mode.SRC_IN
                holder.filterName.setTextColor(Color.WHITE)
                holder.container.setBackgroundResource(R.drawable.filter_selected_background)
            } else {
                holder.filterIcon.setColorFilter(Color.GRAY)
                holder.filterIcon.imageTintMode = PorterDuff.Mode.SRC_IN
                holder.filterName.setTextColor(Color.GRAY)
                holder.container.setBackgroundResource(R.drawable.filter_unselected_background)
            }
        }
    }

    override fun hashCode(): Int {
        return genderOption.hashCode()
    }

    class GenderFilterViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ) : BaseFlexiViewHolder(view, adapter) {
        val filterName: TextView = view.findViewById(R.id.tvFilterName)
        val filterIcon: ImageView = view.findViewById(R.id.ivFilterIcon)
        val container: LinearLayout = view.findViewById(R.id.filterContainer)
    }

    companion object {
        private const val DEFAULT_FILTER_NAME_SIZE = 10F
        private const val ADJUSTED_FILTER_NAME_SIZE = 16F
    }
}
