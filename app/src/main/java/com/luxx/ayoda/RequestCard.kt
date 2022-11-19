package com.luxx.ayoda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_request_card.*

class RequestCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_card)

        val request = intent.getParcelableExtra<requestData>("request")

        if (request != null) {
            Glide.with(this).load(request.imageUrl).into(reqCardIv)
        }
    }
}