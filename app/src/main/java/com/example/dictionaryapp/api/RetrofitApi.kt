package com.example.dictionaryapp.api

import com.example.dictionaryapp.model.Dictionary
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("v2/entries/en/{word}")
    suspend fun getDictionary(
        @Path("word") word: String
    ) : Response<Dictionary>
}

object RetrofitInstance {
    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api by lazy {
        getInstance().create(DictionaryApi::class.java)
    }
}
