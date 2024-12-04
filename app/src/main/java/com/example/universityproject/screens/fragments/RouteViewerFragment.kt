package com.example.universityproject.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.universityproject.databinding.FragmentRouteViewerBinding
import com.example.universityproject.model.getSpecifiedCoordinates
import com.example.universityproject.model.wtf
import com.example.universityproject.route.RouteStep
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED

class RouteViewerFragment (
    private val closeRouteViewer: () -> Unit,
    private val routeSteps: List<RouteStep>
) : Fragment() {

    private lateinit var binding: FragmentRouteViewerBinding
    private val stepsCount: Int = routeSteps.count()
    private var currentStep: RouteStep = routeSteps.first()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteViewerBinding.inflate(inflater)


        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 300
            this.state = STATE_EXPANDED
        }

        if (stepsCount == 1) {
            binding.previousStepButton.visibility = GONE
            binding.nextStepButton.visibility = GONE
        }

        binding.indicator.initDots(stepsCount)
        binding.indicator.setDotSelection(0)
        binding.indicator.invalidate()
        binding.mapView.apply {
            minZoom = 0.1f
            maxZoom = 1f
            setZoom(0.2f)
        }

//        binding.routeTitle.text = currentStep.floorInfo
//        binding.mapView.setImageBitmap(currentStep.bitmap)
//        currentStep.focus.wtf(
//            currentStep.bitmap.width.toFloat(),
//            currentStep.bitmap.height.toFloat()
//        ) { s: Float, fx: Float, fy: Float -> binding.mapView.setZoom(s, fx, fy) }
        updateUI()

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

        binding.backButton.setOnClickListener {
            closeRouteViewer()
        }


        binding.indicator.onSelectListener = {
            currentStep = routeSteps[it]
            updateUI()
        }

    }

    private fun updateUI() {

        binding.routeTitle.text = currentStep.floorInfo
        binding.routeDescription.text = currentStep.description
        binding.mapView.setImageBitmap(currentStep.bitmap)
        currentStep.focus.wtf(
            currentStep.bitmap.width.toFloat(),
            currentStep.bitmap.height.toFloat()
        ) { s: Float, fx: Float, fy: Float -> binding.mapView.setZoom(s, fx, fy) }
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



        updateUI()
    }

    companion object {

    }
}