package com.luxx.ayoda

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class PostActivity : AppCompatActivity() {
    private var storageReference = Firebase.storage
    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        storageReference = FirebaseStorage.getInstance()

        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                uldImg.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )

        imgResetBtn.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        uldImg.setOnClickListener {
            galleryImage.launch("image/*")
        }
        imgUploadBtn.setOnClickListener {
            storageReference.getReference("images").child("demo")
                .putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val imageMap = mapOf(
                                "url" to uri.toString()
                            )

                            val dbRef = FirebaseDatabase.getInstance().getReference("userImage")
                            dbRef.child("users").setValue(imageMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                                }
                        }
                }
        }
    }
}