package com.luxx.ayoda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postBtn.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

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
            }

            true}
    }
}