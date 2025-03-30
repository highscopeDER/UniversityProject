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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.domain.models.Route
import com.example.universityproject.R
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.screens.viewModels.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

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

        binding.touchImageView.apply {
             minZoom = 0.1f
             maxZoom = 5f
             setZoom(0.2f)

             //Floors.floors[1]?.let { updateFloor(it) }
        }

        binding.pickStartButton.setOnClickListener {
          viewModel.showBottomSheet(fManager, true)
       }

        binding.pickEndButton.setOnClickListener {
            viewModel.showBottomSheet(fManager, false)
        }

        binding.touchImageView.setOnTouchListener { _, event ->

            if (event.action != MotionEvent.ACTION_MOVE && event.action != MotionEvent.ACTION_UP) {
                binding.touchImageView.checkClick(event.x, event.y)
                binding.touchImageView.performClick()
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
                        val floorNum: Int =
                        when (item) {
                            this.menu.findItem(R.id.floor1) -> 1
                            this.menu.findItem(R.id.floor2) -> 2
                            this.menu.findItem(R.id.floor3) -> 3
                            this.menu.findItem(R.id.floor4) -> 4
                            this.menu.findItem(R.id.floor5) -> 5

                            else -> 1
                        }

                        viewModel.setFloor(floorNum, "Этаж $floorNum")

                        when(item.groupId) {
                            R.id.buildingMenu -> {
                                binding.menuView.textViewBuilding.text = "Корпус 8"
                                true
                            }
                            else -> {
                                false
                            }
                        }
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
            //if (it.enum != 0) binding.touchImageView.updateFloor(it)
        }

        viewModel.popUpMenu.subscribe {
            showPopupMenu(binding.menuView.dropDownIcon)
        }

        viewModel.currentFloorText.subscribe {
            binding.menuView.textViewFloor.text = it
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

