# Release Notes - Polokwane Veterinary Clinic Management System

## Version 1.0.0

### Documented Updates
* **Android Application**: Complete Android Mobile Application built with Kotlin and Jetpack Compose (Material 3).
* **Local Storage**: Integrated local Room Database for offline persistence and pre-populated sample records.
* **REST API Layer**: Complete REST API interface definitions, Data Transfer Objects (DTOs), and Remote Data Sources in `data/remote/`.
* **Clinic Management Modules**: Implemented modules for Pet Owners, Patients (Animals), Appointments, Medical Treatments, Billing & Invoices, Operational Reports, and User Settings.
* **Full CRUD Operations**: Added Create, Read, Update, and Delete capabilities across all clinic entities.
* **Security & Authentication**: Local authentication with SHA-256 password hashing and input validation.
* **Database & Testing**: Database schema (`schema.sql`) and 10+ record test seed script (`seed.sql`) included in `database/`. Automated unit testing (`ValidationUnitTest.kt`) with 5 passing tests.

### System Screenshots & Media
* Screenshots of the database, website, and Android application are included in the WIL project report and repository media documentation.

### Current Limitations & Future Scope
* Remote REST API integration ready for connection to local XAMPP/Spring Boot server.
* Email confirmation and Single Sign-On (SSO) integration marked for future backend deployment.
