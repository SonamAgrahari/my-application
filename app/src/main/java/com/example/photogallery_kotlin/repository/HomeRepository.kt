package com.example.photogallery_kotlin.repository

import android.content.Context
import android.util.Log
import com.example.photogallery_kotlin.FlickrApplication
import com.example.photogallery_kotlin.model.PhotoResponse
import com.example.photogallery_kotlin.network.ApiClient
import com.example.photogallery_kotlin.util.Constant
import com.example.photogallery_kotlin.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private fun getContext(): Context {
        return FlickrApplication.getFlickrApplication().applicationContext
    }

    fun getphotofromserver(serverresponse: Serverresponse, page: Int) {
        if (!Util.isNetworkConnected(getContext())) {
            serverresponse.onphotoError("No Internet Connection")
            return
        }

        var call: Call<PhotoResponse> =
            ApiClient.getClient.getphoto(Constant.FLICKR_KEY, page)

        call.enqueue(object : Callback<PhotoResponse> {
            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val photoResponse: PhotoResponse? = response.body()

                    if (response.code() == 200) {

                        Log.d("CHECKPOINT", "CHECK ResPonse::" + response.body())
                        if (photoResponse != null) {
                            serverresponse.onphotoSuccess(photoResponse)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.d("CHECKPOINT", "CHECK ERROR::" + t.localizedMessage)
            }

        })
    }


    interface Serverresponse {
        fun onphotoSuccess(response: PhotoResponse)

        fun onphotoError(errorMsg: String)
    }
}