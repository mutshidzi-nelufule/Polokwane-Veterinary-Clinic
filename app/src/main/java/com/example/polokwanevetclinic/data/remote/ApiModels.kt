package com.example.polokwanevetclinic.data.remote

import com.google.gson.annotations.SerializedName

// PET OWNER

data class PetOwnerApi(
    val ownerId: Int? = null,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val address: String?,
    val registeredDate: String?,
    val createdAt: String? = null
)

data class PetOwnerRequestApi(
    val fullName: String,
    val email: String,
    val phone: String?,
    val address: String?,
    val registeredDate: String?
)

// ANIMAL

data class AnimalApi(
    val animalId: Int? = null,
    val ownerId: Int,
    val ownerName: String?,
    val name: String,
    val species: String?,
    val breed: String?,
    val dateOfBirthOrAge: String?,
    val sex: String?,
    val medicalNotes: String?,
    val createdAt: String? = null
)

data class AnimalRequestApi(
    val ownerId: Int,
    val name: String,
    val species: String?,
    val breed: String?,
    val dateOfBirthOrAge: String?,
    val sex: String?,
    val medicalNotes: String?
)

// APPOINTMENT

data class AppointmentApi(
    val appointmentId: Int? = null,
    val animalId: Int,
    val animalName: String?,
    val ownerId: Int,
    val ownerName: String?,
    val appointmentDate: String,
    val timeSlot: String,
    val reason: String,
    val status: String,
    val vetName: String?,
    val createdAt: String? = null
)

data class AppointmentRequestApi(
    val animalId: Int,
    val ownerId: Int,
    val appointmentDate: String,
    val timeSlot: String,
    val reason: String,
    val status: String,
    val vetName: String?
)

// TREATMENT

data class TreatmentApi(
    val treatmentId: Int? = null,
    val animalId: Int,
    val animalName: String?,
    val vetName: String?,
    val diagnosis: String?,
    val treatmentDescription: String?,
    val cost: Double,
    val treatmentDate: String,
    val createdAt: String? = null
)

data class TreatmentRequestApi(
    val animalId: Int,
    val vetName: String?,
    val diagnosis: String?,
    val treatmentDescription: String?,
    val cost: Double,
    val treatmentDate: String
)

// INVOICE

data class InvoiceApi(
    val invoiceId: Int? = null,
    val ownerId: Int,
    val ownerName: String?,
    val animalName: String?,
    val invoiceDate: String,
    val totalAmount: Double,
    val status: String,
    val createdAt: String? = null
)

data class InvoiceRequestApi(
    val ownerId: Int,
    val animalName: String?,
    val invoiceDate: String,
    val totalAmount: Double,
    val status: String
)
