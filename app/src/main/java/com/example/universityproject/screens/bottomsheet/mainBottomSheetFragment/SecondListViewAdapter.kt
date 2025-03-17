package com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityproject.databinding.SecondaryListViewItemBinding
import com.example.universityproject.model.RoutePoint

class SecondListViewAdapter(
    private val data: List<Pair<RoutePoint, RoutePoint>>,
    val itemOnClick: (Pair<RoutePoint, RoutePoint>) -> Unit
)
    : RecyclerView.Adapter<SecondListViewAdapter.ViewHolder>() {

    private lateinit var binding: SecondaryListViewItemBinding
    
    private var actualData = data

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var viewHolderBinding: SecondaryListViewItemBinding
        lateinit var items: Pair<RoutePoint, RoutePoint>

        init {
            viewHolderBinding = SecondaryListViewItemBinding.bind(view)
            viewHolderBinding.root.setOnClickListener{
                itemOnClick(
                   items
                )

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = SecondaryListViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = actualData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewHolderBinding.start.text = actualData[position].first.label
        holder.viewHolderBinding.end.text = actualData[position].second.label
        holder.items = Pair(actualData[position].first, actualData[position].second)

    }

    fun filterData(query: String?) {
        if (query != null) {
            actualData = data.filter {
                it.first.label.contains(query) or it.second.label.contains(query)
            }
        }
        notifyDataSetChanged()
    }

}