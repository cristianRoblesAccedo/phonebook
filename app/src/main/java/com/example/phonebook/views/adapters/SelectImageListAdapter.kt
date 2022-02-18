package com.example.phonebook.views.adapters

import android.app.Person
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phonebook.R
import com.example.phonebook.models.responses.PersonResponse

class SelectImageListAdapter(): RecyclerView.Adapter<SelectImageListAdapter.ViewHolder>() {
    var imageList = mutableListOf<PersonResponse>()
    val baseUrl = "https://image.tmdb.org/t/p/original/"

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.select_image_iv)
        fun render(item: PersonResponse) {
            Glide.with(view).load(baseUrl + item.imagePath).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.select_image_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    fun updateList(newList: MutableList<PersonResponse>) {
        imageList = newList
        notifyDataSetChanged()
    }
}