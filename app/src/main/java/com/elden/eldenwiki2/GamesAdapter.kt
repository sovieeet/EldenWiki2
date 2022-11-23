package com.elden.eldenwiki2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GamesAdapter(private val lista: MutableList<GamesResponseItem>): RecyclerView.Adapter<GamesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GamesViewHolder(layoutInflater.inflate(R.layout.jefe_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val item = lista[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = lista.size

}