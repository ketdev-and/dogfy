package com.example.dog_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dog_app.adapter.dog_breed_adapter
import com.example.dog_app.databinding.ActivityMainBinding

import com.example.dog_app.util.constants
import com.example.dog_app.util.resource.Status
import com.example.dog_app.viewmodel.dog_breed_VM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var dog_breed_adapter : dog_breed_adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        dog_breed_adapter = dog_breed_adapter();

        binding.breedRec.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = dog_breed_adapter
        }
        val model : dog_breed_VM by viewModels<dog_breed_VM>()
        model.res.observe(this,{
            when(it.status){
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.breedRec.visibility = View.VISIBLE
                   it.data?.let { res->
                       dog_breed_adapter.submit_List(res)
                   }
                }

                Status.LOADING->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.breedRec.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.breedRec.visibility = View.VISIBLE
                    Snackbar.make(binding.breedRec, "something went wrong", Snackbar.LENGTH_LONG)
                }
            }
        })

    }
}