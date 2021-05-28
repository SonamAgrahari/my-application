package com.example.photogallery_kotlin.model

data  class FlickrResponse(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: ArrayList<Photos>
)