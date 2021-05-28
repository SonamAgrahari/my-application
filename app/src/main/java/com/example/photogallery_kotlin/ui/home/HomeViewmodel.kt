package com.example.photogallery_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery_kotlin.model.PhotoResponse
import com.example.photogallery_kotlin.repository.HomeRepository

class HomeViewmodel : ViewModel(), HomeRepository.Serverresponse {
    var homeRepository: HomeRepository = HomeRepository()
    private var mutableLiveData: MutableLiveData<PhotoResponse> =
        MutableLiveData()
    private val mutableerror = MutableLiveData<String>()
    var liveData: LiveData<PhotoResponse> = mutableLiveData
    var liveDataerror: LiveData<String> = mutableerror

    fun getdata(page: Int) {
        homeRepository.getphotofromserver(this, page)
    }

    override fun onphotoSuccess(response: PhotoResponse) {
        mutableLiveData.postValue(response)
    }

    override fun onphotoError(errorMsg: String) {
        mutableerror.postValue(errorMsg)
    }
}