package com.example.universityproject.screens.activities

import com.example.universityproject.screens.fragments.RouteViewerFragment

interface RouteViewer {

    fun showRouteFragment(fragment: RouteViewerFragment)

    fun backToMain()

}