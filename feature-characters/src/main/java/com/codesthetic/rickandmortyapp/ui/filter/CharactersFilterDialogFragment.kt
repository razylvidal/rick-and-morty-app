package com.codesthetic.rickandmortyapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayConfigHelper
import com.codesthetic.engine.core.characterdisplayconfig.domain.CharacterDisplayDataSource
import com.codesthetic.feature_characters.databinding.CharacterFilterDialogFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.utilsandroid.LockedBottomSheetBehavior
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import javax.inject.Inject

/**
 * Created by razylvidal on July 03, 2024
 */
@AndroidEntryPoint
class CharactersFilterDialogFragment : BottomSheetDialogFragment(), FlexibleAdapter.OnItemClickListener {
    private lateinit var binding: CharacterFilterDialogFragmentBinding

    override fun getTheme(): Int = com.google.android.material.R.style.Theme_Design_BottomSheetDialog

    @Inject
    lateinit var characterDisplayConfigHelper: CharacterDisplayConfigHelper

    private lateinit var displayConfig: CharacterDisplayDataSource

    private var onApply: ((CharacterDisplayDataSource) -> Unit?)? = null

    private var onReset: (() -> Unit)? = null

    private var selectedGenderIndex: Int = 0
    private var selectedStatusIndex: Int = 0
    private var gender: List<String> = emptyList()
    private var status: List<String> = emptyList()

    private val genderAdapter by lazy {
        FlexibleAdapter(initGenderAdapter()).apply {
            mItemClickListener = this@CharactersFilterDialogFragment
        }
    }

    private val statusAdapter by lazy {
        FlexibleAdapter(initStatusAdapter()).apply {
            mItemClickListener = this@CharactersFilterDialogFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = CharacterFilterDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            dismissAllowingStateLoss()
            return
        }

        displayConfig = characterDisplayConfigHelper.getDisplayConfig()

        show()
        setUpRecyclerView()
        clickHandlers()
    }

    private fun initGenderAdapter(): List<BaseFlexiView> {
        val items = mutableListOf<BaseFlexiView>()
        for (option in gender) {
            val item = GenderFilterFlexiView(option)
            item.isSelected = option == displayConfig.gender
            items.add(item)
        }

        return items
    }

    private fun initStatusAdapter(): List<BaseFlexiView> {
        val items = mutableListOf<BaseFlexiView>()
        for (option in status) {
            val item = StatusFilterFlexiView(option)
            item.isSelected = option == displayConfig.status
            items.add(item)
        }

        return items
    }

    override fun onItemClick(
        view: View?,
        position: Int,
    ): Boolean {
        return true // TODO()
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
                }
                coordinatorLayout.parent.requestLayout()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvGender.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvGender.setHasFixedSize(true)
        binding.rvGender.adapter = genderAdapter

        binding.rvStatus.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvStatus.setHasFixedSize(true)
        binding.rvStatus.adapter = statusAdapter
    }

    private fun clickHandlers() {
        binding.btnApply.setOnClickListener {
            characterDisplayConfigHelper.save(displayConfig)

            onApply?.invoke(displayConfig)
            dismiss()
        }

        binding.btnReset.setOnClickListener {
            characterDisplayConfigHelper.save(CharacterDisplayDataSource())
            onReset?.invoke()
            dismiss()
        }

        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    fun setGender(gender: List<String>): CharactersFilterDialogFragment {
        this.gender = gender
        return this
    }

    fun setStatus(status: List<String>): CharactersFilterDialogFragment {
        this.status = status
        return this
    }

    fun onApply(callback: ((CharacterDisplayDataSource) -> Unit?)?): CharactersFilterDialogFragment {
        this.onApply = callback
        return this
    }

    fun onReset(callback: (() -> Unit)?): CharactersFilterDialogFragment {
        this.onReset = callback
        return this
    }
}
