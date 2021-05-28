package com.example.photogallery_kotlin.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photogallery_kotlin.model.CommentResponse
import com.example.photogallery_kotlin.repository.DetailsRepository

class DetailsViewmodel : ViewModel(), DetailsRepository.Serverresponse {

    var detailsRepository: DetailsRepository = DetailsRepository()

    private var mutableLiveData: MutableLiveData<CommentResponse> =
        MutableLiveData()
    private val mutableerror = MutableLiveData<String>()
    var liveData: LiveData<CommentResponse> = mutableLiveData
    var liveDataerror: LiveData<String> = mutableerror

    fun getdata(id: String) {
        detailsRepository.getdatafromserver(this, id)
    }

    override fun onphotoSuccess(response: CommentResponse) {
        mutableLiveData.postValue(response)
    }

    override fun onphotoError(errorMsg: String) {
        mutableerror.postValue(errorMsg)
    }
}