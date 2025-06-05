package com.example.presentation.screens.bottomsheet.mainBottomSheetFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.MainListViewItemBinding
import com.example.domain.models.RoutePoint


class FirstListViewAdapter(
    private val data: List<RoutePoint>,
    val itemOnClick: (selection: RoutePoint) -> Unit
) : RecyclerView.Adapter<FirstListViewAdapter.ViewHolder>() {

    private var actualData = data

    private lateinit var binding: MainListViewItemBinding

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        var viewHolderBinding: MainListViewItemBinding
        lateinit var item: RoutePoint

        init {
            viewHolderBinding = MainListViewItemBinding.bind(view)
            viewHolderBinding.root.setOnClickListener {

                itemOnClick(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = MainListViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewHolderBinding.textView.text = actualData[position].label
        holder.item = actualData[position]
    }

    override fun getItemCount(): Int = actualData.size

    fun filterData(query: String?) {
        if (query != null){
            actualData = data.filter {
                it.label.lowercase().contains(query)
            }
        }
        notifyDataSetChanged()
    }

}
