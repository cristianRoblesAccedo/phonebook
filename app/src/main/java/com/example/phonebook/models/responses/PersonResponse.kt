package com.example.phonebook.models.responses

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("profile_path") val imagePath: String,
)
