package com.example.phonebook.models

import com.example.phonebook.models.responses.PersonPageResponse
import com.example.phonebook.models.responses.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface TMDBService {
    @GET
    suspend fun getPopularPeople(@Url url: String): retrofit2.Response<PersonPageResponse>
}