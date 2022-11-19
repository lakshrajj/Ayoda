package com.luxx.ayoda

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toDrawable
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {


    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var storageReference = Firebase.storage
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var databaseReference2: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? =null
    var lati : String? = null
    var count: Int = 1
    var imageset = 2

    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)


        auth = FirebaseAuth.getInstance()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference
        databaseReference2 = firebaseDatabase?.reference?.child("request")
        storageReference = FirebaseStorage.getInstance()

        databaseReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ccount = snapshot.child("count").value.toString().toInt()
                count = ccount
                Log.d("TAG", count.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }})

        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uldImg.setImageURI(it)
                if (it != null) {
                    uri = it
                    imageset = 10
                }
            }
        )

        checkgps()
        locationPerms()



        imgResetBtn.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        uldImg.setOnClickListener {
            galleryImage.launch("image/*")
        }

        imgUploadBtn.setOnClickListener(){


            if(TextUtils.isEmpty(desTV.text.toString())){
                desTV.error = "Please add some Description"
                return@setOnClickListener
            }else if(TextUtils.isEmpty(landTV.text.toString())){
                landTV.error = "Please add some Landmark"
                return@setOnClickListener
            }else if(imageset!=10){
                Toast.makeText(this, "Select an image please", Toast.LENGTH_LONG).show()
                return@setOnClickListener}

            uploadData()
        }
    }

    private fun locationPerms() {

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener {
            if(it!=null){
                lati = it.latitude.toString()+","+ it.longitude.toString()
            }

        }.addOnFailureListener setOnClickListener@{
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
            return
        }
    }

    private fun checkgps() {

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(
            this.applicationContext
        )
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener { task ->
            try { //when thr gps is on
                task.getResult(
                    ApiException::class.java
                )
            }catch (e : ApiException){  // GPS is off
                e.printStackTrace()
                when(e.statusCode)
                {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(this,200)

                }
                }
            }
        }

    }

    private fun uploadData() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.show()
        locationPerms()

        if (lati.isNullOrBlank()){
            Toast.makeText(this, "Please Turn On GPS and Try Again", Toast.LENGTH_LONG).show()
            return
        }



        val newPost = databaseReference2?.child(count.toString())
        newPost?.child("user")?.setValue(auth.currentUser?.uid)
        newPost?.child("landmark")?.setValue(landTV.text.toString())
        newPost?.child("des")?.setValue(desTV.text.toString())
        newPost?.child("status")?.setValue("close")
        newPost?.child("location")?.setValue(lati)
        databaseReference!!.child("count").setValue(count.plus(1))


        storageReference.getReference("images/"+count)
            .putFile(uri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        newPost?.child("imageUrl")?.setValue(it.toString())
                            ?.addOnSuccessListener {
                                dialog.dismiss()
                                uldImg.setImageResource(R.drawable.upload_cloud)
                                desTV.setText("")
                                landTV.setText("")
                            Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
                        }
                            ?.addOnFailureListener {
                                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                        }
                    }
            }
    }
}