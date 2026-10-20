package com.example.polokwanevetclinic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.polokwanevetclinic.ui.screens.AnimalsScreen
import com.example.polokwanevetclinic.ui.screens.AppointmentsScreen
import com.example.polokwanevetclinic.ui.screens.AuthScreen
import com.example.polokwanevetclinic.ui.screens.BillingScreen
import com.example.polokwanevetclinic.ui.screens.DashboardScreen
import com.example.polokwanevetclinic.ui.screens.PetOwnersScreen
import com.example.polokwanevetclinic.ui.screens.ReportsScreen
import com.example.polokwanevetclinic.ui.screens.SettingsScreen
import com.example.polokwanevetclinic.ui.screens.TreatmentsScreen
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.AppointmentViewModel
import com.example.polokwanevetclinic.viewmodel.AuthViewModel
import com.example.polokwanevetclinic.viewmodel.BillingViewModel
import com.example.polokwanevetclinic.viewmodel.PetOwnerViewModel
import com.example.polokwanevetclinic.viewmodel.TreatmentViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    ownerViewModel: PetOwnerViewModel,
    animalViewModel: AnimalViewModel,
    appointmentViewModel: AppointmentViewModel,
    treatmentViewModel: TreatmentViewModel,
    billingViewModel: BillingViewModel,
    modifier: Modifier = Modifier,
    startDestination: String = NavRoute.Dashboard.route,
    onLoginSuccess: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavRoute.Auth.route) {
            AuthScreen(
                authViewModel = authViewModel,
                onLoginSuccess = onLoginSuccess
            )
        }
        composable(NavRoute.Dashboard.route) {
            DashboardScreen(
                ownerViewModel = ownerViewModel,
                animalViewModel = animalViewModel,
                appointmentViewModel = appointmentViewModel,
                billingViewModel = billingViewModel,
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(NavRoute.PetOwners.route) {
            PetOwnersScreen(viewModel = ownerViewModel)
        }
        composable(NavRoute.Animals.route) {
            AnimalsScreen(
                animalViewModel = animalViewModel,
                ownerViewModel = ownerViewModel
            )
        }
        composable(NavRoute.Appointments.route) {
            AppointmentsScreen(
                appointmentViewModel = appointmentViewModel,
                animalViewModel = animalViewModel
            )
        }
        composable(NavRoute.Treatments.route) {
            TreatmentsScreen(
                treatmentViewModel = treatmentViewModel,
                animalViewModel = animalViewModel
            )
        }
        composable(NavRoute.Billing.route) {
            BillingScreen(
                billingViewModel = billingViewModel,
                animalViewModel = animalViewModel
            )
        }
        composable(NavRoute.Reports.route) {
            ReportsScreen(
                appointmentViewModel = appointmentViewModel,
                billingViewModel = billingViewModel,
                animalViewModel = animalViewModel
            )
        }
        composable(NavRoute.Settings.route) {
            SettingsScreen(
                onLogout = onLogout
            )
        }
    }
}
