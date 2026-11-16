package com.example.polokwanevetclinic.data.remote

class AnimalRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getAnimals(): ApiResult<List<AnimalApi>> =
        safeApiCall {
            apiService.getAnimals()
        }

    suspend fun getAnimal(id: Int): ApiResult<AnimalApi> =
        safeApiCall {
            apiService.getAnimal(id)
        }

    suspend fun getAnimalsByOwner(
        ownerId: Int
    ): ApiResult<List<AnimalApi>> =
        safeApiCall {
            apiService.getAnimalsByOwner(ownerId)
        }

    suspend fun createAnimal(
        request: AnimalRequestApi
    ): ApiResult<AnimalApi> =
        safeApiResponse {
            apiService.createAnimal(request)
        }

    suspend fun updateAnimal(
        id: Int,
        request: AnimalRequestApi
    ): ApiResult<AnimalApi> =
        safeApiResponse {
            apiService.updateAnimal(id, request)
        }

    suspend fun deleteAnimal(id: Int): ApiResult<Boolean> =
        safeApiCall {
            val response = apiService.deleteAnimal(id)

            if (response.isSuccessful) true
            else throw Exception("Delete failed: ${response.code()}")
        }
}
