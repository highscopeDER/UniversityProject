package com.example.universityproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityproject.databinding.MainListViewItemBinding

class MainListViewAdapter(private val data: List<String>, val l: MainListViewAdapterInterface) : RecyclerView.Adapter<MainListViewAdapter.ViewHolder>() {

    private lateinit var binding: MainListViewItemBinding

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var viewHolderBinding: MainListViewItemBinding

        init {
            viewHolderBinding = MainListViewItemBinding.bind(view)
            viewHolderBinding.root.setOnClickListener {
                l.onClick(viewHolderBinding.textView.text.toString())
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewAdapter.ViewHolder {
        binding = MainListViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewHolderBinding.textView.text = data[position]
    }

    override fun getItemCount(): Int = data.size

    interface MainListViewAdapterInterface{
        fun onClick(desc: String)
    }

}
