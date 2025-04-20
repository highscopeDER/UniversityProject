package com.example.universityproject.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.models.FloorsEnum
import com.example.universityproject.R
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.screens.viewModels.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment(){

    private val viewModel by viewModels<MainFragmentViewModel>({requireActivity()})

    private lateinit var binding: FragmentMainBinding

    private val fManager: FragmentManager by lazy { requireActivity().supportFragmentManager }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        subscribeOnViewModel()

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pickStartButton.setOnClickListener {
          viewModel.showBottomSheet(fManager, true)
       }

        binding.pickEndButton.setOnClickListener {
            viewModel.showBottomSheet(fManager, false)
        }

        binding.touchImageView.setOnTouchListener { _, event ->

            if (event.classification != MotionEvent.CLASSIFICATION_PINCH) when (event.action) {

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    binding.touchImageView.checkClick(event.x, event.y)
                    binding.touchImageView.performClick()
                }

            }

            true
        }



        setMenuViewOnClickListener { viewModel.showPopUpMenu() }

        binding.errorButton.setOnClickListener {
            viewModel.loadFloors()
        }

    }

    private fun showPopupMenu(anchor: View?) {

        PopupMenu(this.requireContext(), anchor)
                .apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        val floorNum: FloorsEnum = when (item) {

                            this.menu.findItem(R.id.h1) -> FloorsEnum.H1
                            this.menu.findItem(R.id.h2) -> FloorsEnum.H2
                            this.menu.findItem(R.id.h3) -> FloorsEnum.H3
                            this.menu.findItem(R.id.h4) -> FloorsEnum.H4
                            this.menu.findItem(R.id.h5) -> FloorsEnum.H5

                            this.menu.findItem(R.id.a0) -> FloorsEnum.A0
                            this.menu.findItem(R.id.a1) -> FloorsEnum.A1
                            this.menu.findItem(R.id.a2) -> FloorsEnum.A2
                            this.menu.findItem(R.id.a3) -> FloorsEnum.A3
                            this.menu.findItem(R.id.a4) -> FloorsEnum.A4
                            this.menu.findItem(R.id.a5) -> FloorsEnum.A5
                            this.menu.findItem(R.id.a6) -> FloorsEnum.A6

                            this.menu.findItem(R.id.j1) -> FloorsEnum.J1
                            this.menu.findItem(R.id.j2) -> FloorsEnum.J2
                            this.menu.findItem(R.id.j3) -> FloorsEnum.J3
                            this.menu.findItem(R.id.j4) -> FloorsEnum.J4

                            else -> {FloorsEnum.A0}
                        }

                        viewModel.setFloor(floorNum)

                        true
                    }
                    show()
                }
    }

    private fun subscribeOnViewModel() {

        binding.touchImageView.pathEdgesSetter = viewModel.pathEdgesSetter



        viewModel.navigateToRouteViewer.subscribe {
            findNavController().navigate(MainFragmentDirections.mainToRouteViewer(route = it.encodeToString()))
        }

        viewModel.startPoint.subscribe {
            binding.pickStartButton.text = it?.label
        }


        viewModel.endPoint.subscribe {
            binding.pickEndButton.text = it?.label
        }

        viewModel.currentFloor.subscribe {
            when(it) {

                MainFragmentViewModel.FloorState.Empty ->  {
                    viewModel.loadFloors()
                }

                MainFragmentViewModel.FloorState.Loading -> showProgressBar()

                is MainFragmentViewModel.FloorState.Error -> {
                    binding.errorTextView.text = it.msg
                    showError()
                }

                is MainFragmentViewModel.FloorState.Showing -> {
                    binding.touchImageView.updateFloor(it.floor)
                    showMap()
                }
            }
        }

        viewModel.popUpMenu.subscribe {
            showPopupMenu(binding.menuView.dropDownIcon)
        }

        viewModel.currentFloorText.subscribe {
            binding.menuView.textViewFloor.text = it
        }

        viewModel.currentBuildingText.subscribe {
            binding.menuView.textViewBuilding.text = it
        }


    }

    private fun showProgressBar() {
        binding.progressBar.visibility = VISIBLE
        binding.mainGroup.visibility = INVISIBLE
        binding.errorGroup.visibility = GONE
    }

    private fun showMap() {
        binding.progressBar.visibility = GONE
        binding.mainGroup.visibility = VISIBLE
        binding.errorGroup.visibility = GONE
    }

    private fun showError() {
        binding.progressBar.visibility = GONE
        binding.mainGroup.visibility = INVISIBLE
        binding.errorGroup.visibility = VISIBLE
    }

    private fun setMenuViewOnClickListener(onClick: () -> Unit) {
        binding.menuView
            .apply {
                textViewBuilding.setOnClickListener{ onClick() }
                textViewFloor.setOnClickListener{ onClick() }
                dropDownIcon.setOnClickListener{ onClick() }
            }
    }



}

