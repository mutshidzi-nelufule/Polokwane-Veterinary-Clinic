package com.example.polokwanevetclinic.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.polokwanevetclinic.model.AppointmentStatus
import com.example.polokwanevetclinic.model.InvoiceStatus
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.AppointmentViewModel
import com.example.polokwanevetclinic.viewmodel.BillingViewModel
import com.example.polokwanevetclinic.viewmodel.PetOwnerViewModel

@Composable
fun DashboardScreen(
    ownerViewModel: PetOwnerViewModel,
    animalViewModel: AnimalViewModel,
    appointmentViewModel: AppointmentViewModel,
    billingViewModel: BillingViewModel,
    onNavigateTo: (String) -> Unit
) {
    val owners by ownerViewModel.owners.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val appointments by appointmentViewModel.appointments.collectAsState()
    val invoices by billingViewModel.invoices.collectAsState()

    val upcomingCount = appointments.count { it.status == AppointmentStatus.SCHEDULED }
    val unpaidTotal = invoices.filter { it.status == InvoiceStatus.UNPAID }.sumOf { it.totalAmount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome to Polokwane Vet Clinic",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardStatCard(
                title = "Registered Pets",
                value = animals.size.toString(),
                icon = Icons.Default.Pets,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTo("animals") }
            )
            DashboardStatCard(
                title = "Pet Owners",
                value = owners.size.toString(),
                icon = Icons.Default.People,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTo("pet_owners") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DashboardStatCard(
                title = "Upcoming Appts",
                value = upcomingCount.toString(),
                icon = Icons.Default.CalendarMonth,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTo("appointments") }
            )
            DashboardStatCard(
                title = "Unpaid Invoices",
                value = "R ${"%.2f".format(unpaidTotal)}",
                icon = Icons.Default.Payments,
                modifier = Modifier.weight(1f),
                onClick = { onNavigateTo("billing") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "System Summary",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "System Status",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Syncing with local Room Database & REST Backend service.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
    }
}
