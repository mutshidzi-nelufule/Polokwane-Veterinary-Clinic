package com.example.polokwanevetclinic.data.remote

class TreatmentRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getTreatments(): ApiResult<List<TreatmentApi>> =
        safeApiCall {
            apiService.getTreatments()
        }

    suspend fun getTreatmentsByAnimal(
        animalId: Int
    ): ApiResult<List<TreatmentApi>> =
        safeApiCall {
            apiService.getTreatmentsByAnimal(animalId)
        }

    suspend fun createTreatment(
        request: TreatmentRequestApi
    ): ApiResult<TreatmentApi> =
        safeApiResponse {
            apiService.createTreatment(request)
        }

    suspend fun deleteTreatment(id: Int): ApiResult<Boolean> =
        safeApiCall {
            val response = apiService.deleteTreatment(id)

            if (response.isSuccessful) true
            else throw Exception("Delete failed: ${response.code()}")
        }
}
