package com.elden.eldenwiki2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var etWomName: EditText
    private lateinit var etWomDesc: EditText
    private lateinit var etWomPlace: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etWomName = findViewById(R.id.etWomName)
        etWomDesc = findViewById(R.id.etWomDesc)
        etWomPlace = findViewById(R.id.etWomPlace)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Women")

        btnSaveData.setOnClickListener {
            saveWomenData()
        }
    }

    private fun saveWomenData() {

        //getting values
        val womName = etWomName.text.toString()
        val womDesc = etWomDesc.text.toString()
        val womPlace = etWomPlace.text.toString()

        if (womName.isEmpty()) {
            etWomName.error = "Please enter name"
        }
        if (womDesc.isEmpty()) {
            etWomDesc.error = "Please enter age"
        }
        if (womPlace.isEmpty()) {
            etWomPlace.error = "Please enter salary"
        }

        val womId = dbRef.push().key!!

        val women = WomModel(womId, womName, womDesc, womPlace)

        dbRef.child(womId).setValue(women)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etWomName.text.clear()
                etWomDesc.text.clear()
                etWomPlace.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}