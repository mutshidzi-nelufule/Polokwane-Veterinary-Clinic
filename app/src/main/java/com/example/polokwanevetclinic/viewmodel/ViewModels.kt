package com.example.polokwanevetclinic.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.polokwanevetclinic.data.local.AppDatabase
import com.example.polokwanevetclinic.data.remote.RetrofitClient
import com.example.polokwanevetclinic.model.Animal
import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.ClinicUser
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.model.Treatment
import com.example.polokwanevetclinic.repository.AnimalRepository
import com.example.polokwanevetclinic.repository.AppointmentRepository
import com.example.polokwanevetclinic.repository.AuthRepository
import com.example.polokwanevetclinic.repository.InvoiceRepository
import com.example.polokwanevetclinic.repository.PetOwnerRepository
import com.example.polokwanevetclinic.repository.TreatmentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    suspend fun login(
        email: String,
        passwordHash: String
    ): Result<ClinicUser> {
        return repository.login(email, passwordHash)
    }

    suspend fun register(
        fullName: String,
        email: String,
        passwordHash: String
    ): Result<ClinicUser> {
        return repository.register(fullName, email, passwordHash)
    }
}

class PetOwnerViewModel(
    private val repository: PetOwnerRepository
) : ViewModel() {

    val owners: StateFlow<List<PetOwner>> = repository.ownersFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addOwner(owner: PetOwner) {
        viewModelScope.launch {
            repository.addOwner(owner)
        }
    }

    fun updateOwner(owner: PetOwner) {
        viewModelScope.launch {
            repository.updateOwner(owner)
        }
    }

    fun deleteOwner(owner: PetOwner) {
        viewModelScope.launch {
            repository.deleteOwner(owner)
        }
    }
}

class AnimalViewModel(
    private val repository: AnimalRepository
) : ViewModel() {

    val animals: StateFlow<List<Animal>> = repository.animalsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.addAnimal(animal)
        }
    }

    fun updateAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.updateAnimal(animal)
        }
    }

    fun deleteAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.deleteAnimal(animal)
        }
    }
}

class AppointmentViewModel(
    private val repository: AppointmentRepository
) : ViewModel() {

    val appointments: StateFlow<List<Appointment>> =
        repository.appointmentsFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun scheduleAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.scheduleAppointment(appointment)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.updateAppointment(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.deleteAppointment(appointment)
        }
    }
}

class TreatmentViewModel(
    private val repository: TreatmentRepository
) : ViewModel() {

    val treatments: StateFlow<List<Treatment>> =
        repository.treatmentsFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun recordTreatment(treatment: Treatment) {
        viewModelScope.launch {
            repository.recordTreatment(treatment)
        }
    }

    fun updateTreatment(treatment: Treatment) {
        viewModelScope.launch {
            repository.updateTreatment(treatment)
        }
    }

    fun deleteTreatment(treatment: Treatment) {
        viewModelScope.launch {
            repository.deleteTreatment(treatment)
        }
    }
}

class BillingViewModel(
    private val repository: InvoiceRepository
) : ViewModel() {

    val invoices: StateFlow<List<Invoice>> = repository.invoicesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun createInvoice(invoice: Invoice) {
        viewModelScope.launch {
            repository.createInvoice(invoice)
        }
    }

    fun updateInvoice(invoice: Invoice) {
        viewModelScope.launch {
            repository.updateInvoice(invoice)
        }
    }

    fun deleteInvoice(invoice: Invoice) {
        viewModelScope.launch {
            repository.deleteInvoice(invoice)
        }
    }
}

class AppViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    private val database = AppDatabase.getDatabase(application)
    private val apiService = RetrofitClient.apiService

    private val authRepo = AuthRepository(
        database.userDao(),
        database.petOwnerDao()
    )
    private val ownerRepo = PetOwnerRepository(
        database.petOwnerDao(),
        apiService
    )
    private val animalRepo = AnimalRepository(
        database.animalDao(),
        apiService
    )
    private val appointmentRepo = AppointmentRepository(
        database.appointmentDao(),
        apiService
    )
    private val treatmentRepo = TreatmentRepository(
        database.treatmentDao(),
        apiService
    )
    private val invoiceRepo = InvoiceRepository(
        database.invoiceDao(),
        apiService
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authRepo) as T

            modelClass.isAssignableFrom(PetOwnerViewModel::class.java) ->
                PetOwnerViewModel(ownerRepo) as T

            modelClass.isAssignableFrom(AnimalViewModel::class.java) ->
                AnimalViewModel(animalRepo) as T

            modelClass.isAssignableFrom(AppointmentViewModel::class.java) ->
                AppointmentViewModel(appointmentRepo) as T

            modelClass.isAssignableFrom(TreatmentViewModel::class.java) ->
                TreatmentViewModel(treatmentRepo) as T

            modelClass.isAssignableFrom(BillingViewModel::class.java) ->
                BillingViewModel(invoiceRepo) as T

            else -> throw IllegalArgumentException(
                "Unknown ViewModel class: ${modelClass.name}"
            )
        }
    }
}
