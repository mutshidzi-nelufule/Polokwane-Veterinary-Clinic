package com.example.polokwanevetclinic.data.remote

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // PET OWNERS

    @GET("api/pet-owners")
    suspend fun getPetOwners(): List<PetOwnerApi>

    @GET("api/pet-owners/{id}")
    suspend fun getPetOwner(
        @Path("id") id: Int
    ): PetOwnerApi

    @POST("api/pet-owners")
    suspend fun createPetOwner(
        @Body request: PetOwnerRequestApi
    ): Response<PetOwnerApi>

    @PUT("api/pet-owners/{id}")
    suspend fun updatePetOwner(
        @Path("id") id: Int,
        @Body request: PetOwnerRequestApi
    ): Response<PetOwnerApi>

    @DELETE("api/pet-owners/{id}")
    suspend fun deletePetOwner(
        @Path("id") id: Int
    ): Response<Unit>


    // ANIMALS

    @GET("api/animals")
    suspend fun getAnimals(): List<AnimalApi>

    @GET("api/animals/{id}")
    suspend fun getAnimal(
        @Path("id") id: Int
    ): AnimalApi

    @GET("api/animals/owner/{ownerId}")
    suspend fun getAnimalsByOwner(
        @Path("ownerId") ownerId: Int
    ): List<AnimalApi>

    @POST("api/animals")
    suspend fun createAnimal(
        @Body request: AnimalRequestApi
    ): Response<AnimalApi>

    @PUT("api/animals/{id}")
    suspend fun updateAnimal(
        @Path("id") id: Int,
        @Body request: AnimalRequestApi
    ): Response<AnimalApi>

    @DELETE("api/animals/{id}")
    suspend fun deleteAnimal(
        @Path("id") id: Int
    ): Response<Unit>


    // APPOINTMENTS

    @GET("api/appointments")
    suspend fun getAppointments(): List<AppointmentApi>

    @GET("api/appointments/{id}")
    suspend fun getAppointment(
        @Path("id") id: Int
    ): AppointmentApi

    @GET("api/appointments/owner/{ownerId}")
    suspend fun getAppointmentsByOwner(
        @Path("ownerId") ownerId: Int
    ): List<AppointmentApi>

    @POST("api/appointments")
    suspend fun createAppointment(
        @Body request: AppointmentRequestApi
    ): Response<AppointmentApi>

    @DELETE("api/appointments/{id}")
    suspend fun deleteAppointment(
        @Path("id") id: Int
    ): Response<Unit>


    // TREATMENTS

    @GET("api/treatments")
    suspend fun getTreatments(): List<TreatmentApi>

    @GET("api/treatments/animal/{animalId}")
    suspend fun getTreatmentsByAnimal(
        @Path("animalId") animalId: Int
    ): List<TreatmentApi>

    @POST("api/treatments")
    suspend fun createTreatment(
        @Body request: TreatmentRequestApi
    ): Response<TreatmentApi>

    @DELETE("api/treatments/{id}")
    suspend fun deleteTreatment(
        @Path("id") id: Int
    ): Response<Unit>


    // INVOICES

    @GET("api/invoices")
    suspend fun getInvoices(): List<InvoiceApi>

    @GET("api/invoices/owner/{ownerId}")
    suspend fun getInvoicesByOwner(
        @Path("ownerId") ownerId: Int
    ): List<InvoiceApi>

    @POST("api/invoices")
    suspend fun createInvoice(
        @Body request: InvoiceRequestApi
    ): Response<InvoiceApi>

    @DELETE("api/invoices/{id}")
    suspend fun deleteInvoice(
        @Path("id") id: Int
    ): Response<Unit>
}
