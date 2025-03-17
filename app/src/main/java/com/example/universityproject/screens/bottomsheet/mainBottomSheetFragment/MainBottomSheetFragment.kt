package com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.BottomSheetFragmentBinding
import com.example.universityproject.model.RoutePoint
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainBottomSheetFragment(
    private val onItemSelected: (item: RoutePoint) -> Unit,
    private val onRouteSelected: (start: RoutePoint, end: RoutePoint) -> Unit,
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private var mainList: List<RoutePoint> = listOf()

    private val mainListOnClick: (param: RoutePoint) -> Unit = {
        onItemSelected(it)
        dismiss()
    }

    private val secondListOnClick: (param: Pair<RoutePoint, RoutePoint>) -> Unit = {
        onRouteSelected(it.first, it.second)
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

        val secondaryListData: List<Pair<String, String>> = listOf(
            Pair("8304", "8308"),
            Pair("8301", "8308"),
            Pair("8408", "8316"),
            Pair("8104", "8208"),
            Pair("8501", "8318"),
            Pair("8108", "8116"),
            Pair("8104", "8208"),
            Pair("8501", "8318"),
            Pair("8108", "8116")
        )

        val secondaryListMap: List<Pair<RoutePoint, RoutePoint>> = listOf(
            RoutePoint("h3039", "8304") to RoutePoint("h3031", "8308"),
            RoutePoint("h3049", "8301") to RoutePoint("h3031", "8308"),
            RoutePoint("h4025", "8408") to RoutePoint("h3017", "8316"),
            RoutePoint("h1035", "8108") to RoutePoint("h5003", "8501")
        )

        val secondListAdapter = SecondListViewAdapter(secondaryListMap, secondListOnClick)

        binding.secondaryListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = secondListAdapter
        }

        mainList = dbApi.getAllClassRooms()
        val mainListAdapter = FirstListViewAdapter(mainList, mainListOnClick)

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