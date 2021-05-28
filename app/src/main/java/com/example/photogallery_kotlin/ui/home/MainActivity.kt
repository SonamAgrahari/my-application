package com.example.photogallery_kotlin.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery_kotlin.model.PhotoResponse
import com.example.photogallery_kotlin.model.Photos
import com.example.photogallery_kotlin.R
import com.example.photogallery_kotlin.adapter.PhotoAdapter
import com.example.photogallery_kotlin.ui.details.DetailsActivity
import com.example.photogallery_kotlin.util.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PhotoAdapter.ItemClickListener {
    lateinit var vIewModel: HomeViewmodel

    lateinit var photoAdapter: PhotoAdapter

    var photosList: ArrayList<Photos> = ArrayList()
    private var loading = true
    lateinit var gridLayoutManager: GridLayoutManager
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vIewModel = ViewModelProviders.of(this@MainActivity).get(HomeViewmodel::class.java)
        initializer()
        observer()
        setlistener()
    }

    private fun setlistener() {
        rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    progress_circular.visibility = View.VISIBLE
                    visibleItemCount = gridLayoutManager.getChildCount()
                    totalItemCount = gridLayoutManager.getItemCount()
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            progress_circular.visibility = View.GONE
                            loading = false
                            Log.v("msg", "Last Item Wow !")
                            page = page + 1
                            vIewModel.getdata(page)
                            loading = true
                        }
                    }
                }
            }
        })
    }


    private fun initializer() {
        gridLayoutManager = GridLayoutManager(this, 2)
        rvPhotos.setLayoutManager(gridLayoutManager)
        photoAdapter = PhotoAdapter(this, photosList, this)
        rvPhotos.setAdapter(photoAdapter)
        vIewModel.getdata(page)

    }

    private fun observer() {
        vIewModel.liveData.observe(this,
            Observer<PhotoResponse> { responsePhotos ->
                Log.d("CHECKPOINT", "CHECK OBSERVER::$responsePhotos")
                if (responsePhotos != null) {
                    progress_circularload.visibility = View.GONE
                    photosList.addAll(responsePhotos.photos.photo)
                    photoAdapter.notifyDataSetChanged()
                }
            })
        vIewModel.liveDataerror.observe(this, Observer<String> { errorresponse ->
            Toast.makeText(this, errorresponse, Toast.LENGTH_LONG).show()
            progress_circularload.visibility = View.GONE
        })
    }

    override fun onItemClick(position: Int) {
        val i = Intent(this, DetailsActivity::class.java)
        var id: String = photosList[position].id
        var title: String = photosList[position].title
        var secret: String = photosList[position].secret
        var server: String = photosList[position].server
        var farm: String = photosList[position].farm.toString()
        i.putExtra(Constant.id, id)
        i.putExtra(Constant.title, title)
        i.putExtra(Constant.secret, secret)
        i.putExtra(Constant.server, server)
        i.putExtra(Constant.farm, farm)
        startActivity(i)
    }
}