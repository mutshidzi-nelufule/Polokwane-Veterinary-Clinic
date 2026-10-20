package com.example.polokwanevetclinic.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polokwanevetclinic.model.Animal
import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.AppointmentStatus
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.AppointmentViewModel

@Composable
fun AppointmentsScreen(
    appointmentViewModel: AppointmentViewModel,
    animalViewModel: AnimalViewModel
) {
    val appointments by appointmentViewModel.appointments.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

    var showScheduleDialog by remember { mutableStateOf(false) }
    var appointmentToEdit by remember { mutableStateOf<Appointment?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Appointment Schedule",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(appointments) { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onEditClick = { appointmentToEdit = appointment }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { showScheduleDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Schedule Appointment")
        }

        if (showScheduleDialog) {
            AppointmentFormDialog(
                title = "Schedule Appointment",
                animals = animals,
                initialAppt = null,
                onDismiss = { showScheduleDialog = false },
                onSave = { newAppt ->
                    appointmentViewModel.scheduleAppointment(newAppt)
                    showScheduleDialog = false
                }
            )
        }

        appointmentToEdit?.let { existingAppt ->
            AppointmentFormDialog(
                title = "Edit Appointment Details",
                animals = animals,
                initialAppt = existingAppt,
                onDismiss = { appointmentToEdit = null },
                onSave = { updatedAppt ->
                    appointmentViewModel.updateAppointment(updatedAppt)
                    appointmentToEdit = null
                },
                onDelete = { apptToDelete ->
                    appointmentViewModel.deleteAppointment(apptToDelete)
                    appointmentToEdit = null
                }
            )
        }
    }
}

@Composable
fun AppointmentFormDialog(
    title: String,
    animals: List<Animal>,
    initialAppt: Appointment?,
    onDismiss: () -> Unit,
    onSave: (Appointment) -> Unit,
    onDelete: ((Appointment) -> Unit)? = null
) {
    var selectedAnimal by remember {
        mutableStateOf(
            animals.find { it.id == initialAppt?.animalId } ?: animals.firstOrNull()
        )
    }
    var animalDropdownExpanded by remember { mutableStateOf(false) }

    var apptDate by remember { mutableStateOf(initialAppt?.appointmentDate ?: "2024-11-15") }
    var timeSlot by remember { mutableStateOf(initialAppt?.timeSlot ?: "09:00 AM") }
    var reason by remember { mutableStateOf(initialAppt?.reason ?: "") }
    var status by remember { mutableStateOf(initialAppt?.status ?: AppointmentStatus.SCHEDULED) }
    var statusDropdownExpanded by remember { mutableStateOf(false) }
    var vetName by remember { mutableStateOf(initialAppt?.vetName ?: "Dr. M. Tshivhase") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                if (error != null) {
                    Text(text = error!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedAnimal?.let { "${it.name} (${it.ownerName})" } ?: "Select Patient *",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Patient") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { animalDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = animalDropdownExpanded,
                        onDismissRequest = { animalDropdownExpanded = false }
                    ) {
                        animals.forEach { animal ->
                            DropdownMenuItem(
                                text = { Text("${animal.name} (${animal.ownerName})") },
                                onClick = {
                                    selectedAnimal = animal
                                    animalDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = apptDate,
                    onValueChange = { apptDate = it },
                    label = { Text("Date (YYYY-MM-DD) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = timeSlot,
                    onValueChange = { timeSlot = it },
                    label = { Text("Time Slot *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Reason for Visit *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = status.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { statusDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = statusDropdownExpanded,
                        onDismissRequest = { statusDropdownExpanded = false }
                    ) {
                        AppointmentStatus.entries.forEach { apptStatus ->
                            DropdownMenuItem(
                                text = { Text(apptStatus.name) },
                                onClick = {
                                    status = apptStatus
                                    statusDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = vetName,
                    onValueChange = { vetName = it },
                    label = { Text("Attending Veterinarian") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row {
                if (initialAppt != null && onDelete != null) {
                    Button(
                        onClick = { onDelete(initialAppt) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        if (selectedAnimal == null || apptDate.isBlank() || timeSlot.isBlank() || reason.isBlank()) {
                            error = "Please fill in all required fields."
                            return@Button
                        }
                        val appt = Appointment(
                            id = initialAppt?.id ?: System.currentTimeMillis().toString(),
                            animalId = selectedAnimal!!.id,
                            animalName = selectedAnimal!!.name,
                            ownerId = selectedAnimal!!.ownerId,
                            ownerName = selectedAnimal!!.ownerName,
                            appointmentDate = apptDate.trim(),
                            timeSlot = timeSlot.trim(),
                            reason = reason.trim(),
                            status = status,
                            vetName = vetName.trim()
                        )
                        onSave(appt)
                    }
                ) {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${appointment.animalName} (${appointment.ownerName})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = appointment.status.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = when (appointment.status) {
                        AppointmentStatus.SCHEDULED -> MaterialTheme.colorScheme.primary
                        AppointmentStatus.COMPLETED -> MaterialTheme.colorScheme.secondary
                        AppointmentStatus.CANCELLED -> MaterialTheme.colorScheme.error
                    }
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Appointment"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date & Time: ${appointment.appointmentDate} at ${appointment.timeSlot}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Reason: ${appointment.reason}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Attending Vet: ${appointment.vetName}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
