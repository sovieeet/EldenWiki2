package com.elden.eldenwiki2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchingActivity2 : AppCompatActivity() {

    private lateinit var menRecyclerView: RecyclerView
    private lateinit var tvLoadingData2: TextView
    private lateinit var menList: ArrayList<MenModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching2)

        menRecyclerView = findViewById(R.id.rvMen)
        menRecyclerView.layoutManager = LinearLayoutManager(this)
        menRecyclerView.setHasFixedSize(true)
        tvLoadingData2 = findViewById(R.id.tvLoadingData2)

        menList = arrayListOf<MenModel>()

        getMenData()

    }

    private fun getMenData() {

        menRecyclerView.visibility = View.GONE
        tvLoadingData2.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Men")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menList.clear()
                if (snapshot.exists()){
                    for (menSnap in snapshot.children){
                        val menData = menSnap.getValue(MenModel::class.java)
                        menList.add(menData!!)
                    }
                    val mAdapter = MenAdapter(menList)
                    menRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MenAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity2, MenDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("menId", menList[position].menId)
                            intent.putExtra("menName", menList[position].menName)
                            intent.putExtra("menDesc", menList[position].menDesc)
                            intent.putExtra("menPlace", menList[position].menPlace)
                            startActivity(intent)
                        }

                    })

                    menRecyclerView.visibility = View.VISIBLE
                    tvLoadingData2.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}