package com.example.presentation.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.presentation.R
import com.example.presentation.databinding.ClickableAreaSelectionBottomSheetFragmentBinding
import com.example.domain.models.RoutePoint
import com.example.presentation.screens.map.PathEdgesSetter
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog

class ClickableAreaSelectionBottomSheetDialog(
    context: Context,
    private val pathEdgesSetter: PathEdgesSetter,
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