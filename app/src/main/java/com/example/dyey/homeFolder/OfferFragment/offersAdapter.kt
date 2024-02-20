package com.example.dyey.homeFolder.OfferFragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dyey.R


class offersAdapter(private val offerList: ArrayList<OfferDetails>) : RecyclerView.Adapter<offersAdapter.ItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.users_recyclerview, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        val imageUrl = offerList[position].restaurantImage.toString()

        Glide.with(holder.itemView)
            .load(imageUrl)
            .placeholder(R.drawable.ic_logo2)
            .error(R.drawable.ic_logo)
            .into(holder.imageView)
        holder.name1.text = offerList[position].restaurantName.toString()
        holder.time.text = offerList[position].dateTime.toString()
        holder.location.text = offerList[position].restaurantAddress.toString()


//
//        holder.fullLayout.setOnClickListener(){
//            listener.measurementData(measurementList[position])
//        }


    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name1: TextView = itemView.findViewById(R.id.name)
        val time: TextView = itemView.findViewById(R.id.time)
        val location: TextView = itemView.findViewById(R.id.location)
        val imageView:ImageView = itemView.findViewById(R.id.image)

//        val fullLayout:CardView= itemView.findViewById(R.id.fullLayout)

    }


}


