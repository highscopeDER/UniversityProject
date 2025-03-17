package com.example.universityproject.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.universityproject.R
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.model.floors.Floors
import com.example.universityproject.model.RoutePoint
import com.example.universityproject.route.RouteBuilder
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetFragment

class MainFragment(
    private val enterRouteViewer: (RouteViewerFragment) -> Unit,
    private val exitRouteViewer: () -> Unit
) : Fragment(){

    private lateinit var binding: FragmentMainBinding

    private var startPoint: RoutePoint? = null
    private var endPoint: RoutePoint? = null

    private val startPointSetter: (point: RoutePoint) -> Unit = {
        startPoint = it
        updateUI()
    }

    private val endPointSetter: (point: RoutePoint) -> Unit = {
        endPoint = it
        updateUI()
    }

    private val routeSetter: (start: RoutePoint, end: RoutePoint) -> Unit = { s, e ->
        startPoint = s
        endPoint = e
        updateUI()
    }

    private lateinit var fmanager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        fmanager = requireActivity().supportFragmentManager
        binding.touchImageView.pathEdgesSetter = Pair(startPointSetter, endPointSetter)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchRouteTypeButton.setOnClickListener {
            it.isActivated = !it.isActivated
        }

        while(Floors.floors.isEmpty()) {

        }

         binding.touchImageView.apply {
             minZoom = 0.1f
             maxZoom = 5f
             setZoom(0.2f)

             updateFloor(Floors.floors[1]!!)
            }

        binding.pickStartButton.setOnClickListener {
           showBottomSheet(startPointSetter)

        }

        binding.pickEndButton.setOnClickListener {
            showBottomSheet(endPointSetter)
        }


        binding.touchImageView.setOnTouchListener { _, event ->

            if (event.action != MotionEvent.ACTION_MOVE && event.action != MotionEvent.ACTION_UP) {
                binding.touchImageView.checkClick(event.x, event.y)
                binding.touchImageView.performClick()
            }

            true
        }

        val menuOnClick: View.OnClickListener = View.OnClickListener {
            showPopupMenu(binding.menuView.dropDownIcon)
        }

        binding.menuView
            .apply {
                textViewBuilding.setOnClickListener(menuOnClick)
                textViewFloor.setOnClickListener(menuOnClick)
                dropDownIcon.setOnClickListener(menuOnClick)
            }
    }

    override fun onPause() {
        super.onPause()
        clearInput()
    }

    override fun onResume() {
        super.onResume()
        binding.touchImageView.updateFloor(Floors.floors[1]!!)
        println("fragment resume")
    }


    private fun updateUI() {
        binding.pickStartButton.text = startPoint?.label
        binding.pickEndButton.text = endPoint?.label

        if (startPoint != null && endPoint != null) {
            enterRouteViewer(
                RouteViewerFragment(
                    exitRouteViewer,
                    RouteBuilder.buildRoute(startPoint!!, endPoint!!)
                )
            )
        }
    }

    private fun clearInput(){
        startPoint = null
        endPoint = null
        updateUI()
    }


    private fun showBottomSheet(pointSetter: (item: RoutePoint) -> Unit) {
        if (activity != null) {
            MainBottomSheetFragment(
                onItemSelected = pointSetter,
                onRouteSelected = routeSetter
            )
                .show(fmanager, null)
        }
    }

    private fun showPopupMenu(anchor: View?) {

        PopupMenu(this.requireContext(), anchor)
                .apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        val floor: Floors =
                        when (item) {
                            this.menu.findItem(R.id.floor1) -> {
                                binding.menuView.textViewFloor.text = "Этаж 1"
                                Floors.floors[1]!!
                            }

                            this.menu.findItem(R.id.floor2) -> {
                                binding.menuView.textViewFloor.text = "Этаж 2"
                                Floors.floors[2]!!
                            }

                            this.menu.findItem(R.id.floor3) -> {
                                binding.menuView.textViewFloor.text = "Этаж 3"
                                Floors.floors[3]!!
                            }

                            this.menu.findItem(R.id.floor4) -> {
                                binding.menuView.textViewFloor.text = "Этаж 4"
                                Floors.floors[4]!!
                            }

                            this.menu.findItem(R.id.floor5) -> {
                                binding.menuView.textViewFloor.text = "Этаж 5"
                                Floors.floors[5]!!
                            }

                            else -> {
                                Floors.floors[1]!!
                            }
                        }

                        binding.touchImageView.updateFloor(floor)

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

}
