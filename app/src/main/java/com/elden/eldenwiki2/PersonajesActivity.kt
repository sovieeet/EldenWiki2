package com.elden.eldenwiki2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PersonajesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personajes)

        findViewById<Button>(R.id.btnWom).setOnClickListener {
            startActivity(Intent(this , WomActivity::class.java))
        }

        findViewById<Button>(R.id.btnMen).setOnClickListener {
            startActivity(Intent(this , MenActivity::class.java))
        }
    }

}