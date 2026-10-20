package com.example.polokwanevetclinic.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.polokwanevetclinic.utils.PasswordUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ClinicUserEntity::class,
        PetOwnerEntity::class,
        AnimalEntity::class,
        AppointmentEntity::class,
        TreatmentEntity::class,
        InvoiceEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): ClinicUserDao
    abstract fun petOwnerDao(): PetOwnerDao
    abstract fun animalDao(): AnimalDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun treatmentDao(): TreatmentDao
    abstract fun invoiceDao(): InvoiceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                lateinit var dbInstance: AppDatabase
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "polokwane_vet_clinic.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                populateInitialData(dbInstance)
                            }
                        }
                    })
                    .build()
                dbInstance = instance
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(db: AppDatabase) {
            // Pre-populate Initial Users
            val defaultPasswordHash = PasswordUtils.hashPassword("Password123!")
            db.userDao().insertAll(
                listOf(
                    ClinicUserEntity("u1", "Dr. M. Tshivhase", "vet@polokwanevet.co.za", "VET", defaultPasswordHash, "local-token-1"),
                    ClinicUserEntity("u2", "John Smith", "john.smith@example.com", "PET_OWNER", defaultPasswordHash, "local-token-2"),
                    ClinicUserEntity("u3", "Admin User", "admin@polokwanevet.co.za", "ADMIN", defaultPasswordHash, "local-token-3")
                )
            )

            // Pre-populate Owners
            db.petOwnerDao().insertAll(
                listOf(
                    PetOwnerEntity("1", "John Smith", "john.smith@example.com", "0821234567", "123 Market St, Polokwane", "2024-01-15"),
                    PetOwnerEntity("2", "Sarah Ndlovu", "sarah.n@example.com", "0719876543", "45 Grobler St, Polokwane", "2024-02-10"),
                    PetOwnerEntity("3", "David Botha", "dbotha@example.com", "0835551234", "78 Landdros Mare St, Polokwane", "2024-03-01"),
                    PetOwnerEntity("4", "Thabo Molefe", "tmolefe@example.com", "0842223344", "12 Hospital St, Polokwane", "2024-03-15"),
                    PetOwnerEntity("5", "Emily van der Merwe", "emilyv@example.com", "0724445566", "89 Schoeman St, Polokwane", "2024-04-02")
                )
            )

            // Pre-populate Animals
            db.animalDao().insertAll(
                listOf(
                    AnimalEntity("1", "1", "John Smith", "Max", "Dog", "German Shepherd", "3 years", "Male", "Up to date with rabies shots"),
                    AnimalEntity("2", "2", "Sarah Ndlovu", "Bella", "Cat", "Siamese", "2 years", "Female", "Spayed"),
                    AnimalEntity("3", "3", "David Botha", "Rocky", "Dog", "Boerboel", "5 years", "Male", "Mild skin allergy"),
                    AnimalEntity("4", "4", "Thabo Molefe", "Milo", "Dog", "Jack Russell", "1 year", "Male", "Microchipped"),
                    AnimalEntity("5", "5", "Emily van der Merwe", "Luna", "Cat", "Persian", "4 years", "Female", "Requires dental scaling")
                )
            )

            // Pre-populate Appointments
            db.appointmentDao().insertAll(
                listOf(
                    AppointmentEntity("1", "1", "Max", "1", "John Smith", "2024-11-10", "09:00 AM", "Annual Checkup & Rabies Vaccine", "SCHEDULED", "Dr. M. Tshivhase"),
                    AppointmentEntity("2", "2", "Bella", "2", "Sarah Ndlovu", "2024-11-11", "10:30 AM", "Dental Examination", "SCHEDULED", "Dr. M. Tshivhase"),
                    AppointmentEntity("3", "3", "Rocky", "3", "David Botha", "2024-11-05", "02:00 PM", "Allergy Treatment Follow-up", "COMPLETED", "Dr. K. Naidoo"),
                    AppointmentEntity("4", "4", "Milo", "4", "Thabo Molefe", "2024-11-12", "11:15 AM", "Deworming & Health Assessment", "SCHEDULED", "Dr. K. Naidoo")
                )
            )

            // Pre-populate Treatments
            db.treatmentDao().insertAll(
                listOf(
                    TreatmentEntity("1", "1", "Max", "Dr. M. Tshivhase", "Canine Parvovirus Booster", "Administered DHPP vaccine and deworming tablet.", 450.00, "2024-10-15"),
                    TreatmentEntity("2", "3", "Rocky", "Dr. K. Naidoo", "Allergies & Dermatitis", "Prescribed antihistamines and anti-itch ointment.", 680.00, "2024-11-05")
                )
            )

            // Pre-populate Invoices
            db.invoiceDao().insertAll(
                listOf(
                    InvoiceEntity("INV-1001", "1", "John Smith", "Max", "2024-10-15", 450.00, "PAID"),
                    InvoiceEntity("INV-1002", "3", "David Botha", "Rocky", "2024-11-05", 680.00, "UNPAID")
                )
            )
        }
    }
}
