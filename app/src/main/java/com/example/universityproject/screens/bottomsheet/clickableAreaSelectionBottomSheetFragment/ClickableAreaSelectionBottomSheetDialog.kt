package com.example.universityproject.screens.bottomsheet.clickableAreaSelectionBottomSheetFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.universityproject.R
import com.example.universityproject.databinding.ClickableAreaSelectionBottomSheetFragmentBinding
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface
import com.example.universityproject.screens.fragments.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ClickableAreaSelectionBottomSheetDialog(
    context: Context,
    private val fragment: MainBottomSheetInterface,
    private val str: String,
    private val onDismiss: () -> Unit
) :  BottomSheetDialog(context){

    private lateinit var binding: ClickableAreaSelectionBottomSheetFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ClickableAreaSelectionBottomSheetFragmentBinding.bind(
            View.inflate(context, R.layout.clickable_area_selection_bottom_sheet_fragment, null)
        )

        setContentView(binding.root)
        binding.tv.text = str

        binding.toStart.setOnClickListener {
            fragment.startPoint = str
            dismiss()
        }

        binding.toEnd.setOnClickListener {
            fragment.endPoint = str
            dismiss()
        }
    }

    override fun dismiss() {
        onDismiss()
        fragment.updateUI()
        super.dismiss()

    }

}