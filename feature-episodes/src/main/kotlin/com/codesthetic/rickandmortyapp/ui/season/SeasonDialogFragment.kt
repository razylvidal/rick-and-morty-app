package com.codesthetic.rickandmortyapp.ui.season

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesthetic.feature.episodes.databinding.SeasonBottomSheetDialogBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.flexi.ThrottledFlexiItemClickedListener
import com.codesthetic.utilsandroid.LockedBottomSheetBehavior
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.davidea.flexibleadapter.FlexibleAdapter

class SeasonDialogFragment : BottomSheetDialogFragment() {
    private var currentSeason = 1

    private var selectedSeason = 1

    private val binding by lazy {
        SeasonBottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FlexibleAdapter(initFlexiView())
    }

    private var onApply: ((Int) -> Unit?)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentSeason = getCurrentSeason()
        selectedSeason = currentSeason

        adapter.mItemClickListener =
            object : ThrottledFlexiItemClickedListener() {
                override fun onSingleClicked(
                    view: View?,
                    position: Int,
                ) {
                    val item = adapter.getItem(position) as? SeasonFlexiView
                    if (item != null) {
                        val prevSeason = selectedSeason.dec()
                        val old = adapter.getItem(prevSeason) as? SeasonFlexiView
                        old?.isSelected = false
                        adapter.notifyItemChanged(prevSeason)

                        item.isSelected = true
                        adapter.notifyItemChanged(position)
                        selectedSeason = item.season
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            dismissAllowingStateLoss()
            return
        }

        show()
        setupRecyclerView()

        binding.tvApply.setOnClickListener {
            onApply?.invoke(selectedSeason)
            currentSeason = selectedSeason
            dismiss()
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    private fun initFlexiView(): List<BaseFlexiView> {
        val items = mutableListOf<BaseFlexiView>()
        val seasons = getSeasons()
        for (season in seasons) {
            val item = SeasonFlexiView(season)
            item.isSelected = season == currentSeason
            items.add(item)
        }

        return items
    }

    private fun show() {
        dialog?.setOnShowListener {
            runCatching {
                val d = dialog
                val bottomSheet: FrameLayout? = d?.findViewById(design_bottom_sheet)
                val coordinatorLayout = bottomSheet!!.parent as CoordinatorLayout
                val bottomSheetBehavior = LockedBottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.apply {
                    isHideable = false
                    state = BottomSheetBehavior.STATE_EXPANDED
                    isCancelable = false
                }
                coordinatorLayout.parent.requestLayout()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvSeason.layoutManager = LinearLayoutManager(context)
        binding.rvSeason.itemAnimator = DefaultItemAnimator()
        binding.rvSeason.adapter = adapter
    }

    fun onApply(callback: (Int) -> Unit): SeasonDialogFragment {
        this.onApply = callback
        return this
    }

    private fun getCurrentSeason(): Int = requireArguments().getInt(SEASON_KEY)

    private fun getSeasons(): List<Int> = requireArguments().getIntegerArrayList(SEASONS)!!

    companion object {
        private const val SEASON_KEY = "season_key"
        private const val SEASONS = "seasons"

        fun newInstance(
            currentSeason: Int,
            seasons: List<Int>,
        ): SeasonDialogFragment {
            Log.e("season", "$currentSeason")
            return SeasonDialogFragment().apply {
                val listOfSeasons = ArrayList<Int>()
                listOfSeasons.addAll(seasons)
                arguments =
                    bundleOf(
                        SEASON_KEY to currentSeason,
                        SEASONS to listOfSeasons
                    )
            }
        }
    }
}
