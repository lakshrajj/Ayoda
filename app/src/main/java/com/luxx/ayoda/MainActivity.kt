package com.luxx.ayoda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postBtn.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference?.child("profile")?.child((auth.currentUser?.uid!!))

        databaseReference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ngo = snapshot.child("ngo").value.toString()
                if (ngo == "yes"){
                    ngomove()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }})

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        toggle = ActionBarDrawerToggle(this,drawer,R.string.open,R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav.setNavigationItemSelectedListener {
            if(it.itemId==R.id.nav_backup){

            }else if(it.itemId==R.id.nav_Restore){

            }else if (it.itemId==R.id.nav_Settings){

            }else if(it.itemId==R.id.nav_about){

                drawer.close()
            }else if(it.itemId==R.id.help){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/lakshrajj/Ayoda"))
                startActivity(browserIntent)
            }else if(it.itemId==R.id.logout){
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent= Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            true}
    }

    private fun ngomove() {
            val intent = Intent(this,NgoView::class.java)
            startActivity(intent)
}
}