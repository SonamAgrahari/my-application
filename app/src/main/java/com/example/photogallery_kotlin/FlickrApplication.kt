package com.example.photogallery_kotlin

import android.app.Application

 class FlickrApplication : Application() {

    companion object {
        lateinit var flickrApplicaiton: FlickrApplication

        fun getFlickrApplication(): FlickrApplication {
            return flickrApplicaiton
        }
    }

    override fun onCreate() {
        super.onCreate()

        flickrApplicaiton = this
    }

}