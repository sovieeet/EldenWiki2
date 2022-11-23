package com.elden.eldenwiki2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val personajes = view.findViewById<Button>(R.id.btnPersonajes)

        personajes.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, PersonajesActivity::class.java))
                finish()
            }
        }
        return view
        }
}