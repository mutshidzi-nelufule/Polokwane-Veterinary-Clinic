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
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.PetOwnerViewModel

@Composable
fun AnimalsScreen(
    animalViewModel: AnimalViewModel,
    ownerViewModel: PetOwnerViewModel
) {
    val animals by animalViewModel.animals.collectAsState()
    val owners by ownerViewModel.owners.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var animalToEdit by remember { mutableStateOf<Animal?>(null) }

    val filteredAnimals = animals.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.species.contains(searchQuery, ignoreCase = true) ||
                it.ownerName.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Animals / Patients") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredAnimals.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No animal records found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn {
                    items(filteredAnimals) { animal ->
                        AnimalCard(
                            animal = animal,
                            onEditClick = { animalToEdit = animal }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Register Patient")
        }

        if (showAddDialog) {
            AnimalFormDialog(
                title = "Register New Patient",
                owners = owners,
                initialAnimal = null,
                onDismiss = { showAddDialog = false },
                onSave = { newAnimal ->
                    animalViewModel.addAnimal(newAnimal)
                    showAddDialog = false
                }
            )
        }

        animalToEdit?.let { existingAnimal ->
            AnimalFormDialog(
                title = "Edit Patient Details",
                owners = owners,
                initialAnimal = existingAnimal,
                onDismiss = { animalToEdit = null },
                onSave = { updatedAnimal ->
                    animalViewModel.updateAnimal(updatedAnimal)
                    animalToEdit = null
                },
                onDelete = { animalToDelete ->
                    animalViewModel.deleteAnimal(animalToDelete)
                    animalToEdit = null
                }
            )
        }
    }
}

@Composable
fun AnimalFormDialog(
    title: String,
    owners: List<PetOwner>,
    initialAnimal: Animal?,
    onDismiss: () -> Unit,
    onSave: (Animal) -> Unit,
    onDelete: ((Animal) -> Unit)? = null
) {
    var name by remember { mutableStateOf(initialAnimal?.name ?: "") }
    var species by remember { mutableStateOf(initialAnimal?.species ?: "") }
    var breed by remember { mutableStateOf(initialAnimal?.breed ?: "") }
    var age by remember { mutableStateOf(initialAnimal?.dateOfBirthOrAge ?: "") }
    var sex by remember { mutableStateOf(initialAnimal?.sex.takeIf { !it.isNullOrBlank() } ?: "Male") }
    var medicalNotes by remember { mutableStateOf(initialAnimal?.medicalNotes ?: "") }

    var selectedOwner by remember {
        mutableStateOf(
            owners.find { it.id == initialAnimal?.ownerId } ?: owners.firstOrNull()
        )
    }
    var ownerDropdownExpanded by remember { mutableStateOf(false) }
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
                        value = selectedOwner?.fullName ?: "Select Owner *",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Pet Owner") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { ownerDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = ownerDropdownExpanded,
                        onDismissRequest = { ownerDropdownExpanded = false }
                    ) {
                        owners.forEach { owner ->
                            DropdownMenuItem(
                                text = { Text(owner.fullName) },
                                onClick = {
                                    selectedOwner = owner
                                    ownerDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Pet Name *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Species (e.g., Dog, Cat) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("Breed") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age / Date of Birth") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = medicalNotes,
                    onValueChange = { medicalNotes = it },
                    label = { Text("Medical Notes") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row {
                if (initialAnimal != null && onDelete != null) {
                    Button(
                        onClick = { onDelete(initialAnimal) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        if (name.isBlank() || species.isBlank() || selectedOwner == null) {
                            error = "Owner, Pet Name and Species are required."
                            return@Button
                        }
                        val animal = Animal(
                            id = initialAnimal?.id ?: System.currentTimeMillis().toString(),
                            ownerId = selectedOwner!!.id,
                            ownerName = selectedOwner!!.fullName,
                            name = name.trim(),
                            species = species.trim(),
                            breed = breed.trim(),
                            dateOfBirthOrAge = age.trim(),
                            sex = sex,
                            medicalNotes = medicalNotes.trim()
                        )
                        onSave(animal)
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
fun AnimalCard(
    animal: Animal,
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
                    text = "${animal.name} (${animal.species} - ${animal.breed})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Patient"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Owner: ${animal.ownerName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Age: ${animal.dateOfBirthOrAge} | Sex: ${animal.sex}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (animal.medicalNotes.isNotBlank()) {
                Text(
                    text = "Notes: ${animal.medicalNotes}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
