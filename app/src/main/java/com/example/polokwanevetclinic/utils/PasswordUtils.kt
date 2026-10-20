package com.example.polokwanevetclinic.utils

import java.security.MessageDigest

object PasswordUtils {

    /**
     * Hashes a password string using SHA-256.
     * Note on local authentication limitations:
     * SHA-256 provides basic non-plaintext password protection on device storage.
     * For production systems, salted hashing (such as PBKDF2 or BCrypt) and server-side verification
     * are recommended to mitigate against local rainbow-table attacks.
     */
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Validates that the provided email address follows a standard format.
     */
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }
}
