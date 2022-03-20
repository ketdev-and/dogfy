package com.example.dog_app.data


import com.example.dog_app.model.dog_breed.dog_breeds
import com.example.dog_app.util.constants
import com.example.dog_app.util.constants.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface dog_api_network {
    @Headers("x-api-key: $API_KEY")
    @GET("breeds")
   suspend fun get_dog_breeds() : Response<dog_breeds>
}