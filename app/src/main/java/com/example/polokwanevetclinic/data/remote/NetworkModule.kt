package com.example.polokwanevetclinic.data.remote

object NetworkModule {

    val apiService: ApiService
        get() = RetrofitClient.apiService
}
