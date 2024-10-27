package com.example.universityproject.screens.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.universityproject.R
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.route.RouteBuilder
import com.example.universityproject.model.Floors
import com.example.universityproject.screens.activities.RouteViewer
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetFragment
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface

class MainFragment(private val routeViewer: RouteViewer) : Fragment(), MainBottomSheetInterface{

    private lateinit var binding: FragmentMainBinding

    override var startPoint: String? = null
    override var endPoint: String? = null

    private lateinit var fmanager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        fmanager = requireActivity().supportFragmentManager
        binding.touchImageView.fragment = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startPoint = null
        endPoint = null
    }

    override fun updateUI() {
        binding.pickStartButton.text = startPoint
        binding.pickEndButton.text = endPoint

        if (startPoint != null && endPoint != null) {

            routeViewer.showRouteFragment(
                RouteViewerFragment(
                    routeViewer,
                    RouteBuilder.buildRoute(startPoint!!, endPoint!!)
                )
            )

            Log.d("TAG", "WTFFF")

        }
    }

    private fun clearInput(){
        startPoint = null
        endPoint = null
        updateUI()
    }

    @SuppressLint("ResourceType", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchRouteTypeButton.setOnClickListener {
            it.isActivated = !it.isActivated
        }

         binding.touchImageView.apply {
                minZoom = 0.1f
                maxZoom = 5f
                setZoom(0.2f)
            }

        binding.pickStartButton.setOnClickListener {

           if (activity != null) {
               MainBottomSheetFragment(MainBottomSheetInterface.selectionType.START, this)
                   .show(fmanager, "fw")
           }

        }

        binding.pickEndButton.setOnClickListener {
            if (activity != null) {
                MainBottomSheetFragment(MainBottomSheetInterface.selectionType.END, this)
                    .show(fmanager, "wf")
            }
        }

        binding.touchImageView.setOnTouchListener { _, event ->

            if (event.action != MotionEvent.ACTION_MOVE && event.action != MotionEvent.ACTION_UP) {
                Log.d(
                    "TAG",
                    "x, y - ${event.x}, ${event.y}"
                )
                binding.touchImageView.checkClick(event.x, event.y)
            }

            true
        }

        val menuOnClick: View.OnClickListener = View.OnClickListener { showPopupMenu(binding.menuView.dropDownIcon) }

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

    private fun showPopupMenu(anchor: View?) {

        PopupMenu(this.requireContext(), anchor)
                .apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        val floor: Floors =
                        when (item) {
                            this.menu.findItem(R.id.floor1) -> {
                                binding.menuView.textViewFloor.text = "Этаж 1"
                                Floors.FLOOR_1
                            }

                            this.menu.findItem(R.id.floor2) -> {
                                binding.menuView.textViewFloor.text = "Этаж 2"
                                Floors.FLOOR_2
                            }

                            this.menu.findItem(R.id.floor3) -> {
                                binding.menuView.textViewFloor.text = "Этаж 3"
                                Floors.FLOOR_3
                            }

                            this.menu.findItem(R.id.floor4) -> {
                                binding.menuView.textViewFloor.text = "Этаж 4"
                                Floors.FLOOR_4
                            }

                            this.menu.findItem(R.id.floor5) -> {
                                binding.menuView.textViewFloor.text = "Этаж 5"
                                Floors.FLOOR_5
                            }

                            else -> {
                                Floors.FLOOR_1}
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
