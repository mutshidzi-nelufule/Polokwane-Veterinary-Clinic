package com.example.polokwanevetclinic.ui.screens

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
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.viewmodel.PetOwnerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PetOwnersScreen(viewModel: PetOwnerViewModel) {
    val owners by viewModel.owners.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var ownerToEdit by remember { mutableStateOf<PetOwner?>(null) }

    val filteredOwners = owners.filter {
        it.fullName.contains(searchQuery, ignoreCase = true) ||
                it.phone.contains(searchQuery) ||
                it.email.contains(searchQuery, ignoreCase = true)
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
                label = { Text("Search Pet Owners") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredOwners.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No pet owners found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn {
                    items(filteredOwners) { owner ->
                        PetOwnerCard(
                            owner = owner,
                            onEditClick = { ownerToEdit = owner }
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
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pet Owner")
        }

        if (showAddDialog) {
            PetOwnerFormDialog(
                title = "Register New Pet Owner",
                initialOwner = null,
                onDismiss = { showAddDialog = false },
                onSave = { newOwner ->
                    viewModel.addOwner(newOwner)
                    showAddDialog = false
                }
            )
        }

        ownerToEdit?.let { existingOwner ->
            PetOwnerFormDialog(
                title = "Edit Pet Owner Details",
                initialOwner = existingOwner,
                onDismiss = { ownerToEdit = null },
                onSave = { updatedOwner ->
                    viewModel.updateOwner(updatedOwner)
                    ownerToEdit = null
                },
                onDelete = { ownerToDelete ->
                    viewModel.deleteOwner(ownerToDelete)
                    ownerToEdit = null
                }
            )
        }
    }
}

@Composable
fun PetOwnerFormDialog(
    title: String,
    initialOwner: PetOwner?,
    onDismiss: () -> Unit,
    onSave: (PetOwner) -> Unit,
    onDelete: ((PetOwner) -> Unit)? = null
) {
    var fullName by remember { mutableStateOf(initialOwner?.fullName ?: "") }
    var email by remember { mutableStateOf(initialOwner?.email ?: "") }
    var phone by remember { mutableStateOf(initialOwner?.phone ?: "") }
    var address by remember { mutableStateOf(initialOwner?.address ?: "") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                if (error != null) {
                    Text(
                        text = error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Physical Address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Row {
                if (initialOwner != null && onDelete != null) {
                    Button(
                        onClick = { onDelete(initialOwner) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        if (fullName.isBlank() || email.isBlank() || phone.isBlank()) {
                            error = "Full Name, Email and Phone are required."
                            return@Button
                        }
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val owner = PetOwner(
                            id = initialOwner?.id ?: System.currentTimeMillis().toString(),
                            fullName = fullName.trim(),
                            email = email.trim(),
                            phone = phone.trim(),
                            address = address.trim(),
                            registeredDate = initialOwner?.registeredDate ?: currentDate
                        )
                        onSave(owner)
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
fun PetOwnerCard(
    owner: PetOwner,
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
                    text = owner.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Pet Owner"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Email: ${owner.email}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Phone: ${owner.phone}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Address: ${owner.address}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
