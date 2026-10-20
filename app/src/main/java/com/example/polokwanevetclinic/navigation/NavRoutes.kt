package com.example.polokwanevetclinic.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screen routes for application navigation.
 */
sealed class NavRoute(val route: String, val title: String, val icon: ImageVector? = null) {
    data object Auth : NavRoute("auth", "Authentication")
    data object Dashboard : NavRoute("dashboard", "Dashboard", Icons.Default.Dashboard)
    data object PetOwners : NavRoute("pet_owners", "Pet Owners", Icons.Default.People)
    data object Animals : NavRoute("animals", "Patients / Pets", Icons.Default.Pets)
    data object Appointments : NavRoute("appointments", "Appointments", Icons.Default.CalendarMonth)
    data object Treatments : NavRoute("treatments", "Treatments", Icons.Default.MedicalServices)
    data object Billing : NavRoute("billing", "Billing & Invoices", Icons.Default.Payments)
    data object Reports : NavRoute("reports", "Reports", Icons.Default.Assessment)
    data object Settings : NavRoute("settings", "Settings", Icons.Default.Settings)
}

/**
 * Main bottom navigation destinations displayed on the bottom bar.
 */
val bottomNavItems = listOf(
    NavRoute.Dashboard,
    NavRoute.PetOwners,
    NavRoute.Animals,
    NavRoute.Appointments,
    NavRoute.Billing
)

/**
 * Navigation items displayed in the Navigation Drawer.
 */
val drawerNavItems = listOf(
    NavRoute.Dashboard,
    NavRoute.PetOwners,
    NavRoute.Animals,
    NavRoute.Appointments,
    NavRoute.Treatments,
    NavRoute.Billing,
    NavRoute.Reports,
    NavRoute.Settings
)
