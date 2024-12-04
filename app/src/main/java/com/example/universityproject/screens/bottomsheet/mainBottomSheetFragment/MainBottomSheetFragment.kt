package com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import androidx.annotation.DimenRes
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.BottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainBottomSheetFragment(
    private val onItemSelected: (item: String) -> Unit,
    private val onRouteSelected: (start: String, end: String) -> Unit,
) : BottomSheetDialogFragment() {



    private lateinit var binding: BottomSheetFragmentBinding
    private var mainList: List<String> = listOf()

    private val mainListOnClick: (param: String) -> Unit = {
        onItemSelected(it)
        dismiss()
    }

    private val secondListOnClick: (param: Pair<String, String>) -> Unit = {
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

        val secondListAdapter = SecondListViewAdapter(secondaryListData, secondListOnClick)

        binding.secondaryListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = secondListAdapter
        }

        mainList = dbApi.getAllClassRooms().keys.toList()
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