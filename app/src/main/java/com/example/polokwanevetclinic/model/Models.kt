package com.example.polokwanevetclinic.model

/**
 * User roles in the Polokwane Vet Clinic system.
 */
enum class UserRole {
    ADMIN,
    VET,
    RECEPTIONIST,
    PET_OWNER
}

/**
 * Clinic user account details.
 */
data class ClinicUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.PET_OWNER,
    val token: String? = null
)

/**
 * Pet owner registered at the clinic.
 */
data class PetOwner(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val registeredDate: String = ""
)

/**
 * Animal/Patient record associated with an owner.
 */
data class Animal(
    val id: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val name: String = "",
    val species: String = "",
    val breed: String = "",
    val dateOfBirthOrAge: String = "",
    val sex: String = "",
    val medicalNotes: String = ""
)

enum class AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}

/**
 * Appointment record.
 */
data class Appointment(
    val id: String = "",
    val animalId: String = "",
    val animalName: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val appointmentDate: String = "",
    val timeSlot: String = "",
    val reason: String = "",
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val vetName: String = ""
)

/**
 * Treatment / Medical Record for an animal.
 */
data class Treatment(
    val id: String = "",
    val animalId: String = "",
    val animalName: String = "",
    val vetName: String = "",
    val diagnosis: String = "",
    val treatmentDescription: String = "",
    val cost: Double = 0.0,
    val date: String = ""
)

enum class InvoiceStatus {
    UNPAID,
    PAID,
    OVERDUE
}

/**
 * Invoice item details.
 */
data class InvoiceItem(
    val description: String = "",
    val cost: Double = 0.0
)

/**
 * Billing invoice associated with an owner and animal.
 */
data class Invoice(
    val id: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val animalName: String = "",
    val invoiceDate: String = "",
    val totalAmount: Double = 0.0,
    val status: InvoiceStatus = InvoiceStatus.UNPAID,
    val items: List<InvoiceItem> = emptyList()
)
