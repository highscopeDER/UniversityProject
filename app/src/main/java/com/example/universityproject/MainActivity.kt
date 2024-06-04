package com.example.universityproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.universityproject.api.API.Companion.dbApi
import com.example.universityproject.databinding.ActivityMainBinding
import com.google.android.material.internal.EdgeToEdgeUtils.applyEdgeToEdge

class MainActivity : AppCompatActivity(){

    private lateinit var fragment: Fragment
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        applyEdgeToEdge(window, true)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbApi.initialize()

        fragment = MainFragment()
        useFragment(fragment)
    }

    private fun useFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            commit()
        }
    }

}