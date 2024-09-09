package com.codesthetic.flexi

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.shared.flexi.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.Payload
import eu.davidea.flexibleadapter.items.IFlexible

/**
 * @author Davide Steduto
 * @since 22/04/2016
 */
class ProgressItem : BaseFlexiView() {
    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
    ): BaseFlexiViewHolder {
        return ProgressViewHolder(view, adapter)
    }

    private var status = ProgressStatus.MORE_TO_LOAD

    fun setStatus(status: ProgressStatus) {
        this.status = status
    }

    override fun equals(other: Any?): Boolean {
        return this === other // The default implementation
    }

    override fun hashCode(): Int {
        return status.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.progress_layout
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<*>>,
        holder: BaseFlexiViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        if (holder is ProgressViewHolder) {
            holder.progressBar.visibility = View.GONE

            if (!adapter.isEndlessScrollEnabled) {
                status =
                    ProgressStatus.DISABLE_ENDLESS
            } else if (payloads.contains(Payload.NO_MORE_LOAD)) {
                status =
                    ProgressStatus.NO_MORE_LOAD
            }

            when (status) {
                ProgressStatus.MORE_TO_LOAD -> {
                    holder.progressBar.visibility = View.VISIBLE
                }
                ProgressStatus.NO_MORE_LOAD -> {
                    holder.progressBar.visibility = View.GONE
                    status = ProgressStatus.MORE_TO_LOAD
                }
                ProgressStatus.DISABLE_ENDLESS -> {
                    holder.progressBar.visibility = View.GONE
                }
                ProgressStatus.ON_CANCEL -> {
                    holder.progressBar.visibility = View.GONE
                    status = ProgressStatus.MORE_TO_LOAD
                }
                ProgressStatus.ON_ERROR -> {
                    holder.progressBar.visibility = View.GONE
                    status = ProgressStatus.MORE_TO_LOAD
                }
            }
        }
    }

    inner class ProgressViewHolder(view: View, adapter: FlexibleAdapter<*>) : BaseFlexiViewHolder(view, adapter) {
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
    }

    enum class ProgressStatus {
        MORE_TO_LOAD, // Default = should have an empty Payload
        DISABLE_ENDLESS, // Endless is disabled because user has set limits
        NO_MORE_LOAD, // Non-empty Payload = Payload.NO_MORE_LOAD
        ON_CANCEL,
        ON_ERROR,
    }
}
