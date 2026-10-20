package com.example.polokwanevetclinic.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicUserDao {
    @Query("SELECT * FROM users WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun getUserByEmail(email: String): ClinicUserEntity?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): ClinicUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: ClinicUserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<ClinicUserEntity>): List<Long>

    @Update
    suspend fun updateUser(user: ClinicUserEntity): Int

    @Delete
    suspend fun deleteUser(user: ClinicUserEntity): Int
}

@Dao
interface PetOwnerDao {
    @Query("SELECT * FROM pet_owners ORDER BY fullName ASC")
    fun getAllOwners(): Flow<List<PetOwnerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(owner: PetOwnerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(owners: List<PetOwnerEntity>): List<Long>

    @Update
    suspend fun updateOwner(owner: PetOwnerEntity): Int

    @Delete
    suspend fun deleteOwner(owner: PetOwnerEntity): Int
}

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals ORDER BY name ASC")
    fun getAllAnimals(): Flow<List<AnimalEntity>>

    @Query("SELECT * FROM animals WHERE ownerId = :ownerId")
    fun getAnimalsByOwner(ownerId: String): Flow<List<AnimalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(animal: AnimalEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animals: List<AnimalEntity>): List<Long>

    @Update
    suspend fun updateAnimal(animal: AnimalEntity): Int

    @Delete
    suspend fun deleteAnimal(animal: AnimalEntity): Int
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY appointmentDate DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appointments: List<AppointmentEntity>): List<Long>

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity): Int

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity): Int
}

@Dao
interface TreatmentDao {
    @Query("SELECT * FROM treatments ORDER BY date DESC")
    fun getAllTreatments(): Flow<List<TreatmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTreatment(treatment: TreatmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(treatments: List<TreatmentEntity>): List<Long>

    @Update
    suspend fun updateTreatment(treatment: TreatmentEntity): Int

    @Delete
    suspend fun deleteTreatment(treatment: TreatmentEntity): Int
}

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM invoices ORDER BY invoiceDate DESC")
    fun getAllInvoices(): Flow<List<InvoiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoiceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(invoices: List<InvoiceEntity>): List<Long>

    @Update
    suspend fun updateInvoice(invoice: InvoiceEntity): Int

    @Delete
    suspend fun deleteInvoice(invoice: InvoiceEntity): Int
}
