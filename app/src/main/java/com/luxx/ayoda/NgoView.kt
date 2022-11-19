package com.luxx.ayoda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ngo_view.*

class NgoView : AppCompatActivity() {
    private lateinit var requestList: ArrayList<requestData>
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo_view)

        imageRecyc.layoutManager = LinearLayoutManager(this)

        requestList = arrayListOf()

        val requestAdapter = requestAdapter(requestList,this@NgoView)

        requestAdapter.onItemClick={
            val intent = Intent(this@NgoView, RequestCard::class.java)
            Log.d("s","clickekddddddddddd")
            intent.putExtra("request",it)
            startActivity(intent)
        }


        databaseReference = FirebaseDatabase.getInstance().getReference("request")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (dataSnapShot in snapshot.children){
                        val request = dataSnapShot.getValue(requestData::class.java)
                        requestList.add(request!!)
                    }
                    imageRecyc.adapter = requestAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NgoView, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}