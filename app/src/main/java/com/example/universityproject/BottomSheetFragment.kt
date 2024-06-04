package com.example.universityproject

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universityproject.api.API
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.BottomSheetFragmentBinding
import com.example.universityproject.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class BottomSheetFragment(val code: Int, val ins: BottomSheetInterface) : BottomSheetDialogFragment(), MainListViewAdapter.MainListViewAdapterInterface {

    private lateinit var binding: BottomSheetFragmentBinding
    private var mainList: List<String> = listOf()

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

        val secondaryListData: Array<Pair<String, String>> = arrayOf(
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

        binding.secondaryListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SecondaryListViewAdapter(secondaryListData)
        }

         mainList = dbApi.getAllClassRooms().keys.toList()

        binding.mainListView.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            adapter = MainListViewAdapter(mainList, this@BottomSheetFragment)
        }
    }


    override fun onClick(desc: String) {

        if (code == BottomSheetInterface.selectionType.START) ins.startPoint = desc
        else ins.endPoint = desc

        ins.updateUI()

        dismiss()
    }

}