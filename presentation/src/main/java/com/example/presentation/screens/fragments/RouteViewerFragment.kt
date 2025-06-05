package com.example.presentation.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.Route
import com.example.presentation.databinding.FragmentRouteViewerBinding
import com.example.presentation.screens.viewModels.RouteViewerViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED

class RouteViewerFragment(): BaseFragment() {

    private val args by navArgs<RouteViewerFragmentArgs>()

    private val viewModel by viewModels<RouteViewerViewModel>()

    private lateinit var binding: FragmentRouteViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteViewerBinding.inflate(inflater)

        viewModel.setRoute(
           Route.decodeFromString(args.route)
        )


        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 300
            this.state = STATE_EXPANDED
        }

        subscribeToViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextStepButton.setOnClickListener {
            viewModel.nextPart()
        }

        binding.previousStepButton.setOnClickListener {
            viewModel.previousPart()
        }

        binding.backButton.setOnClickListener {
            this.findNavController().popBackStack()
        }

        binding.indicator.onSelectListener = {
            viewModel.part(it)
        }

    }

    private fun subscribeToViewModel(){

        viewModel.partsCount.subscribe {
            setupIndicator(it)
            if (it == 1) {
                binding.previousStepButton.visibility = GONE
                binding.nextStepButton.visibility = GONE
            }
        }

        viewModel.currentPart.subscribe {
            when(it) {
                RouteViewerViewModel.StepState.Empty -> {}
                RouteViewerViewModel.StepState.Loading -> {}
                is RouteViewerViewModel.StepState.Showing -> {
                    binding.routeTitle.text = it.part.sourceInfo
                    binding.routeDescription.text = it.part.description
                    binding.mapView.updateFloor(it.part.source, it.part.pathPoints)
                    binding.indicator.setDotSelection(
                        viewModel.getCurrentPartIndex(it.part)
                    )
                }
            }
        }

    }

    private fun setupIndicator(count: Int) {
        binding.indicator.initDots(count)
        binding.indicator.setDotSelection(0)
        binding.indicator.invalidate()
    }


}