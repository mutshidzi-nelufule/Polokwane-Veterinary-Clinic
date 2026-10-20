# Polokwane Veterinary Clinic Management System

**Module**: Work Integrated Learning (XISD6329) - Semester 2 Task 2  
**Project**: Polokwane Veterinary Clinic  
**Package**: `com.example.polokwanevetclinic`  

---

## Project Overview

This Android application forms part of the integrated management system for Polokwane Veterinary Clinic. The application allows clinic staff and pet owners to manage clinic operations, including pet owners, animals (patients), appointments, medical treatments, billing/invoices, and operational summary reports.

---

## Technologies Used

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose with Material 3
* **Architecture Pattern**: MVVM (Model-View-ViewModel) + Repository Pattern
* **Local Database**: Room Database (for offline storage and local caching)
* **API / Networking**: Retrofit 2 with Gson converter
* **Asynchronous Execution**: Kotlin Coroutines & Flow
* **Build System**: Gradle (Kotlin DSL)
* **CI/CD**: GitHub Actions (`.github/workflows/android.yml`)

---

## Project Package Structure

```
com.example.polokwanevetclinic
├── model/           # Data models and enums (PetOwner, Animal, Appointment, Treatment, Invoice, etc.)
├── data/
│   ├── local/       # Room database entities, DAOs, and database configuration
│   └── remote/      # Retrofit API interface, DTOs, and HTTP client
├── repository/      # Repository classes combining local Room DB and Retrofit API
├── viewmodel/       # ViewModels and ViewModelFactory for managing UI state
├── ui/
│   ├── components/  # Top bar, bottom navigation bar, and drawer components
│   ├── screens/     # Screen layouts and dialog forms for adding/editing records
│   └── theme/       # Color palette, typography, and theme styling
└── navigation/      # Navigation routes and AppNavHost
```

---

## Database Setup (MySQL)

SQL scripts for setting up the central MySQL database are located in the `database/` folder:

1. `database/schema.sql` – Creates the database tables (`users`, `pet_owners`, `animals`, `appointments`, `treatments`, `invoices`) with primary keys, foreign key constraints, and indexes.
2. `database/seed.sql` – Populates the database with test records (at least 10 records per table).

### How to run the SQL scripts in MySQL Workbench:
1. Open MySQL Workbench and connect to your local MySQL server.
2. Open and execute `database/schema.sql`.
3. Open and execute `database/seed.sql`.

---

## How to Run and Test the Android Application

### Option A: Running on Android Studio Emulator
1. Open the project in Android Studio.
2. Let Gradle sync complete.
3. Select an Android Virtual Device (Emulator) with API level 26 or higher.
4. Click **Run** (`Shift + F10`).
5. The default API base URL is configured to `http://10.0.2.2:8080/api/` (Android emulator alias for localhost).

### Option B: Running on a Physical Android Phone
1. Enable **Developer Options** and **USB Debugging** on your Android phone.
2. Connect the phone to your computer using a USB cable.
3. In Android Studio, select your physical phone as the run target and click **Run**.
4. Once the app launches on your phone, open the navigation drawer and go to **Settings**.
5. Update the **REST API Base URL** to your development computer's local IP address (for example: `http://192.168.1.50:8080/api/`).

---

## Running Unit Tests

To run the unit tests in Android Studio:
1. Navigate to `app/src/test/java/com/example/polokwanevetclinic/ValidationUnitTest.kt`.
2. Right-click the file and select **Run 'ValidationUnitTest'**.
3. Alternatively, run the following command in terminal:
   ```bash
   ./gradlew testDebugUnitTest
   ```
