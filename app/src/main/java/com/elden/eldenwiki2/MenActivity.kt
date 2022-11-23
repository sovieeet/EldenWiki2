package com.elden.eldenwiki2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenActivity : AppCompatActivity() {

    private lateinit var btnInsertData2: Button
    private lateinit var btnFetchData2: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_men)


        btnInsertData2 = findViewById(R.id.btnInsertData2)
        btnFetchData2 = findViewById(R.id.btnFetchData2)

        btnInsertData2.setOnClickListener {
            val intent = Intent(this, InsertionActivity2::class.java)
            startActivity(intent)
        }

        btnFetchData2.setOnClickListener {
            val intent = Intent(this, FetchingActivity2::class.java)
            startActivity(intent)
        }

    }
}