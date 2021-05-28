package com.example.photogallery_kotlin.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery_kotlin.model.Comment
import com.example.photogallery_kotlin.R
import kotlinx.android.synthetic.main.row_comment.view.*

class DetailsAdapter(
    val arrayList: ArrayList<Comment>
) : RecyclerView.Adapter<DetailsAdapter.myholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, parent, false)
        return myholder(itemView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: myholder, position: Int) {
        holder.textAuthor.text = arrayList[position].authorname
        holder.textComment.text = arrayList[position]._content
    }

    class myholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textAuthor = itemView.textAuthor
        var textComment = itemView.textComment
    }
}