package com.example.universityproject.screens.bottomsheet.routeViewerBottomSheetFragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import com.example.universityproject.databinding.RouteViewerBottomSheetFragmentBinding
import com.example.universityproject.route.RouteStep
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RouteViewerBottomSheet(
    private val showStep: (b: Bitmap) -> Unit,
    private val routeSteps: List<RouteStep>,
) : BottomSheetDialogFragment() {

    private val stepsCount: Int = routeSteps.count()
    private var currentStep: RouteStep = routeSteps.first()


    private lateinit var binding: RouteViewerBottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        dialog?.window?.clearFlags(FLAG_DIM_BEHIND)
        binding = RouteViewerBottomSheetFragmentBinding.inflate(inflater)

        if (stepsCount == 1) {
            binding.previousStepButton.visibility = View.GONE
            binding.nextStepButton.visibility = View.GONE
        }

        binding.indicator.initDots(stepsCount)
        binding.indicator.setDotSelection(0)
        binding.indicator.invalidate()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextStepButton.setOnClickListener {
            moveStep(true)
        }

        binding.previousStepButton.setOnClickListener {
            moveStep(false)
        }

        binding.indicator.onSelectListener = {
            currentStep = routeSteps[it]
            showStep(currentStep.bitmap)
        }

        val bh = BottomSheetBehavior.from(binding.root)
        bh.apply {

        }

    }



    private fun moveStep(forward: Boolean) {
        var currentIndex = routeSteps.indexOf(currentStep)
        val endOfDirectionIndex = if (forward) routeSteps.lastIndex else 0

        currentStep = if (currentIndex != endOfDirectionIndex) {
            if (forward) currentIndex++ else currentIndex--
            routeSteps[currentIndex]
        } else {
            currentStep
        }

        binding.indicator.setDotSelection(currentIndex)

        showStep(currentStep.bitmap)
    }

}