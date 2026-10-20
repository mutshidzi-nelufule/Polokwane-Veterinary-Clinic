package com.example.polokwanevetclinic.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.polokwanevetclinic.model.AppointmentStatus
import com.example.polokwanevetclinic.model.InvoiceStatus
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.AppointmentViewModel
import com.example.polokwanevetclinic.viewmodel.BillingViewModel

@Composable
fun ReportsScreen(
    appointmentViewModel: AppointmentViewModel,
    billingViewModel: BillingViewModel,
    animalViewModel: AnimalViewModel
) {
    val appointments by appointmentViewModel.appointments.collectAsState()
    val invoices by billingViewModel.invoices.collectAsState()
    val animals by animalViewModel.animals.collectAsState()

    val totalAppts = appointments.size
    val completedAppts = appointments.count { it.status == AppointmentStatus.COMPLETED }
    val scheduledAppts = appointments.count { it.status == AppointmentStatus.SCHEDULED }

    val totalRevenue = invoices.filter { it.status == InvoiceStatus.PAID }.sumOf { it.totalAmount }
    val unpaidRevenue = invoices.filter { it.status == InvoiceStatus.UNPAID }.sumOf { it.totalAmount }

    val dogsCount = animals.count { it.species.equals("Dog", ignoreCase = true) }
    val catsCount = animals.count { it.species.equals("Cat", ignoreCase = true) }
    val otherCount = animals.size - (dogsCount + catsCount)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Clinic Operational Reports",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ReportSummaryCard(
            title = "Appointments Summary",
            detail1 = "Total Scheduled: $totalAppts",
            detail2 = "Upcoming: $scheduledAppts | Completed: $completedAppts"
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReportSummaryCard(
            title = "Financial & Billing Summary",
            detail1 = "Paid Revenue: R ${"%.2f".format(totalRevenue)}",
            detail2 = "Outstanding Unpaid Invoices: R ${"%.2f".format(unpaidRevenue)}"
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReportSummaryCard(
            title = "Patient Species Breakdown",
            detail1 = "Dogs: $dogsCount | Cats: $catsCount | Other: $otherCount",
            detail2 = "Total Registered Patients: ${animals.size}"
        )
    }
}

@Composable
fun ReportSummaryCard(
    title: String,
    detail1: String,
    detail2: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = detail1, style = MaterialTheme.typography.bodyMedium)
            Text(text = detail2, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
