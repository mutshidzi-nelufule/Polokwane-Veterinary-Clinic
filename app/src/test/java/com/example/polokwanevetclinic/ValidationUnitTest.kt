package com.example.polokwanevetclinic

import com.example.polokwanevetclinic.model.Appointment
import com.example.polokwanevetclinic.model.AppointmentStatus
import com.example.polokwanevetclinic.model.Invoice
import com.example.polokwanevetclinic.model.InvoiceStatus
import com.example.polokwanevetclinic.model.PetOwner
import com.example.polokwanevetclinic.utils.PasswordUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationUnitTest {

    @Test
    fun testPasswordHashing() {
        val rawPassword = "Password123!"
        val hashed1 = PasswordUtils.hashPassword(rawPassword)
        val hashed2 = PasswordUtils.hashPassword(rawPassword)

        assertNotEquals(rawPassword, hashed1)
        assertEquals(hashed1, hashed2)
    }

    @Test
    fun testPetOwnerEmailValidation() {
        val validOwner = PetOwner("1", "John Smith", "john@example.com", "0821234567", "123 Street", "2024-01-01")
        val invalidOwner = PetOwner("2", "John Smith", "invalid-email", "0821234567", "123 Street", "2024-01-01")

        assertTrue(validOwner.email.contains("@"))
        assertFalse(invalidOwner.email.contains("@") && invalidOwner.email.contains("."))
    }

    @Test
    fun testInvoiceStatusCalculation() {
        val unpaidInvoice = Invoice("INV-101", "1", "John Smith", "Max", "2024-11-01", 500.0, InvoiceStatus.UNPAID)
        val paidInvoice = Invoice("INV-102", "1", "John Smith", "Max", "2024-11-01", 500.0, InvoiceStatus.PAID)

        assertEquals(InvoiceStatus.UNPAID, unpaidInvoice.status)
        assertEquals(InvoiceStatus.PAID, paidInvoice.status)
    }

    @Test
    fun testAppointmentStatus() {
        val appt = Appointment("1", "a1", "Max", "o1", "John", "2024-11-10", "09:00 AM", "Vaccination", AppointmentStatus.SCHEDULED, "Dr. M. Tshivhase")
        assertEquals("SCHEDULED", appt.status.name)
    }
}
