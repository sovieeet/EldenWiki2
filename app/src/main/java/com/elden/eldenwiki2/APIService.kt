package com.elden.eldenwiki2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET()
    suspend fun getGames(@Url url:String): Response<List<GamesResponseItem>>
}