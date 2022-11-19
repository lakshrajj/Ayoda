package com.luxx.ayoda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_request_card.*
import kotlinx.android.synthetic.main.request_card.*


class RequestCard : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? =null
    var loca: String = "10"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_card)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()


        val request = intent.getParcelableExtra<requestData>("request")

        if (request != null) {
            Glide.with(this).load(request.imageUrl).into(reqCardIv)
            loca = request.location.toString()
            rCardDescription.text = request.des
            rCardLandmark.text = request.landmark
            rCardStatus.text = request.status
            databaseReference = firebaseDatabase?.reference?.child("request")?.child(request.id.toString())
        }
        var result = loca.split(",").map { it.trim() }
        Location.setOnClickListener{
            val strUri =
                "http://maps.google.com/maps?q=loc:" + result[0] + "," + result[1] + " (" + "Label which you want" + ")"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )

            startActivity(intent)
        }

        strTreatBtm.setOnClickListener {
            databaseReference?.child("status")?.setValue("Treating")

        }
    }
}