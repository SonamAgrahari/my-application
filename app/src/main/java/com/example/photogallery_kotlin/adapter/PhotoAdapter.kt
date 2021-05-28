package com.example.photogallery_kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery_kotlin.model.Photos
import com.example.photogallery_kotlin.R
import com.example.photogallery_kotlin.util.Constant
import kotlinx.android.synthetic.main.row_photo.view.*

class PhotoAdapter(
    val context: Context,
    val arrayList: ArrayList<Photos>,
    val mItemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<PhotoAdapter.myholder>() {

    interface ItemClickListener {
        fun onItemClick(posi: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_photo, parent, false)
        return myholder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        var url: String =
            "https://farm" + arrayList[position].farm + ".static.flickr.com/" + arrayList[position].server + "/" + arrayList[position].id + "_" + arrayList[position].secret + ".jpg"
        Glide.with(context).load(url)
            .into(holder.imageview)

        holder.imageview.setOnClickListener {
            mItemClickListener.onItemClick(position)
        }

    }

    class myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageview = itemView.imageview
    }
}