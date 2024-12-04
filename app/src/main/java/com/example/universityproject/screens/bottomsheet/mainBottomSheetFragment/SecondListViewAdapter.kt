package com.example.universityproject.screens.bottomsheet.mainBottomSheetFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universityproject.databinding.SecondaryListViewItemBinding

class SecondListViewAdapter(
    private val data: List<Pair<String, String>>,
    val itemOnClick: (Pair<String, String>) -> Unit
)
    : RecyclerView.Adapter<SecondListViewAdapter.ViewHolder>() {

    private lateinit var binding: SecondaryListViewItemBinding
    
    private var actualData = data

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var viewHolderBinding: SecondaryListViewItemBinding

        init {
            viewHolderBinding = SecondaryListViewItemBinding.bind(view)
            viewHolderBinding.root.setOnClickListener{
                itemOnClick(
                    Pair(
                        viewHolderBinding.start.text.toString(),
                        viewHolderBinding.end.text.toString()
                    )
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

        holder.viewHolderBinding.start.text = actualData[position].first
        holder.viewHolderBinding.end.text = actualData[position].second

    }

    fun filterData(query: String?) {
        if (query != null) {
            actualData = data.filter {
                it.first.contains(query) or it.second.contains(query)
            }
        }
        notifyDataSetChanged()
    }

}