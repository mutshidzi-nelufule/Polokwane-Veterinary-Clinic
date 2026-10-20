package com.example.polokwanevetclinic.data.remote

/**
 * Request payload for login endpoint.
 */
data class LoginRequestDto(
    val email: String,
    val passwordHash: String
)

/**
 * Request payload for user registration endpoint.
 */
data class RegisterRequestDto(
    val fullName: String,
    val email: String,
    val passwordHash: String,
    val role: String = "PET_OWNER"
)

/**
 * Response returned upon successful authentication.
 */
data class AuthResponseDto(
    val token: String,
    val userId: String,
    val fullName: String,
    val email: String,
    val role: String
)

/**
 * Standard API status message.
 */
data class ApiMessageDto(
    val success: Boolean,
    val message: String
)
