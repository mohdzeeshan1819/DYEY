package com.example.dyey.homeFolder.OfferFragment.CreateOffer.RestaurantDetails

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dyey.R
import com.example.dyey.homeFolder.OfferFragment.CreateOffer.OnItemClickListeners

class RestaurantAdapter(private val personList: ArrayList<Results>, private val listener: OnItemClickListenerss) :
    RecyclerView.Adapter<RestaurantAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_recyclerview, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val location = personList[position]


        val context = holder.itemView.context // Get the context from the itemView
        val image = personList[position].icon.toString()
        Glide.with(context)
            .load(image)
            .placeholder(R.drawable.ic_logo2)
            .error(R.drawable.ic_logo2)
            .into(holder.imageView)
        holder.nameTextView.text = personList[position].name.toString()
        holder.timeTextView.text = "Address : "+personList[position].vicinity.toString()
        holder.locationTextView.text = "Rating : "+personList[position].rating.toString()
        holder.layout.setOnClickListener() {
            listener.onItemClick(location)
        }
    }


    override fun getItemCount(): Int {
        return personList.size
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val timeTextView: TextView = itemView.findViewById(R.id.time)
        val locationTextView: TextView = itemView.findViewById(R.id.location)
        val layout:LinearLayout=itemView.findViewById(R.id.layout)
    }
}

interface OnItemClickListenerss {
    fun onItemClick(location: Results)
}