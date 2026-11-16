package com.example.polokwanevetclinic.data.remote

class PetOwnerRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getPetOwners(): ApiResult<List<PetOwnerApi>> =
        safeApiCall {
            apiService.getPetOwners()
        }

    suspend fun getPetOwner(id: Int): ApiResult<PetOwnerApi> =
        safeApiCall {
            apiService.getPetOwner(id)
        }

    suspend fun createPetOwner(
        request: PetOwnerRequestApi
    ): ApiResult<PetOwnerApi> =
        safeApiResponse {
            apiService.createPetOwner(request)
        }

    suspend fun updatePetOwner(
        id: Int,
        request: PetOwnerRequestApi
    ): ApiResult<PetOwnerApi> =
        safeApiResponse {
            apiService.updatePetOwner(id, request)
        }

    suspend fun deletePetOwner(id: Int): ApiResult<Boolean> =
        safeApiCall {
            val response = apiService.deletePetOwner(id)

            if (response.isSuccessful) {
                true
            } else {
                throw Exception("Delete failed: ${response.code()}")
            }
        }
}
