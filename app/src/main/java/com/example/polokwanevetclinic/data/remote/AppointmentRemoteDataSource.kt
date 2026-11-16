package com.example.polokwanevetclinic.data.remote

class AppointmentRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getAppointments(): ApiResult<List<AppointmentApi>> =
        safeApiCall {
            apiService.getAppointments()
        }

    suspend fun getAppointment(id: Int): ApiResult<AppointmentApi> =
        safeApiCall {
            apiService.getAppointment(id)
        }

    suspend fun getAppointmentsByOwner(
        ownerId: Int
    ): ApiResult<List<AppointmentApi>> =
        safeApiCall {
            apiService.getAppointmentsByOwner(ownerId)
        }

    suspend fun createAppointment(
        request: AppointmentRequestApi
    ): ApiResult<AppointmentApi> =
        safeApiResponse {
            apiService.createAppointment(request)
        }

    suspend fun deleteAppointment(id: Int): ApiResult<Boolean> =
        safeApiCall {
            val response = apiService.deleteAppointment(id)

            if (response.isSuccessful) true
            else throw Exception("Delete failed: ${response.code()}")
        }
}
