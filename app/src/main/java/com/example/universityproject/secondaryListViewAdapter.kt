package com.example.universityproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityproject.databinding.SecondaryListViewItemBinding

class SecondaryListViewAdapter(private val data: Array<Pair<String, String>>) : RecyclerView.Adapter<SecondaryListViewAdapter.ViewHolder>() {

    private lateinit var binding: SecondaryListViewItemBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var viewHolderBinding: SecondaryListViewItemBinding

        init {

            viewHolderBinding = SecondaryListViewItemBinding.bind(view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = SecondaryListViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewHolderBinding.start.text = data[position].first
        holder.viewHolderBinding.end.text = data[position].second

    }

}