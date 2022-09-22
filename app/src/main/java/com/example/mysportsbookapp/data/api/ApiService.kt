package com.example.mysportsbookapp.data.api

import com.example.mysportsbookapp.data.api.dao.SportsResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("sports")
    suspend fun getTodos(): Response<List<SportsResponseItem>>
}