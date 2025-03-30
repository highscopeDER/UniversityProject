package com.example.universityproject.screens.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseFragment() : Fragment() {

    fun <T> Flow<T>.subscribe(listen: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@subscribe.collectLatest {  it: T ->
                    listen(it)
                }
            }
        }
    }

}