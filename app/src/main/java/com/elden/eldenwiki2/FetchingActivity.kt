package com.elden.eldenwiki2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var womRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var womList: ArrayList<WomModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        womRecyclerView = findViewById(R.id.rvWom)
        womRecyclerView.layoutManager = LinearLayoutManager(this)
        womRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        womList = arrayListOf<WomModel>()

        getWomenData()

    }

    private fun getWomenData() {

        womRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Women")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                womList.clear()
                if (snapshot.exists()){
                    for (womSnap in snapshot.children){
                        val womData = womSnap.getValue(WomModel::class.java)
                        womList.add(womData!!)
                    }
                    val mAdapter = WomAdapter(womList)
                    womRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : WomAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, WomDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("womId", womList[position].womId)
                            intent.putExtra("womName", womList[position].womName)
                            intent.putExtra("womDesc", womList[position].womDesc)
                            intent.putExtra("womPlace", womList[position].womPlace)
                            startActivity(intent)
                        }

                    })

                    womRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}