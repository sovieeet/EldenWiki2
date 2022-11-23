package com.elden.eldenwiki2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenAdapter(private val menList: ArrayList<MenModel>) :
    RecyclerView.Adapter<MenAdapter.ViewHolder>() {

    private lateinit var hListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        hListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.men_list_item, parent, false)
        return ViewHolder(itemView, hListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMen = menList[position]
        holder.tvMenName.text = currentMen.menName
    }

    override fun getItemCount(): Int {
        return menList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvMenName : TextView = itemView.findViewById(R.id.tvMenName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}