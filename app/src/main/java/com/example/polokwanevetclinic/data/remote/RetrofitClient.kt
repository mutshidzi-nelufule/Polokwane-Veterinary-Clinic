package com.example.polokwanevetclinic.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Default API Base URL:
    // "10.0.2.2" is the special alias to your host machine's localhost in Android Emulator.
    var baseUrl: String = "http://10.0.2.2:8080/api/"
        set(value) {
            field = if (value.endsWith("/")) value else "$value/"
            apiService = createApiService()
        }

    private fun createApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    var apiService: ApiService = createApiService()
}
