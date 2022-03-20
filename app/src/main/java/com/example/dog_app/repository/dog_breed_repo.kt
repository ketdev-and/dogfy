package com.example.dog_app.repository

import android.util.Log
import com.example.dog_app.data.dog_api_network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class dog_breed_repo @Inject constructor(private val dogApiNetwork: dog_api_network) {
    suspend fun get_dog_breed() = dogApiNetwork.get_dog_breeds()
}