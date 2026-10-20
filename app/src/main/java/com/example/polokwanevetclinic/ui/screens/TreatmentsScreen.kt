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
import com.example.polokwanevetclinic.model.Treatment
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.TreatmentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TreatmentsScreen(
    treatmentViewModel: TreatmentViewModel,
    animalViewModel: AnimalViewModel
) {
    val treatments by treatmentViewModel.treatments.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var treatmentToEdit by remember { mutableStateOf<Treatment?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Treatment & Medical Records",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(treatments) { treatment ->
                    TreatmentCard(
                        treatment = treatment,
                        onEditClick = { treatmentToEdit = treatment }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Medical Record")
        }

        if (showAddDialog) {
            TreatmentFormDialog(
                title = "Record Medical Treatment",
                animals = animals,
                initialTreatment = null,
                onDismiss = { showAddDialog = false },
                onSave = { newTreatment ->
                    treatmentViewModel.recordTreatment(newTreatment)
                    showAddDialog = false
                }
            )
        }

        treatmentToEdit?.let { existingTreatment ->
            TreatmentFormDialog(
                title = "Edit Treatment Record",
                animals = animals,
                initialTreatment = existingTreatment,
                onDismiss = { treatmentToEdit = null },
                onSave = { updatedTreatment ->
                    treatmentViewModel.updateTreatment(updatedTreatment)
                    treatmentToEdit = null
                },
                onDelete = { treatmentToDelete ->
                    treatmentViewModel.deleteTreatment(treatmentToDelete)
                    treatmentToEdit = null
                }
            )
        }
    }
}

@Composable
fun TreatmentFormDialog(
    title: String,
    animals: List<Animal>,
    initialTreatment: Treatment?,
    onDismiss: () -> Unit,
    onSave: (Treatment) -> Unit,
    onDelete: ((Treatment) -> Unit)? = null
) {
    var selectedAnimal by remember {
        mutableStateOf(
            animals.find { it.id == initialTreatment?.animalId } ?: animals.firstOrNull()
        )
    }
    var animalDropdownExpanded by remember { mutableStateOf(false) }

    var vetName by remember { mutableStateOf(initialTreatment?.vetName ?: "Dr. M. Tshivhase") }
    var diagnosis by remember { mutableStateOf(initialTreatment?.diagnosis ?: "") }
    var description by remember { mutableStateOf(initialTreatment?.treatmentDescription ?: "") }
    var costText by remember { mutableStateOf(initialTreatment?.cost?.toString() ?: "") }
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
                    value = diagnosis,
                    onValueChange = { diagnosis = it },
                    label = { Text("Diagnosis *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Treatment Description *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = costText,
                    onValueChange = { costText = it },
                    label = { Text("Treatment Cost (R) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

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
                if (initialTreatment != null && onDelete != null) {
                    Button(
                        onClick = { onDelete(initialTreatment) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        val cost = costText.toDoubleOrNull()
                        if (selectedAnimal == null || diagnosis.isBlank() || description.isBlank() || cost == null) {
                            error = "Patient, Diagnosis, Description and a valid Cost are required."
                            return@Button
                        }
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val treatment = Treatment(
                            id = initialTreatment?.id ?: System.currentTimeMillis().toString(),
                            animalId = selectedAnimal!!.id,
                            animalName = selectedAnimal!!.name,
                            vetName = vetName.trim(),
                            diagnosis = diagnosis.trim(),
                            treatmentDescription = description.trim(),
                            cost = cost,
                            date = initialTreatment?.date ?: currentDate
                        )
                        onSave(treatment)
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
fun TreatmentCard(
    treatment: Treatment,
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
                    text = "${treatment.animalName} - ${treatment.diagnosis}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "R ${"%.2f".format(treatment.cost)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Treatment"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Description: ${treatment.treatmentDescription}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Attending Vet: ${treatment.vetName} | Date: ${treatment.date}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
