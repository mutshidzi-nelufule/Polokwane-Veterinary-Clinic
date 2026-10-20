package com.example.polokwanevetclinic.repository

import com.example.polokwanevetclinic.data.local.AnimalDao
import com.example.polokwanevetclinic.data.local.AppointmentDao
import com.example.polokwanevetclinic.data.local.ClinicUserDao
import com.example.polokwanevetclinic.data.local.ClinicUserEntity
import com.example.polokwanevetclinic.data.local.InvoiceDao
import com.example.polokwanevetclinic.data.local.PetOwnerDao
import com.example.polokwanevetclinic.data.local.PetOwnerEntity
import com.example.polokwanevetclinic.data.local.TreatmentDao
import com.example.polokwanevetclinic.data.local.toEntity
import com.example.polokwanevetclinic.data.remote.ApiService
import com.example.polokwanevetclinic.model.Animal
import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.ClinicUser
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.model.Treatment
import com.example.polokwanevetclinic.model.UserRole
import com.example.polokwanevetclinic.utils.PasswordUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthRepository(
    private val userDao: ClinicUserDao,
    private val petOwnerDao: PetOwnerDao
) {

    suspend fun login(
        email: String,
        rawPassword: String
    ): Result<ClinicUser> {
        return try {
            require(email.isNotBlank()) {
                "Please enter your email address."
            }
            require(rawPassword.isNotBlank()) {
                "Please enter your password."
            }

            val normalizedEmail = email.trim()
            val existingUser = userDao.getUserByEmail(normalizedEmail)
                ?: return Result.failure(Exception("Account not found for $normalizedEmail. Please register."))

            val inputHash = PasswordUtils.hashPassword(rawPassword)
            if (existingUser.passwordHash != null && existingUser.passwordHash != inputHash) {
                return Result.failure(Exception("Incorrect password. Please try again."))
            }

            Result.success(existingUser.toDomain())
        } catch (e: Exception) {
            Result.failure(
                Exception(e.message ?: "Login failed. Check your details.")
            )
        }
    }

    suspend fun register(
        fullName: String,
        email: String,
        rawPassword: String
    ): Result<ClinicUser> {
        return try {
            require(fullName.isNotBlank()) {
                "Please enter your full name."
            }
            require(PasswordUtils.isValidEmail(email)) {
                "Please enter a valid email address."
            }
            require(rawPassword.length >= 8) {
                "Your password must contain at least 8 characters."
            }

            val normalizedEmail = email.trim()
            val existingUser = userDao.getUserByEmail(normalizedEmail)
            if (existingUser != null) {
                return Result.failure(Exception("An account with email $normalizedEmail already exists."))
            }

            val userId = "u-" + System.currentTimeMillis()
            val passwordHash = PasswordUtils.hashPassword(rawPassword)
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val newUserEntity = ClinicUserEntity(
                id = userId,
                name = fullName.trim(),
                email = normalizedEmail,
                role = UserRole.PET_OWNER.name,
                passwordHash = passwordHash,
                token = "local-session-$userId"
            )

            val newOwnerEntity = PetOwnerEntity(
                id = "o-" + System.currentTimeMillis(),
                fullName = fullName.trim(),
                email = normalizedEmail,
                phone = "",
                address = "",
                registeredDate = currentDate
            )

            userDao.insertUser(newUserEntity)
            petOwnerDao.insertOwner(newOwnerEntity)

            Result.success(newUserEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(
                Exception(
                    e.message ?: "Registration failed. Please try again."
                )
            )
        }
    }
}

class PetOwnerRepository(
    private val ownerDao: PetOwnerDao,
    private val apiService: ApiService
) {
    val ownersFlow: Flow<List<PetOwner>> =
        ownerDao.getAllOwners().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun addOwner(owner: PetOwner) {
        ownerDao.insertOwner(owner.toEntity())
        try {
            apiService.addOwner(owner)
        } catch (_: Exception) {
            // Local DB already updated
        }
    }

    suspend fun updateOwner(owner: PetOwner) {
        ownerDao.updateOwner(owner.toEntity())
    }

    suspend fun deleteOwner(owner: PetOwner) {
        ownerDao.deleteOwner(owner.toEntity())
    }
}

class AnimalRepository(
    private val animalDao: AnimalDao,
    private val apiService: ApiService
) {
    val animalsFlow: Flow<List<Animal>> =
        animalDao.getAllAnimals().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun addAnimal(animal: Animal) {
        animalDao.insertAnimal(animal.toEntity())
        try {
            apiService.addAnimal(animal)
        } catch (_: Exception) {
            // Local DB updated
        }
    }

    suspend fun updateAnimal(animal: Animal) {
        animalDao.updateAnimal(animal.toEntity())
    }

    suspend fun deleteAnimal(animal: Animal) {
        animalDao.deleteAnimal(animal.toEntity())
    }
}

class AppointmentRepository(
    private val appointmentDao: AppointmentDao,
    private val apiService: ApiService
) {
    val appointmentsFlow: Flow<List<Appointment>> =
        appointmentDao.getAllAppointments().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun scheduleAppointment(appointment: Appointment) {
        appointmentDao.insertAppointment(appointment.toEntity())
        try {
            apiService.scheduleAppointment(appointment)
        } catch (_: Exception) {
            // Local DB updated
        }
    }

    suspend fun updateAppointment(appointment: Appointment) {
        appointmentDao.updateAppointment(appointment.toEntity())
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        appointmentDao.deleteAppointment(appointment.toEntity())
    }
}

class TreatmentRepository(
    private val treatmentDao: TreatmentDao,
    private val apiService: ApiService
) {
    val treatmentsFlow: Flow<List<Treatment>> =
        treatmentDao.getAllTreatments().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun recordTreatment(treatment: Treatment) {
        treatmentDao.insertTreatment(treatment.toEntity())
        try {
            apiService.recordTreatment(treatment)
        } catch (_: Exception) {
            // Local DB updated
        }
    }

    suspend fun updateTreatment(treatment: Treatment) {
        treatmentDao.updateTreatment(treatment.toEntity())
    }

    suspend fun deleteTreatment(treatment: Treatment) {
        treatmentDao.deleteTreatment(treatment.toEntity())
    }
}

class InvoiceRepository(
    private val invoiceDao: InvoiceDao,
    private val apiService: ApiService
) {
    val invoicesFlow: Flow<List<Invoice>> =
        invoiceDao.getAllInvoices().map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun createInvoice(invoice: Invoice) {
        invoiceDao.insertInvoice(invoice.toEntity())
        try {
            apiService.createInvoice(invoice)
        } catch (_: Exception) {
            // Local DB updated
        }
    }

    suspend fun updateInvoice(invoice: Invoice) {
        invoiceDao.updateInvoice(invoice.toEntity())
    }

    suspend fun deleteInvoice(invoice: Invoice) {
        invoiceDao.deleteInvoice(invoice.toEntity())
    }
}
