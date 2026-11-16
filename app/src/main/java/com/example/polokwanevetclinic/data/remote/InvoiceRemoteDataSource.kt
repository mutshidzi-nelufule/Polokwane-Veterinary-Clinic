package com.example.polokwanevetclinic.data.remote

class InvoiceRemoteDataSource(
    private val apiService: ApiService
) {

    suspend fun getInvoices(): ApiResult<List<InvoiceApi>> =
        safeApiCall {
            apiService.getInvoices()
        }

    suspend fun getInvoicesByOwner(
        ownerId: Int
    ): ApiResult<List<InvoiceApi>> =
        safeApiCall {
            apiService.getInvoicesByOwner(ownerId)
        }

    suspend fun createInvoice(
        request: InvoiceRequestApi
    ): ApiResult<InvoiceApi> =
        safeApiResponse {
            apiService.createInvoice(request)
        }

    suspend fun deleteInvoice(id: Int): ApiResult<Boolean> =
        safeApiCall {
            val response = apiService.deleteInvoice(id)

            if (response.isSuccessful) true
            else throw Exception("Delete failed: ${response.code()}")
        }
}
