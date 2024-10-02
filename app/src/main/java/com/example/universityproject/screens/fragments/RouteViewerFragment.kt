package com.example.universityproject.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.universityproject.databinding.FragmentRouteViewerBinding
import com.example.universityproject.route.RouteStep
import com.example.universityproject.screens.activities.RouteViewer
import java.util.function.UnaryOperator

class RouteViewerFragment (
    private val routeViewer: RouteViewer,
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
        binding.indicator.initDots(stepsCount)
        binding.indicator.setDotSelection(0)
        binding.indicator.invalidate()
        binding.mapView.apply {
            minZoom = 0.1f
            maxZoom = 5f
            setZoom(0.2f)
        }
        binding.mapView.setImageBitmap(routeSteps.first().bitmap)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextStepButton.setOnClickListener {
            moveStep(true)
        }

        binding.backButton.setOnClickListener {
            routeViewer.backToMain()
        }


        binding.indicator.onSelectListener = {
            currentStep = routeSteps[it]
            updateUI()
        }

    }

    private fun updateUI() = binding.mapView.setImageBitmap(currentStep.bitmap)

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