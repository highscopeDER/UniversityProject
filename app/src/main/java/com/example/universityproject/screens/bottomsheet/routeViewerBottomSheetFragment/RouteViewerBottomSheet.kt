package com.example.universityproject.screens.bottomsheet.routeViewerBottomSheetFragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.universityproject.databinding.FragmentRouteViewerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RouteViewerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRouteViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteViewerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun update(){

    }



}