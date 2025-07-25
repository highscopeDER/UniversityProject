package com.example.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.presentation.core.theme.UniversityProjectTheme
import com.example.presentation.screens.compose.home.HomeScreen
import com.google.android.material.internal.EdgeToEdgeUtils.applyEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {

        applyEdgeToEdge(window, true)
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            UniversityProjectTheme {
                HomeScreen {

                }
            }
        }

    }


}