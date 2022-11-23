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

class WomDetailsActivity : AppCompatActivity() {
    private lateinit var tvWomId: TextView
    private lateinit var tvWomName: TextView
    private lateinit var tvWomDesc: TextView
    private lateinit var tvWomPlace: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wom_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("womId").toString(),
                intent.getStringExtra("womName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("womId").toString()
            )
        }

    }

    private fun initView() {
        tvWomId = findViewById(R.id.tvWomId)
        tvWomName = findViewById(R.id.tvWomName)
        tvWomDesc = findViewById(R.id.tvWomDesc)
        tvWomPlace = findViewById(R.id.tvWomPlace)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvWomId.text = intent.getStringExtra("womId")
        tvWomName.text = intent.getStringExtra("womName")
        tvWomDesc.text = intent.getStringExtra("womDesc")
        tvWomPlace.text = intent.getStringExtra("womPlace")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Women").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Datos de mujer eliminados", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Error al eliminar ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        womId: String,
        womName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_wom_dialog, null)

        mDialog.setView(mDialogView)

        val etWomName = mDialogView.findViewById<EditText>(R.id.etWomName)
        val etWomDesc = mDialogView.findViewById<EditText>(R.id.etWomDesc)
        val etWomPlace = mDialogView.findViewById<EditText>(R.id.etWomPlace)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etWomName.setText(intent.getStringExtra("womName").toString())
        etWomDesc.setText(intent.getStringExtra("womDesc").toString())
        etWomPlace.setText(intent.getStringExtra("womPlace").toString())

        mDialog.setTitle("Actualizando $womName ")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                womId,
                etWomName.text.toString(),
                etWomDesc.text.toString(),
                etWomPlace.text.toString()
            )

            Toast.makeText(applicationContext, "Datos de mujer actualizados", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvWomName.text = etWomName.text.toString()
            tvWomDesc.text = etWomDesc.text.toString()
            tvWomPlace.text = etWomPlace.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        desc: String,
        place: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Women").child(id)
        val empInfo = WomModel(id, name, desc, place)
        dbRef.setValue(empInfo)
    }

}
