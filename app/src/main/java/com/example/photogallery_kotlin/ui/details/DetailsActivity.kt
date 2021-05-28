package com.example.photogallery_kotlin.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.photogallery_kotlin.R
import com.example.photogallery_kotlin.adapter.DetailsAdapter
import com.example.photogallery_kotlin.model.Comment
import com.example.photogallery_kotlin.model.CommentResponse
import com.example.photogallery_kotlin.util.Constant
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    lateinit var vIewModel: DetailsViewmodel
    lateinit var detailsadapter: DetailsAdapter

    var commetlist: ArrayList<Comment> = ArrayList()

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var secret: String
    private lateinit var server: String
    private lateinit var farm: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        vIewModel = ViewModelProviders.of(this).get(DetailsViewmodel::class.java)
        initializer()
        observer()
    }

    private fun initializer() {
        intent.getStringExtra(Constant.id)?.let {
            id = it
        }
        intent.getStringExtra(Constant.title)?.let {
            title = it
        }

        intent.getStringExtra(Constant.secret)?.let {
            secret = it
        }
        intent.getStringExtra(Constant.server)?.let {
            server = it
        }

        intent.getStringExtra(Constant.farm)?.let {
            farm = it
        }


        var url: String =
            "https://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + ".jpg"
        Glide.with(this).load(url)
            .into(imageview)

        if (::id.isInitialized) {
            textId.text = ("id:" + id)

            vIewModel.getdata(id)
        }
        if (::title.isInitialized) {
            textTitle.text = ("Title:" + title)
        }
        if (::secret.isInitialized) {
            textSecret.text = ("Secret:" + secret)
        }
        if (::server.isInitialized) {
            textServer.text = ("Server:" + server)
        }
        if (::farm.isInitialized) {
            textFarm.text = ("Farm:" + farm)
        }
        detailsadapter = DetailsAdapter(commetlist)
        rvComments.setAdapter(detailsadapter)
    }

    private fun observer() {
        vIewModel.liveData
            .observe(this, Observer<CommentResponse> { responsecomment ->
                progress_circularload.visibility = View.GONE
                if (responsecomment != null && responsecomment.comments != null) {
                    tvNoComment.setVisibility(View.VISIBLE)
                    responsecomment.comments.comment?.let {
                        if (responsecomment.comments.comment.size !== 0) {
                            Log.d("msg", "Checkrun")
                            tvNoComment.setVisibility(View.GONE)
                        }

                        commetlist.addAll(responsecomment.comments.comment)
                        detailsadapter.notifyDataSetChanged()
                    }
                }
            })
        vIewModel.liveDataerror.observe(this, Observer { errorresponse ->
            Toast.makeText(this, errorresponse, Toast.LENGTH_LONG).show()
            Log.d("msg", "no internet")
            progress_circularload.visibility = View.GONE
        })
    }


}