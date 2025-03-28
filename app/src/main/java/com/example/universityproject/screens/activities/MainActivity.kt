package com.example.universityproject.screens.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.ActivityMainBinding
import com.example.universityproject.model.floors.Floors
import com.example.universityproject.route.RouteBuilder
import com.example.universityproject.screens.fragments.MainFragment
import com.example.universityproject.screens.fragments.RouteViewerFragment
import com.google.android.material.internal.EdgeToEdgeUtils.applyEdgeToEdge

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fmanager = supportFragmentManager

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        dbApi.initialize()
        applyEdgeToEdge(window, true)
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.viewTreeObserver.addOnPreDrawListener (
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if(Floors.floors.isNotEmpty()) {
                        binding.root.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
        println("activity started")
        RouteBuilder.resources = resources
        fmanager.beginTransaction().apply {
            replace(
                binding.fragmentContainer.id,
                MainFragment(
                    enterRouteViewer = {f: RouteViewerFragment -> showRouteFragment(f)},
                    exitRouteViewer = {backToMain()}
                ),
                null
            )
            setReorderingAllowed(true)
            addToBackStack(null)

            commit()
        }

       onBackPressedDispatcher.addCallback(this) {
           if(fmanager.backStackEntryCount > 1) {
               backToMain()
           }
       }

    }

    private fun showRouteFragment(fragment: RouteViewerFragment) {
        fmanager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment, null)
            .addToBackStack("main")
            .commit()
    }

    private fun backToMain() {
        fmanager.popBackStack()
    }

}