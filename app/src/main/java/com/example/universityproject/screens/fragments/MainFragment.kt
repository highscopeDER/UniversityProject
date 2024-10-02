package com.example.universityproject.screens.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.universityproject.R
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.screens.Floors
import com.example.universityproject.route.RouteBuilder
import com.example.universityproject.screens.activities.RouteViewer
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetFragment
import com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment.MainBottomSheetInterface
import com.example.universityproject.screens.map.MapView
import com.ortiz.touchview.TouchImageView

class MainFragment(private val routeViewer: RouteViewer) : Fragment(), MainBottomSheetInterface {

    private lateinit var binding: FragmentMainBinding

    private var currentFloor: Int = 1

    override var startPoint: String? = null
    override var endPoint: String? = null

    private lateinit var mapView: MapView

    private lateinit var fmanager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        fmanager = requireActivity().supportFragmentManager
        return binding.root
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
        }
    }

    fun clearInput(){
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

         mapView = binding.touchImageView
            .apply {
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


        val menuOnClick: View.OnClickListener = View.OnClickListener { showPopupMenu(binding.menuView.dropDownIcon, binding.root) }

        binding.menuView
            .apply {
                textViewBuilding.setOnClickListener(menuOnClick)
                textViewFloor.setOnClickListener(menuOnClick)
                dropDownIcon.setOnClickListener(menuOnClick)
            }
    }

    private fun showPopupMenu(anchor: View?, view: View) {

        val imgView: TouchImageView = view.findViewById(R.id.touchImageView)
        val floorTextView: TextView = view.findViewById(R.id.textViewFloor)
        val buildingTextView: TextView = view.findViewById(R.id.textViewBuilding)

        PopupMenu(this.requireContext(), anchor)
                .apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        val floorNum: Int =
                        when (item.itemId) {
                            R.id.floor1 -> {
                                imgView.setImageResource(Floors.FLOOR_1.res)
                                floorTextView.text = "Этаж 1"
                                1
                            }

                            R.id.floor2 -> {
                                imgView.setImageResource(Floors.FLOOR_2.res)
                                floorTextView.text = "Этаж 2"
                                2
                            }

                            R.id.floor3 -> {
                                imgView.setImageResource(Floors.FLOOR_3.res)
                                floorTextView.text = "Этаж 3"
                                3
                            }

                            R.id.floor4 -> {
                                imgView.setImageResource(Floors.FLOOR_4.res)
                                floorTextView.text = "Этаж 4"
                                4
                            }

                            R.id.floor5 -> {
                                imgView.setImageResource(Floors.FLOOR_5.res)
                                floorTextView.text = "Этаж 5"
                                5
                            }
                            else -> 0
                        }

                        mapView.updateDrawable()
                        currentFloor = floorNum

                        when(item.groupId) {
                            R.id.buildingMenu -> {
                                buildingTextView.text = "Корпус 8"
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

    companion object {
        @JvmStatic
        fun newInstance(p: RouteViewer) = MainFragment(p)
    }


}
