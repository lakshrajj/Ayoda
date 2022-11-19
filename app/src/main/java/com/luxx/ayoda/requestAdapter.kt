package com.luxx.ayoda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class requestAdapter(private val requestList:ArrayList<requestData>,
                     private val context: Context
                     ) : RecyclerView.Adapter<requestAdapter.requestViewHolder>() {

    var onItemClick: ((requestData) -> Unit)? = null

    class requestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.requestImageView)
        val des: TextView = itemView.findViewById(R.id.requestDescription)
        val landmark: TextView = itemView.findViewById(R.id.requestLandmark)
        val location: TextView = itemView.findViewById(R.id.requestLocation)
        val status: TextView = itemView.findViewById(R.id.requestStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): requestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.request_card, parent, false)
        return requestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: requestViewHolder, position: Int) {
        val request = requestList[position]
        Glide.with(context).load(requestList[position].imageUrl).into(holder.image)
        holder.des.text = request.des
        holder.landmark.text = request.landmark
        holder.location.text = request.location
        holder.status.text = request.status

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(request)
        }
    }

    override fun getItemCount(): Int {
        return requestList.size
    }
}