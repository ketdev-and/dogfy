package com.example.dog_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dog_app.model.dog_breed.dog_breeds
import com.example.dog_app.qualifiers.IOThread
import com.example.dog_app.repository.dog_breed_repo
import com.example.dog_app.util.constants
import com.example.dog_app.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class dog_breed_VM @Inject public constructor(private val dogBreedRepo: dog_breed_repo,  @IOThread val dispatcher: CoroutineDispatcher) :ViewModel() {
    private val _res = MutableLiveData<Resource<dog_breeds>>()

    val res : LiveData<Resource<dog_breeds>>
    get() = _res

    init {
        get_all_breeds()
    }

    private fun get_all_breeds() = viewModelScope.launch(dispatcher) {
            _res.postValue(Resource.loading(null))
           try {
             dogBreedRepo.get_dog_breed().let {
                 if (it.isSuccessful && it.body() != null){
                     _res.postValue(Resource.success(it.body()))
                     Log.d(constants.DEBUG_DOG_BREED, it.body().toString())
                 }else{
                     _res.postValue((Resource.error(null, it.errorBody().toString())))
                     Log.d(constants.DEBUG_DOG_BREED, "unable to fetch from api")
                 }
             }

           }catch (e: IOException){
               _res.postValue((Resource.error(null, e.message)))
                Log.d(constants.DEBUG_DOG_BREED, "unable to fetch from api")
           }catch (e : HttpException){
               _res.postValue((Resource.error(null, e.message)))
               Log.d(constants.DEBUG_DOG_BREED, "server error")
           }



         }

}