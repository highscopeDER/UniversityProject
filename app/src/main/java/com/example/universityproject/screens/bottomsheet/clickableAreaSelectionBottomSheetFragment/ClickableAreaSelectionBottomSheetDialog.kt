package com.example.universityproject.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.universityproject.R
import com.example.universityproject.databinding.ClickableAreaSelectionBottomSheetFragmentBinding
import com.example.universityproject.model.RoutePoint
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.State
import com.google.android.material.bottomsheet.BottomSheetDialog

class ClickableAreaSelectionBottomSheetDialog(
    context: Context,
    private val pathEdgesSetter: Pair<(item: RoutePoint) -> Unit, (item: RoutePoint) -> Unit>,
    private val str: RoutePoint,
    private val onDismiss: () -> Unit
) :  BottomSheetDialog(context){

    private lateinit var binding: ClickableAreaSelectionBottomSheetFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ClickableAreaSelectionBottomSheetFragmentBinding.bind(
            View.inflate(context, R.layout.clickable_area_selection_bottom_sheet_fragment, null)
        )

        setContentView(binding.root)
        binding.tv.text = str.label

        behavior.peekHeight = 1000
        behavior.state = STATE_EXPANDED

        binding.toStart.setOnClickListener {
            pathEdgesSetter.first(str)
            dismiss()
        }

        binding.toEnd.setOnClickListener {
            pathEdgesSetter.second(str)
            dismiss()
        }
    }

    override fun dismiss() {
        onDismiss()
        super.dismiss()

    }

}