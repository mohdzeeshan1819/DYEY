package com.example.dyey.homeFolder.OfferFragment.CreateOffer


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dyey.R

class SearchByCousinAdapter(private val locations: List<String>, private val listener: OnItemClickListeners) : RecyclerView.Adapter<SearchByCousinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        holder.locationTextView.text=location.toString()
//        holder.bind(location)
       holder.layout.setOnClickListener() {
            listener.onItemClick(location)
        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val locationTextView: TextView = itemView.findViewById(R.id.address)
        val layout:RelativeLayout=itemView.findViewById(R.id.relativeLayout)

//        fun bind(location: String) {
//            locationTextView.text = location
//            itemView.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val clickedLocation = locations[position]
//                    listener.onItemClick(clickedLocation)
//                }
//            }
//        }
    }
}


interface OnItemClickListeners {
    fun onItemClick(location: String)
}
