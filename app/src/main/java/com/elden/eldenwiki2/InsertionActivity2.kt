package com.elden.eldenwiki2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity2 : AppCompatActivity() {
    private lateinit var etMenName: EditText
    private lateinit var etMenDesc: EditText
    private lateinit var etMenPlace: EditText
    private lateinit var btnSaveData2: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion2)

        etMenName = findViewById(R.id.etMenName)
        etMenDesc = findViewById(R.id.etMenDesc)
        etMenPlace = findViewById(R.id.etMenPlace)
        btnSaveData2 = findViewById(R.id.btnSave2)

        dbRef = FirebaseDatabase.getInstance().getReference("Men")

        btnSaveData2.setOnClickListener {
            saveMenData()
        }
    }

    private fun saveMenData() {

        //getting values
        val menName = etMenName.text.toString()
        val menDesc = etMenDesc.text.toString()
        val menPlace = etMenPlace.text.toString()

        if (menName.isEmpty()) {
            etMenName.error = "Porfavor ingresa un nombre"
        }
        if (menDesc.isEmpty()) {
            etMenDesc.error = "Porfavor ingresa una descripción"
        }
        if (menPlace.isEmpty()) {
            etMenPlace.error = "Porfavor ingresa una ubicación"
        }

        val menId = dbRef.push().key!!

        val men = MenModel(menId, menName, menDesc, menPlace)

        dbRef.child(menId).setValue(men)
            .addOnCompleteListener {
                Toast.makeText(this, "Hombre creado correctamente", Toast.LENGTH_LONG).show()

                etMenName.text.clear()
                etMenDesc.text.clear()
                etMenPlace.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}