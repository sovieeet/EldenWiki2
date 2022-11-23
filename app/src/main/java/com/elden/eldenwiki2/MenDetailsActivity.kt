package com.elden.eldenwiki2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class MenDetailsActivity : AppCompatActivity() {
    private lateinit var tvMenId: TextView
    private lateinit var tvMenName: TextView
    private lateinit var tvMenDesc: TextView
    private lateinit var tvMenPlace: TextView
    private lateinit var btnUpdate2: Button
    private lateinit var btnDelete2: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_men_details)

        initView()
        setValuesToViews()

        btnUpdate2.setOnClickListener {
            abrirUpdateDialog(
                intent.getStringExtra("menId").toString(),
                intent.getStringExtra("menName").toString()
            )
        }

        btnDelete2.setOnClickListener {
            eliminarRecord(
                intent.getStringExtra("menId").toString()
            )
        }

    }

    private fun initView() {
        tvMenId = findViewById(R.id.tvMenId)
        tvMenName = findViewById(R.id.tvMenName)
        tvMenDesc = findViewById(R.id.tvMenDesc)
        tvMenPlace = findViewById(R.id.tvMenPlace)

        btnUpdate2 = findViewById(R.id.btnUpdate2)
        btnDelete2 = findViewById(R.id.btnDelete2)
    }

    private fun setValuesToViews() {
        tvMenId.text = intent.getStringExtra("menId")
        tvMenName.text = intent.getStringExtra("menName")
        tvMenDesc.text = intent.getStringExtra("menDesc")
        tvMenPlace.text = intent.getStringExtra("menPlace")

    }

    private fun eliminarRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Men").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Datos de hombre eliminados", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity2::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Error al eliminar ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun abrirUpdateDialog(
        menId: String,
        menName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_men_dialog, null)

        mDialog.setView(mDialogView)

        val etMenName = mDialogView.findViewById<EditText>(R.id.etMenName)
        val etMenDesc = mDialogView.findViewById<EditText>(R.id.etMenDesc)
        val etMenPlace = mDialogView.findViewById<EditText>(R.id.etMenPlace)

        val btnUpdateData2 = mDialogView.findViewById<Button>(R.id.btnUpdateData2)

        etMenName.setText(intent.getStringExtra("menName").toString())
        etMenDesc.setText(intent.getStringExtra("menDesc").toString())
        etMenPlace.setText(intent.getStringExtra("menPlace").toString())

        mDialog.setTitle("Actualizando $menName ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData2.setOnClickListener {
            updateMenData(
                menId,
                etMenName.text.toString(),
                etMenDesc.text.toString(),
                etMenPlace.text.toString()
            )

            Toast.makeText(applicationContext, "Datos de hombre actualizados", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvMenName.text = etMenName.text.toString()
            tvMenDesc.text = etMenDesc.text.toString()
            tvMenPlace.text = etMenPlace.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateMenData(
        id: String,
        name: String,
        desc: String,
        place: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Men").child(id)
        val empInfo = MenModel(id, name, desc, place)
        dbRef.setValue(empInfo)
    }

}
