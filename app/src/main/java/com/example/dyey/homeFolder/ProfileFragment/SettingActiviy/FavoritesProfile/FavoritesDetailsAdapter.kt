package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.FavoritesProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dyey.R

class FavoritesDetailsAdapter(private val context: Context, private val data: ArrayList<UserDetails>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): UserDetails {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.home_recyclerview, parent, false)
        val viewHolder: ViewHolder

        if (convertView == null) {
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val user = data[position]

        val lastName = user.lastName.toString()
        viewHolder.name.text = "${user.firstName} $lastName"
        viewHolder.profession.text=user.profession.toString()
        viewHolder.location.text=user.city.toString()
        val imageUrl = user.profileImageUrl.toString()
        Glide.with(parent!!.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_logo2)
            .error(R.drawable.ic_logo)
            .into(viewHolder.imageView)
        // Set other views as needed

        return view
    }


    private class ViewHolder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val profession : TextView = view.findViewById(R.id.time)
        val location : TextView = view.findViewById(R.id.location)


    }
}
