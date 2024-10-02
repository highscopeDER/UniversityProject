package com.example.universityproject.screens.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.ActivityMainBinding
import com.example.universityproject.route.RouteBuilder
import com.example.universityproject.screens.fragments.MainFragment
import com.example.universityproject.screens.fragments.RouteViewerFragment
import com.google.android.material.internal.EdgeToEdgeUtils.applyEdgeToEdge

class MainActivity : AppCompatActivity(), RouteViewer {

    private lateinit var mapViewerFragment: MainFragment
    private lateinit var binding: ActivityMainBinding
    private val fmanager = supportFragmentManager

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        applyEdgeToEdge(window, true)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbApi.initialize()
        RouteBuilder.resources = resources

        mapViewerFragment = MainFragment.newInstance(this)

        useFragment(mapViewerFragment)

    }

    private fun useFragment(fragment: Fragment){
        fmanager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            commit()
        }
    }

    override fun showRouteFragment(fragment: RouteViewerFragment) {
        useFragment(fragment)
    }

    override fun backToMain() {
        useFragment(mapViewerFragment)
        mapViewerFragment.clearInput()
    }


}