package com.example.photogallery_kotlin.network

import com.example.photogallery_kotlin.model.CommentResponse
import com.example.photogallery_kotlin.model.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    fun getphoto(@Query("api_key") apiKey: String, @Query("page") page: Int): Call<PhotoResponse>

    @GET("services/rest/?method=flickr.photos.comments.getList&nojsoncallback=1&format=json")
    fun getComments(
        @Query("api_key") apiKey: String,
        @Query("photo_id") id: String
    ): Call<CommentResponse>
}


