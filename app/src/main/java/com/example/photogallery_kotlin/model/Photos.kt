package com.example.photogallery_kotlin.model

data  class Photos(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val farm: Int,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
)

