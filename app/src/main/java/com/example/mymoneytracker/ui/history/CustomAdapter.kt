package com.example.mymoneytracker.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoneytracker.R
import com.example.mymoneytracker.databinding.FragmentHistoryBinding

class CustomAdapter(private val mList: List<ItemsViewModel>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textDesignDate.text = ItemsViewModel.text
        holder.textDesignMoney.text = ItemsViewModel.text2
        holder.textDesignDescription.text = ItemsViewModel.text3
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textDesignDate: TextView = itemView.findViewById(R.id.textDesignDate)
        val textDesignMoney: TextView = itemView.findViewById(R.id.textDesignMoney)
        val textDesignDescription: TextView = itemView.findViewById(R.id.textDesignDescription)
    }
}