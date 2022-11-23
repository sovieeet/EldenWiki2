package com.elden.eldenwiki2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elden.eldenwiki2.databinding.JefeListItemBinding
import com.squareup.picasso.Picasso

class GamesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = JefeListItemBinding.bind(view)

    val auxNombre = view.findViewById<TextView>(R.id.txtNombre)
    val auxGenero = view.findViewById<TextView>(R.id.txtDescripcion)
    val auxPlataforma = view.findViewById<TextView>(R.id.txtLugar)
    val auxFecha = view.findViewById<TextView>(R.id.txtRegion)

    fun render(gameResponseItemModel: GamesResponseItem) {
        auxNombre.text = gameResponseItemModel.title
        auxGenero.text = gameResponseItemModel.genre
        auxPlataforma.text = gameResponseItemModel.platform
        auxFecha.text = gameResponseItemModel.release_date
        Picasso.get().load(gameResponseItemModel.thumbnail).into(binding.txtImagen)
    }

}