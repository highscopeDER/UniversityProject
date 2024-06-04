package com.example.universityproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.FragmentMainBinding
import com.example.universityproject.dijkstra.Graph
import com.example.universityproject.dijkstra.useDijkstra
import com.ortiz.touchview.TouchImageView
import kotlin.math.pow
import kotlin.math.sqrt

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class MainFragment( ) : Fragment(), BottomSheetInterface {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentMainBinding

    private var param1: String? = null
    private var param2: String? = null
    private var currentFloor: Int = 1

    override var startPoint: String? = null
    override var endPoint: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var mapView: MapView
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun updateUI() {
        binding.pickStartButton.text = startPoint
        binding.pickEndButton.text = endPoint

        if (startPoint != null && endPoint != null) drawRoute()

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
               val fmanager = requireActivity().supportFragmentManager
               BottomSheetFragment(BottomSheetInterface.selectionType.START, this)
                   .show(fmanager, "fw")
           }
           
        }

        binding.pickEndButton.setOnClickListener {
            if (activity != null) {
                val fmanager = requireActivity().supportFragmentManager
                BottomSheetFragment(BottomSheetInterface.selectionType.END, this)
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


    private fun drawRoute() {
        val data: Map<String, List<String>> = dbApi.getAlgorithmData()
        val coordinates: Map<String, Pair<Float, Float>> = dbApi.getAllPointsCoordinates()
        val pointsList = dbApi.getAllClassRooms()

        val vertices: Set<String> = data.keys
        val edges: MutableMap<String, Set<String>> = mutableMapOf()
        val weights: MutableMap<Pair<String, String>, Int> = mutableMapOf()

        data.forEach {
            edges[it.key] = it.value.toSet()

            val firstPoint = coordinates[it.key]

            it.value.forEach { item ->
                val secondPoint = coordinates[item]
                val a = Pair(it.key, item)

                val b = sqrt(
                    (secondPoint!!.first - firstPoint!!.first).pow(2) + (secondPoint.second - firstPoint.second).pow(2)
                ).toInt()

                weights[a] = b
            }
        }
        val graph: Graph<String> = Graph(vertices, edges, weights)

        val pathEdges = Pair(pointsList[startPoint]!!, pointsList[endPoint]!!)

        val start = minOf(pathEdges.first, pathEdges.second)
        val end: String = maxOf(pathEdges.first, pathEdges.second)

        println("start point: $start, end point: $end")

        val path: List<String> = useDijkstra(graph, start, end)

        println("FIRST $path")

        val pathCoordinates: MutableList< Pair<Float, Float> > = mutableListOf()

        path.forEach {
            coordinates[it]?.let { it1 ->
                pathCoordinates.add(
                    it1
                )
            }
        }
//
//        val leftLadder = Pair("h3030", "h4013")
//        val rightLadder = Pair("h3065", "h4033")
//
//
//        val config: Pair<Int?, Int?> = if (start[1] != end[1]) {
//            if (currentFloor == 3) {
//                if (path.contains(leftLadder.first)) Pair(path.indexOf(leftLadder.first), 4)
//                else Pair(path.indexOf(rightLadder.first), 4)
//            } else {
//                if (path.contains(leftLadder.second)) Pair(path.indexOf(leftLadder.second), 3)
//                else Pair(path.indexOf(rightLadder.second), 3)
//            }
//        } else Pair(null, null)
//
//        println("SECOND $path")

        mapView.drawPathByListOfPoints(pathCoordinates, null, null)
    }



    private fun showPopupMenu(anchor: View?, view: View) {

        val imgView: TouchImageView = view.findViewById(R.id.touchImageView)
        val floorTextView: TextView = view.findViewById(R.id.textViewFloor)
        val buildingTextView: TextView = view.findViewById(R.id.textViewBuilding)

        val popMenu: PopupMenu = PopupMenu(this.requireContext(), anchor)
                .apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        val floorNum: Int =
                        when (item.itemId) {
                            R.id.floor1 -> {
                                imgView.setImageResource(R.drawable.floor1)
                                floorTextView.text = "Этаж 1"
                                1
                            }

                            R.id.floor2 -> {
                                imgView.setImageResource(R.drawable.floor2)
                                floorTextView.text = "Этаж 2"
                                2
                            }

                            R.id.floor3 -> {
                                imgView.setImageResource(R.drawable.floor3)
                                floorTextView.text = "Этаж 3"
                                3
                            }

                            R.id.floor4 -> {
                                imgView.setImageResource(R.drawable.floor4)
                                floorTextView.text = "Этаж 4"
                                4
                            }

                            R.id.floor5 -> {
                                imgView.setImageResource(R.drawable.floor5)
                                floorTextView.text = "Этаж 5"
                                5
                            }
                            else -> 0
                        }

                        mapView.updateDrawable(floorNum)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mainFragment.
         */
        //val floor0Drawable: Drawable? = AppCompatResources.getDrawable(fragment.requireContext(), R.drawable.floor0)

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
