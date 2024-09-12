package com.codesthetic.rickandmortyapp.ui.location

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
import androidx.recyclerview.widget.RecyclerView
import com.codesthetic.engine.core.characters.domain.Character
import com.codesthetic.engine.core.location.domain.Location
import com.codesthetic.feature.characters.databinding.LocationDialogFragmentBinding
import com.codesthetic.flexi.BaseFlexiView
import com.codesthetic.flexi.ThrottledFlexiItemClickedListener
import com.codesthetic.rickandmortyapp.ui.flexiitem.CharacterFlexiView
import com.codesthetic.utilsandroid.LockedBottomSheetBehavior
import com.codesthetic.utilsandroid.capitalizeFirstChar
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import eu.davidea.flexibleadapter.FlexibleAdapter
import javax.inject.Inject

/**
 * Created by razylvidal on February 05, 2024
 */

@AndroidEntryPoint
class LocationDialogFragment : BottomSheetDialogFragment(), LocationsContracts.View {
    private lateinit var binding: LocationDialogFragmentBinding

    override fun getTheme(): Int = com.google.android.material.R.style.Theme_Design_BottomSheetDialog

    private val adapter by lazy {
        FlexibleAdapter<BaseFlexiView>(emptyList())
    }

    @Inject
    lateinit var presenter: LocationsPresenter

    private var listener: ((Int) -> Unit?)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.mItemClickListener =
            object : ThrottledFlexiItemClickedListener() {
                override fun onSingleClicked(
                    view: View?,
                    position: Int,
                ) {
                    val character = adapter.getItem(position) as? CharacterFlexiView
                    if (character != null) {
                        listener?.invoke(character.character.id)
                        dismiss()
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = LocationDialogFragmentBinding.inflate(layoutInflater)
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

        show()
        setupRecyclerView()

        presenter.onViewReady(this, getLocationId())

        binding.ivCloseBottomsheet.setOnClickListener {
            dismiss()
        }
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
        binding.rvResidents.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvResidents.itemAnimator = DefaultItemAnimator()
        binding.rvResidents.adapter = adapter
        binding.rvResidents.setHasFixedSize(true)
    }

    @SuppressLint("SetTextI18n")
    override fun showLocationDetails(location: Location) {
        binding.tvLocationName.text = location.name
        binding.tvLocationType.text = "${location.type} -"
        binding.tvLocationDimension.text = location.dimension.capitalizeFirstChar()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showResidents(residents: List<Character>) {
        if (residents.isEmpty()) {
            binding.tvEmptyResidentMessage.visibility = View.VISIBLE
            return
        }

        adapter.clear()
        adapter.updateDataSet(residents.map { CharacterFlexiView(it) })
        binding.tvEmptyResidentMessage.visibility = View.GONE
    }

    fun onCharacterClicked(callback: ((Int) -> Unit?)?): LocationDialogFragment {
        listener = callback
        return this
    }

    private fun getLocationId(): Int = requireArguments().getInt(LOCATION_ID_KEY)

    companion object {
        private const val LOCATION_ID_KEY = "location_id_key"

        fun newInstance(locationId: Int): LocationDialogFragment {
            return LocationDialogFragment().apply {
                arguments =
                    bundleOf(
                        LOCATION_ID_KEY to locationId
                    )
            }
        }
    }
}
