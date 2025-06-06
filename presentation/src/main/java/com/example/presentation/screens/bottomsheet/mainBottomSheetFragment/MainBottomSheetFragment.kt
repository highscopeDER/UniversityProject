package com.example.presentation.screens.bottomsheet.mainBottomSheetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.RoutePoint
import com.example.presentation.databinding.BottomSheetFragmentBinding
import com.example.presentation.screens.viewModels.MainFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainBottomSheetFragment(
    private val start: Boolean,
    private val classrooms: List<RoutePoint>
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private var mainList: List<RoutePoint> = listOf()

    private val viewModel by viewModels<MainFragmentViewModel>({ requireActivity() })

    private val mainListOnClick: (param: RoutePoint) -> Unit = {
        if (start) viewModel.setStartPoint(it) else viewModel.setEndPoint(it)
        dismiss()
    }

    private val secondListOnClick: (param: Pair<RoutePoint, RoutePoint>) -> Unit = {
        viewModel.apply {
            setStartPoint(it.first)
            setEndPoint(it.second)
        }
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val secondaryListMap: List<Pair<RoutePoint, RoutePoint>> = listOf(
            RoutePoint("h3039", "8304") to RoutePoint(
                "h3031",
                "8308"
            ),
            RoutePoint("h3049", "8301") to RoutePoint(
                "h3031",
                "8308"
            ),
            RoutePoint("h4025", "8408") to RoutePoint(
                "h3017",
                "8316"
            ),
            RoutePoint("h1035", "8108") to RoutePoint(
                "h5003",
                "8501"
            )
        )

        val secondListAdapter =
            com.example.presentation.screens.bottomsheet.mainBottomSheetFragment.SecondListViewAdapter(
                secondaryListMap,
                secondListOnClick
            )

        binding.secondaryListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = secondListAdapter
        }

        //mainList = dbApi.getAllClassRooms()
        val mainListAdapter =
            com.example.presentation.screens.bottomsheet.mainBottomSheetFragment.FirstListViewAdapter(
                classrooms,
                mainListOnClick
            )

        binding.mainListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = mainListAdapter
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                mainListAdapter.filterData(newText)
                secondListAdapter.filterData(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })


    }

}