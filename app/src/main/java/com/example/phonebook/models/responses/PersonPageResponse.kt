package com.example.phonebook.models.responses

import com.google.gson.annotations.SerializedName

data class PersonPageResponse(
    @SerializedName("page") val page: String,
    @SerializedName("results") val results: MutableList<PersonResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)
