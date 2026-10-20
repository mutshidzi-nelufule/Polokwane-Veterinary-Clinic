package com.example.polokwanevetclinic.data.remote

import com.example.polokwanevetclinic.model.Animal
import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.model.Treatment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit interface defining REST API endpoints for Polokwane Vet Clinic backend.
 */
interface ApiService {

    // Auth
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): Response<AuthResponseDto>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<AuthResponseDto>

    // Pet Owners
    @GET("owners")
    suspend fun getOwners(): Response<List<PetOwner>>

    @POST("owners")
    suspend fun addOwner(@Body owner: PetOwner): Response<PetOwner>

    // Animals / Patients
    @GET("animals")
    suspend fun getAnimals(): Response<List<Animal>>

    @POST("animals")
    suspend fun addAnimal(@Body animal: Animal): Response<Animal>

    @GET("animals/owner/{ownerId}")
    suspend fun getAnimalsByOwner(@Path("ownerId") ownerId: String): Response<List<Animal>>

    // Appointments
    @GET("appointments")
    suspend fun getAppointments(): Response<List<Appointment>>

    @POST("appointments")
    suspend fun scheduleAppointment(@Body appointment: Appointment): Response<Appointment>

    // Treatments
    @GET("treatments")
    suspend fun getTreatments(): Response<List<Treatment>>

    @POST("treatments")
    suspend fun recordTreatment(@Body treatment: Treatment): Response<Treatment>

    // Invoices / Billing
    @GET("invoices")
    suspend fun getInvoices(): Response<List<Invoice>>

    @POST("invoices")
    suspend fun createInvoice(@Body invoice: Invoice): Response<Invoice>
}
