package com.example.dog_app.di

import android.content.Context
import android.os.Build
import android.util.Log
import com.example.dog_app.BuildConfig
import com.example.dog_app.data.dog_api_network
import com.example.dog_app.qualifiers.IOThread
import com.example.dog_app.qualifiers.MainThread
import com.example.dog_app.util.constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provide_base_url() = constants.BASE_URL

   @Singleton
   @Provides
   fun provideApplicationContext(@ApplicationContext context: Context ) = context

    @MainThread
    @Singleton
    @Provides
    fun provideMainDispatcher() : CoroutineDispatcher = Dispatchers.Main

    @IOThread
    @Singleton
    @Provides
    fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provide_Http_client() = if(BuildConfig.DEBUG){
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()
    }else{
        OkHttpClient.Builder().build()
    }

   @Singleton
   @Provides
   fun retrofit_instance(okHttpClient: OkHttpClient,BASE_URL:String) : dog_api_network =
       Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl(BASE_URL)
           .client(okHttpClient)
           .build()
           .create(dog_api_network::class.java)

}