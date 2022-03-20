package com.example.dog_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_app.databinding.DogItemBinding
import com.example.dog_app.model.dog_breed.DogBreedsItem

class dog_breed_adapter :  ListAdapter<DogBreedsItem, dog_breed_adapter.dog_breed_VH>(dog_diff_callback()) {



   private class dog_diff_callback: DiffUtil.ItemCallback<DogBreedsItem>() {
       override fun areItemsTheSame(oldItem: DogBreedsItem, newItem: DogBreedsItem): Boolean {
           return oldItem.id == newItem.id
       }

       override fun areContentsTheSame(oldItem: DogBreedsItem, newItem: DogBreedsItem): Boolean {
           return oldItem == newItem
       }

   }


    private  val differ = AsyncListDiffer(this, dog_diff_callback())

    fun submit_List(list:List<DogBreedsItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dog_breed_VH {
         return  dog_breed_VH.from(parent)
    }

    override fun onBindViewHolder(holder: dog_breed_VH, position: Int) {
            val breed = differ.currentList[position]
            holder.bind(breed)
    }

   class dog_breed_VH(val binding: DogItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(breed : DogBreedsItem){
            binding.breeds = breed
            binding.executePendingBindings()
        }
       companion object {
            fun from(parent: ViewGroup) : dog_breed_VH {
                val binding = DogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return dog_breed_VH(binding)
            }

        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }


}
