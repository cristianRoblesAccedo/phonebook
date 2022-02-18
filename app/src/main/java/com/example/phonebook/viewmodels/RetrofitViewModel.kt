package com.example.phonebook.viewmodels

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.R
import com.example.phonebook.models.TMDBService
import com.example.phonebook.models.responses.PersonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitViewModel: ViewModel() {
    private val baseUrl = "https://api.themoviedb.org/"
    private val apiKey = "8cf6b9028b7faea32272410623cc9121"
    private val api: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val personListLiveData = MutableLiveData<MutableList<PersonResponse>>()

    fun getPopularPeople() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = api.create(TMDBService::class.java).getPopularPeople(
                "3/person/popular?api_key=$apiKey")
            call.body()?.let {
                personListLiveData.postValue(it.results)
            }
        }
    }
}
