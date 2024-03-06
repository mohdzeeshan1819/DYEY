package com.example.dyey.homeFolder.HomeFragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dyey.R

private const val PREF_NAME = "UserData"
private const val KEY_USER_DATA = "userData"
class HomeAdapter(private val itemList: ArrayList<Users>, private val listener: OnItemClickListener) : BaseAdapter()


{

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Users {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.home_recyclerview, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        // Set click listener
        view?.setOnClickListener {
            listener.onItemClick(position)
        }

        // Load data into views
        val user = itemList[position]
        val imageUrl = user.profileImageUrl.toString()
        Glide.with(parent!!.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_logo2)
            .error(R.drawable.ic_logo)
            .into(viewHolder.imageView)
        val firstName = user.firstName.toString()
        viewHolder.name.text = "$firstName ${user.lastName}"
        viewHolder.location.text = user.city.toString()

        return view!!
    }

    class ViewHolder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val name:TextView = view.findViewById(R.id.name)
        val location :TextView = view.findViewById(R.id.location)


    }
}


interface OnItemClickListener {
    fun onItemClick(position: Int)
}
