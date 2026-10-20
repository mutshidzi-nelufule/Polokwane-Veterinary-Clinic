package com.example.polokwanevetclinic.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.polokwanevetclinic.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var isRegisterMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var isSuccessMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Polokwane Vet Clinic",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = if (isRegisterMode) {
                "Create an Account"
            } else {
                "Sign In to Your Account"
            },
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        message?.let {
            Text(
                text = it,
                color = if (isSuccessMessage) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        if (isRegisterMode) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                singleLine = true,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    message = null
                    isSuccessMessage = false

                    if (isRegisterMode && fullName.isBlank()) {
                        message = "Please enter your full name."
                        return@Button
                    }

                    if (email.isBlank() ||
                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim()).matches()
                    ) {
                        message = "Please enter a valid email address."
                        return@Button
                    }

                    if (password.isBlank()) {
                        message = "Please enter your password."
                        return@Button
                    }

                    if (isRegisterMode && password.length < 8) {
                        message = "Your password must contain at least 8 characters."
                        return@Button
                    }

                    isLoading = true

                    coroutineScope.launch {
                        try {
                            val result = if (isRegisterMode) {
                                authViewModel.register(
                                    fullName.trim(),
                                    email.trim(),
                                    password
                                )
                            } else {
                                authViewModel.login(
                                    email.trim(),
                                    password
                                )
                            }

                            if (result.isSuccess) {
                                if (isRegisterMode) {
                                    isSuccessMessage = true
                                    message = "Account created successfully! You can now sign in with your email and password."
                                    isRegisterMode = false
                                    password = ""
                                } else {
                                    onLoginSuccess()
                                }
                            } else {
                                message = result.exceptionOrNull()?.message
                                    ?: "Authentication failed. Please try again."
                            }
                        } catch (e: Exception) {
                            message = e.message
                                ?: "Something went wrong. Please try again."
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    if (isRegisterMode) {
                        "Register Account"
                    } else {
                        "Sign In"
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            enabled = !isLoading,
            onClick = {
                isRegisterMode = !isRegisterMode
                message = null
                isSuccessMessage = false
            }
        ) {
            Text(
                if (isRegisterMode) {
                    "Already have an account? Sign In"
                } else {
                    "Don't have an account? Register"
                }
            )
        }
    }
}
