package com.example.photogallery_kotlin.repository

import android.content.Context
import android.util.Log
import com.example.photogallery_kotlin.FlickrApplication
import com.example.photogallery_kotlin.model.CommentResponse
import com.example.photogallery_kotlin.network.ApiClient
import com.example.photogallery_kotlin.util.Constant
import com.example.photogallery_kotlin.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsRepository {

    private fun getContext(): Context {
        return FlickrApplication.getFlickrApplication().applicationContext
    }

    fun getdatafromserver(serverresponse: Serverresponse, id: String) {
        if (!Util.isNetworkConnected(getContext())) {
            serverresponse.onphotoError("No Internet Connection")
            return
        }

        var call: Call<CommentResponse> =
            ApiClient.getClient.getComments(Constant.FLICKR_KEY, id)

        call.enqueue(object : Callback<CommentResponse> {
            override fun onResponse(
                call: Call<CommentResponse>,
                response: Response<CommentResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val commentResponse: CommentResponse? = response.body()
                    if (response.code() == 200) {
                        Log.d("CHECKPOINT", "CHECK ResPonse Commment::" + response.body())
                        if (commentResponse != null) {
                            serverresponse.onphotoSuccess(commentResponse)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                Log.d("CHECKPOINT", "CHECK ERROR::" + t.localizedMessage)
            }

        })
    }

    interface Serverresponse {
        fun onphotoSuccess(response: CommentResponse)

        fun onphotoError(errorMsg: String)
    }
}