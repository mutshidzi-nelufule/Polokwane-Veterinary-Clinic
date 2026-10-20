package com.example.polokwanevetclinic

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.polokwanevetclinic.navigation.AppNavHost
import com.example.polokwanevetclinic.navigation.NavRoute
import com.example.polokwanevetclinic.ui.components.AppBottomBar
import com.example.polokwanevetclinic.ui.components.AppDrawerContent
import com.example.polokwanevetclinic.ui.components.AppTopBar
import com.example.polokwanevetclinic.ui.theme.PolokwaneVetClinicTheme
import com.example.polokwanevetclinic.viewmodel.AnimalViewModel
import com.example.polokwanevetclinic.viewmodel.AppViewModelFactory
import com.example.polokwanevetclinic.viewmodel.AppointmentViewModel
import com.example.polokwanevetclinic.viewmodel.AuthViewModel
import com.example.polokwanevetclinic.viewmodel.BillingViewModel
import com.example.polokwanevetclinic.viewmodel.PetOwnerViewModel
import com.example.polokwanevetclinic.viewmodel.TreatmentViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PolokwaneVetClinicTheme {
                PolokwaneVetClinicApp()
            }
        }
    }
}

@Composable
fun PolokwaneVetClinicApp() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val factory = remember { AppViewModelFactory(application) }

    val authViewModel: AuthViewModel = viewModel(factory = factory)
    val ownerViewModel: PetOwnerViewModel = viewModel(factory = factory)
    val animalViewModel: AnimalViewModel = viewModel(factory = factory)
    val appointmentViewModel: AppointmentViewModel = viewModel(factory = factory)
    val treatmentViewModel: TreatmentViewModel = viewModel(factory = factory)
    val billingViewModel: BillingViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var isAuthenticated by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: NavRoute.Dashboard.route

    val isAuthScreen = currentRoute == NavRoute.Auth.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !isAuthScreen,
        drawerContent = {
            AppDrawerContent(
                currentRoute = currentRoute,
                onNavigate = { navRoute ->
                    coroutineScope.launch { drawerState.close() }

                    navController.navigate(navRoute.route) {
                        popUpTo(NavRoute.Dashboard.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (!isAuthScreen) {
                    val title = when (currentRoute) {
                        NavRoute.Dashboard.route -> "Dashboard"
                        NavRoute.PetOwners.route -> "Pet Owners"
                        NavRoute.Animals.route -> "Patients / Pets"
                        NavRoute.Appointments.route -> "Appointments"
                        NavRoute.Treatments.route -> "Treatments"
                        NavRoute.Billing.route -> "Billing & Invoices"
                        NavRoute.Reports.route -> "Reports"
                        NavRoute.Settings.route -> "Settings"
                        else -> "Polokwane Vet Clinic"
                    }

                    AppTopBar(
                        title = title,
                        onOpenDrawer = {
                            coroutineScope.launch { drawerState.open() }
                        }
                    )
                }
            },
            bottomBar = {
                if (!isAuthScreen) {
                    AppBottomBar(
                        currentRoute = currentRoute,
                        onNavigate = { navRoute ->
                            navController.navigate(navRoute.route) {
                                popUpTo(NavRoute.Dashboard.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                authViewModel = authViewModel,
                ownerViewModel = ownerViewModel,
                animalViewModel = animalViewModel,
                appointmentViewModel = appointmentViewModel,
                treatmentViewModel = treatmentViewModel,
                billingViewModel = billingViewModel,
                modifier = Modifier.padding(innerPadding),
                startDestination = if (isAuthenticated) {
                    NavRoute.Dashboard.route
                } else {
                    NavRoute.Auth.route
                },
                onLoginSuccess = {
                    isAuthenticated = true
                    navController.navigate(NavRoute.Dashboard.route) {
                        popUpTo(NavRoute.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onLogout = {
                    isAuthenticated = false
                    navController.navigate(NavRoute.Auth.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
