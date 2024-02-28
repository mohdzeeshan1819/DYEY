package com.example.dyey.homeFolder.ProfileFragment.SettingActiviy.TermAndCondition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dyey.R

class TermAndConditionAdapter(private val offerList: ArrayList<Details>) : RecyclerView.Adapter<TermAndConditionAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.term_and_condition_layout, parent, false)
        return ItemViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name1.text = offerList[position].title.toString()
        holder.time.text = offerList[position].content.toString()

    }
    override fun getItemCount(): Int {
        return offerList.size
    }
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name1: TextView = itemView.findViewById(R.id.question)
        val time: TextView = itemView.findViewById(R.id.answere)
    }


}


