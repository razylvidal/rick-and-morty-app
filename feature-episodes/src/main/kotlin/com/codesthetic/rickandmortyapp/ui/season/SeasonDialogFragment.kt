package com.codesthetic.rickandmortyapp.ui.season

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.codesthetic.utilsandroid.LockedBottomSheetBehavior
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import eu.davidea.flexibleadapter.FlexibleAdapter

class SeasonDialogFragment : BottomSheetDialogFragment() {
    private val binding by lazy {
        SeasonBottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
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
            dismiss()
        }

        adapter.updateDataSet(getSeasons().map { SeasonFlexiView(it, 5) })
        adapter.notifyDataSetChanged()
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

    private fun getCurrentSeason(): Int = requireArguments().getInt(SEASON_KEY)

    private fun getSeasons(): List<Int> = listOf(1, 2, 3, 4, 5, 6, 7)

    private fun setupRecyclerView() {
        binding.rvSeason.layoutManager = LinearLayoutManager(context)
        binding.rvSeason.itemAnimator = DefaultItemAnimator()
        binding.rvSeason.adapter = adapter
        binding.rvSeason.setHasFixedSize(true)
    }

    companion object {
        private const val SEASON_KEY = "season_key"
        private const val SEASONS = "seasons"

        fun newInstance(
            currentSeason: Int,
            seasons: List<Int>,
        ): SeasonDialogFragment {
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
