package com.example.polokwanevetclinic.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.polokwanevetclinic.model.Animal
import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.AppointmentStatus
import com.example.polokwanevetclinic.model.ClinicUser
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.InvoiceStatus
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.model.Treatment
import com.example.polokwanevetclinic.model.UserRole

@Entity(tableName = "users")
data class ClinicUserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val role: String,
    val passwordHash: String? = null,
    val token: String? = null
) {
    fun toDomain() = ClinicUser(
        id = id,
        name = name,
        email = email,
        role = try { UserRole.valueOf(role) } catch (e: Exception) { UserRole.PET_OWNER },
        token = token
    )
}

@Entity(tableName = "pet_owners")
data class PetOwnerEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val registeredDate: String
) {
    fun toDomain() = PetOwner(
        id = id,
        fullName = fullName,
        email = email,
        phone = phone,
        address = address,
        registeredDate = registeredDate
    )
}

fun PetOwner.toEntity() = PetOwnerEntity(
    id = id,
    fullName = fullName,
    email = email,
    phone = phone,
    address = address,
    registeredDate = registeredDate
)

@Entity(tableName = "animals")
data class AnimalEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val ownerName: String,
    val name: String,
    val species: String,
    val breed: String,
    val dateOfBirthOrAge: String,
    val sex: String,
    val medicalNotes: String
) {
    fun toDomain() = Animal(
        id = id,
        ownerId = ownerId,
        ownerName = ownerName,
        name = name,
        species = species,
        breed = breed,
        dateOfBirthOrAge = dateOfBirthOrAge,
        sex = sex,
        medicalNotes = medicalNotes
    )
}

fun Animal.toEntity() = AnimalEntity(
    id = id,
    ownerId = ownerId,
    ownerName = ownerName,
    name = name,
    species = species,
    breed = breed,
    dateOfBirthOrAge = dateOfBirthOrAge,
    sex = sex,
    medicalNotes = medicalNotes
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val animalId: String,
    val animalName: String,
    val ownerId: String,
    val ownerName: String,
    val appointmentDate: String,
    val timeSlot: String,
    val reason: String,
    val status: String,
    val vetName: String
) {
    fun toDomain() = Appointment(
        id = id,
        animalId = animalId,
        animalName = animalName,
        ownerId = ownerId,
        ownerName = ownerName,
        appointmentDate = appointmentDate,
        timeSlot = timeSlot,
        reason = reason,
        status = try { AppointmentStatus.valueOf(status) } catch (e: Exception) { AppointmentStatus.SCHEDULED },
        vetName = vetName
    )
}

fun Appointment.toEntity() = AppointmentEntity(
    id = id,
    animalId = animalId,
    animalName = animalName,
    ownerId = ownerId,
    ownerName = ownerName,
    appointmentDate = appointmentDate,
    timeSlot = timeSlot,
    reason = reason,
    status = status.name,
    vetName = vetName
)

@Entity(tableName = "treatments")
data class TreatmentEntity(
    @PrimaryKey val id: String,
    val animalId: String,
    val animalName: String,
    val vetName: String,
    val diagnosis: String,
    val treatmentDescription: String,
    val cost: Double,
    val date: String
) {
    fun toDomain() = Treatment(
        id = id,
        animalId = animalId,
        animalName = animalName,
        vetName = vetName,
        diagnosis = diagnosis,
        treatmentDescription = treatmentDescription,
        cost = cost,
        date = date
    )
}

fun Treatment.toEntity() = TreatmentEntity(
    id = id,
    animalId = animalId,
    animalName = animalName,
    vetName = vetName,
    diagnosis = diagnosis,
    treatmentDescription = treatmentDescription,
    cost = cost,
    date = date
)

@Entity(tableName = "invoices")
data class InvoiceEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val ownerName: String,
    val animalName: String,
    val invoiceDate: String,
    val totalAmount: Double,
    val status: String
) {
    fun toDomain() = Invoice(
        id = id,
        ownerId = ownerId,
        ownerName = ownerName,
        animalName = animalName,
        invoiceDate = invoiceDate,
        totalAmount = totalAmount,
        status = try { InvoiceStatus.valueOf(status) } catch (e: Exception) { InvoiceStatus.UNPAID },
        items = emptyList()
    )
}

fun Invoice.toEntity() = InvoiceEntity(
    id = id,
    ownerId = ownerId,
    ownerName = ownerName,
    animalName = animalName,
    invoiceDate = invoiceDate,
    totalAmount = totalAmount,
    status = status.name
)
