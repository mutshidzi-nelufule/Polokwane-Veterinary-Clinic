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
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.InvoiceItem
import com.example.polokwanevetclinic.model.InvoiceStatus
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.BillingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BillingScreen(
    billingViewModel: BillingViewModel,
    animalViewModel: AnimalViewModel
) {
    val invoices by billingViewModel.invoices.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var invoiceToEdit by remember { mutableStateOf<Invoice?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Billing & Invoices",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(invoices) { invoice ->
                    InvoiceCard(
                        invoice = invoice,
                        onEditClick = { invoiceToEdit = invoice }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Create Invoice")
        }

        if (showCreateDialog) {
            InvoiceFormDialog(
                title = "Generate New Invoice",
                animals = animals,
                initialInvoice = null,
                onDismiss = { showCreateDialog = false },
                onSave = { newInvoice ->
                    billingViewModel.createInvoice(newInvoice)
                    showCreateDialog = false
                }
            )
        }

        invoiceToEdit?.let { existingInvoice ->
            InvoiceFormDialog(
                title = "Edit Invoice Details",
                animals = animals,
                initialInvoice = existingInvoice,
                onDismiss = { invoiceToEdit = null },
                onSave = { updatedInvoice ->
                    billingViewModel.updateInvoice(updatedInvoice)
                    invoiceToEdit = null
                },
                onDelete = { invoiceToDelete ->
                    billingViewModel.deleteInvoice(invoiceToDelete)
                    invoiceToEdit = null
                }
            )
        }
    }
}

@Composable
fun InvoiceFormDialog(
    title: String,
    animals: List<Animal>,
    initialInvoice: Invoice?,
    onDismiss: () -> Unit,
    onSave: (Invoice) -> Unit,
    onDelete: ((Invoice) -> Unit)? = null
) {
    var selectedAnimal by remember {
        mutableStateOf(
            animals.find { initialInvoice != null && it.ownerId == initialInvoice.ownerId && it.name == initialInvoice.animalName } ?: animals.firstOrNull()
        )
    }
    var animalDropdownExpanded by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf(initialInvoice?.items?.firstOrNull()?.description ?: "Veterinary Services") }
    var amountText by remember { mutableStateOf(initialInvoice?.totalAmount?.toString() ?: "") }
    var status by remember { mutableStateOf(initialInvoice?.status ?: InvoiceStatus.UNPAID) }
    var statusDropdownExpanded by remember { mutableStateOf(false) }
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
                        label = { Text("Patient & Owner") },
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
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Service / Product Description *") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Total Amount (R) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = status.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Payment Status") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { statusDropdownExpanded = true }
                    )
                    DropdownMenu(
                        expanded = statusDropdownExpanded,
                        onDismissRequest = { statusDropdownExpanded = false }
                    ) {
                        InvoiceStatus.entries.forEach { invStatus ->
                            DropdownMenuItem(
                                text = { Text(invStatus.name) },
                                onClick = {
                                    status = invStatus
                                    statusDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row {
                if (initialInvoice != null && onDelete != null) {
                    Button(
                        onClick = { onDelete(initialInvoice) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }
                Button(
                    onClick = {
                        val amount = amountText.toDoubleOrNull()
                        if (selectedAnimal == null || description.isBlank() || amount == null || amount <= 0) {
                            error = "Select a patient, enter a description and valid positive amount."
                            return@Button
                        }
                        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val invoiceId = initialInvoice?.id ?: "INV-${(1000..9999).random()}"
                        val invoice = Invoice(
                            id = invoiceId,
                            ownerId = selectedAnimal!!.ownerId,
                            ownerName = selectedAnimal!!.ownerName,
                            animalName = selectedAnimal!!.name,
                            invoiceDate = initialInvoice?.invoiceDate ?: currentDate,
                            totalAmount = amount,
                            status = status,
                            items = listOf(InvoiceItem(description.trim(), amount))
                        )
                        onSave(invoice)
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
fun InvoiceCard(
    invoice: Invoice,
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
                    text = "${invoice.id} - ${invoice.ownerName}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = invoice.status.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = when (invoice.status) {
                        InvoiceStatus.PAID -> MaterialTheme.colorScheme.primary
                        InvoiceStatus.UNPAID -> MaterialTheme.colorScheme.error
                        InvoiceStatus.OVERDUE -> MaterialTheme.colorScheme.error
                    }
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Invoice"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Patient: ${invoice.animalName} | Date: ${invoice.invoiceDate}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total Amount: R ${"%.2f".format(invoice.totalAmount)}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
