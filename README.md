# Polokwane Veterinary Clinic Management System

## 1. Project Overview

The **Polokwane Veterinary Clinic Management System** is a software project developed to help improve the management of veterinary clinic activities. The system is intended to make it easier to manage pet owners, animals, appointments, treatments and invoices.

The project focuses on developing an Android mobile application supported by a Spring Boot backend and a MySQL database. A website was also included in the original project plan, but its development is still outstanding.

The system is being developed as part of the Work Integrated Learning (WIL) project for Semester 2, 2026.

## 2. Project Objectives

The main objectives of the project are to:

- Improve the way pet owner information is stored and managed.
- Maintain animal records and their medical information.
- Assist with appointment management.
- Store treatment and diagnosis information.
- Keep track of invoices and payments.
- Provide a mobile application for accessing clinic information.
- Use a central MySQL database for storing information through the backend API.
- Support local data storage using Room in the Android application.

## 3. Main System Components

The project consists of three main components.

### 3.1 Android Mobile Application

The Android application is being developed using Kotlin and Jetpack Compose in Android Studio.

The application is designed to provide a mobile interface for managing veterinary clinic information.

Technologies used include:

- Kotlin
- Android Studio
- Jetpack Compose
- Android Jetpack libraries
- Room Database
- Retrofit
- Gson

The project uses Room for local database storage and includes Retrofit API classes for communication with the Spring Boot backend.

### 3.2 Spring Boot Backend

The backend was developed using Java and Spring Boot. It provides REST API endpoints that allow the application and other supported clients to access information stored in MySQL.

The backend uses:

- Java 21
- Spring Boot
- Spring WebMVC
- Spring Data JPA
- Hibernate
- Maven
- MySQL Connector/J
- Jakarta Validation

The backend runs locally on port `8080` during development.

### 3.3 MySQL Database

The system uses MySQL as its main relational database.

The database name is:

`polokwane_vet_db`

The database contains six main tables:

| Table | Purpose |
|---|---|
| users | Stores system user information and roles |
| pet_owners | Stores pet owner details |
| animals | Stores animal and pet information |
| appointments | Stores appointment details |
| treatments | Stores treatment and diagnosis information |
| invoices | Stores invoice and payment information |

The tables use primary keys and foreign keys to maintain relationships between related records.

## 4. Main System Features

The backend currently provides API functionality for the following areas:

### Pet Owner Management
- View all pet owners.
- View an individual pet owner.
- Register a new pet owner.
- Update pet owner information.
- Delete a pet owner.

### Animal Management
- Retrieve animal records.
- Search for animals belonging to a particular owner.
- Retrieve animals by species.
- Add new animal records.
- Update animal information.
- Delete animal records.

### Appointment Management
- View appointments.
- Retrieve appointments by owner or animal.
- Retrieve appointments by date.
- Create appointments.
- Delete appointments.

### Treatment Management
- View treatment records.
- Retrieve treatments belonging to a particular animal.
- Add treatment information.
- Delete treatment records.

### Invoice Management
- View invoices.
- Retrieve invoices belonging to a particular owner.
- Create invoices.
- Delete invoices.

### User Management
- Retrieve registered users.
- Retrieve a user by ID or email.
- Delete a user.
- Store user roles such as ADMIN, VET, RECEPTIONIST and PET_OWNER.

**Note:** Full authentication and authorisation functionality is still to be implemented.

## 5. Technologies and Development Tools

| Technology | Purpose |
|---|---|
| Kotlin | Android application development |
| Java | Backend development |
| Jetpack Compose | Android user interface |
| Android Studio | Android development environment |
| IntelliJ IDEA | Backend development |
| Spring Boot | REST API development |
| MySQL | Main relational database |
| MySQL Workbench | Database management |
| Room | Local Android database |
| Retrofit | API communication |
| Gson | JSON conversion |
| Maven | Backend dependency management |
| Postman | API testing |
| Git | Version control |
| GitHub | Source code hosting |
| GitHub Actions | Continuous integration workflow |

## 6. System Architecture

The intended architecture connects the Android application to the backend through REST APIs.

The backend communicates with the MySQL database using Spring Data JPA and Hibernate.

The general structure is:

```text
+------------------------------+
|      Android Application     |
|                              |
| Kotlin / Jetpack Compose     |
| Room Database                |
| Retrofit API Client          |
+--------------+---------------+
               |
               | REST API / JSON
               |
+--------------v---------------+
|       Spring Boot Backend    |
|                              |
| Controllers                  |
| Services                     |
| Repositories                 |
| JPA / Hibernate              |
+--------------+---------------+
               |
               | JDBC
               |
+--------------v---------------+
|          MySQL               |
|                              |
| polokwane_vet_db             |
|                              |
| users                        |
| pet_owners                   |
| animals                      |
| appointments                 |
| treatments                   |
| invoices                     |
+------------------------------+
```

Room provides local storage on the Android application. The connection and synchronisation of local and remote records must be tested as part of the remaining integration work.

## 7. Database Relationships

The database uses the following relationships:

- One pet owner can have multiple animals.
- One animal can have multiple appointments.
- One pet owner can have multiple appointments.
- One animal can have multiple treatment records.
- One pet owner can have multiple invoices.

The users table stores system user information separately.

Foreign key constraints help maintain data consistency and prevent records from being deleted when related records still depend on them.

## 8. Getting Started

### 8.1 Requirements

Before running the project, make sure the following software is installed:

- Android Studio
- JDK 21
- IntelliJ IDEA or another compatible Java IDE
- MySQL Server
- MySQL Workbench
- Git
- Postman (recommended for API testing)

### 8.2 Clone the Repository

Open a terminal and run:

```bash
git clone https://github.com/mutshidzi-nelufule/Polokwane-Veterinary-Clinic.git
```

Navigate into the project folder:

```bash
cd Polokwane-Veterinary-Clinic
```

Open the Android project in Android Studio.

### 8.3 Configure the MySQL Database

1. Start MySQL Server.
2. Open MySQL Workbench.
3. Create a database named:

```sql
CREATE DATABASE polokwane_vet_db;
```

4. Create the required tables using the project's database scripts or SQL definitions.
5. Insert sample records if required for testing.

The Spring Boot application uses the following database configuration in `application.properties`:

```properties
spring.application.name=PolokwaneVetClinicBackend

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/polokwane_vet_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

Replace `YOUR_MYSQL_PASSWORD` with your own local MySQL password. Do not commit real passwords or database credentials to GitHub.

The `validate` setting checks that the existing database structure matches the entity mappings. It does not automatically create the tables.

### 8.4 Run the Spring Boot Backend

1. Open the backend project in IntelliJ IDEA.
2. Make sure JDK 21 is selected.
3. Check the database configuration.
4. Allow Maven to download the required dependencies.
5. Run `PolokwaneVetClinicBackendApplication.java`.

The backend should start on:

`http://localhost:8080`

If the application starts successfully, the console should show that the embedded Tomcat server has started.

### 8.5 Test the API

The following endpoints can be used for initial testing:

| Endpoint | Purpose |
|---|---|
| GET /api/pet-owners | Retrieve pet owners |
| GET /api/animals | Retrieve animals |
| GET /api/appointments | Retrieve appointments |
| GET /api/treatments | Retrieve treatments |
| GET /api/invoices | Retrieve invoices |
| GET /api/users | Retrieve users |

For example, open the following address in a browser while the backend is running:

`http://localhost:8080/api/pet-owners`

Postman can also be used to test GET, POST, PUT and DELETE requests where supported by the controller.

### 8.6 Android Backend Connection

The Android application uses Retrofit to prepare communication with the backend.

For an Android emulator, the backend base URL should use:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```

The address `10.0.2.2` allows the Android emulator to access the development computer's localhost.

For a physical Android device, use the computer's local network IP address instead. Both devices must be connected to the same network, and the firewall must allow the connection.

The Android API integration is still being completed and must be tested against the running backend before it can be considered fully operational.

## 9. Screenshots

Screenshots should be added to this section to demonstrate the development and functionality of the project.

### Android Application

Add screenshots of:

- Application home screen.
- Login screen.
- Navigation menu.
- Pet owner management.
- Animal management.
- Appointment management.
- Treatment records.
- Invoice screens.

Example:

```markdown
![Android Home Screen](screenshots/android-home.png)
```

### Backend and Database

Add screenshots showing:

- Spring Boot application running.
- MySQL database and tables.
- API responses in Postman.
- Successful POST, PUT and DELETE requests.

Example:

```markdown
![Backend API Test](screenshots/backend-api-test.png)
```

### Website

The website was included in the original project plan but has not yet been developed. Website screenshots will be added when implementation is completed.

## 10. GitHub and Version Control

GitHub is used to store the source code and keep track of project changes.

The repository is:

https://github.com/mutshidzi-nelufule/Polokwane-Veterinary-Clinic

Git is used to stage, commit and push changes.

Example commands:

```bash
git status
git add .
git commit -m "Describe your changes"
git push origin master
```

The current local development branch used for the recent push is `master`.

### GitHub Actions

GitHub Actions is intended to support continuous integration by automatically running checks when changes are pushed to the repository.

A workflow can be configured to:

- Build the Android application.
- Check whether the project compiles.
- Run available automated tests.
- Build the Spring Boot backend.
- Report build errors.

The actual workflow configuration and successful execution should be documented once implemented and verified in the repository's Actions tab.

## 11. Testing

Testing has been carried out at different stages of development.

### Database Testing
- Verified that the database and tables were created.
- Inserted sample records.
- Checked database relationships.
- Tested updating records.
- Tested deleting records.
- Checked foreign key restrictions.

### Backend Testing
- Started the Spring Boot application successfully.
- Tested retrieving pet owner information from MySQL.
- Tested creating a pet owner through the API.
- Tested updating a pet owner through the API.
- Tested deleting a pet owner through the API.

### Android Testing
The Android application is being developed and its API integration requires further testing.

Further testing should confirm that:
- The application can communicate with the backend.
- Records can be retrieved and displayed correctly.
- New records can be saved.
- Updates and deletions work correctly.
- Local data remains available when offline.
- Synchronisation works correctly when the device reconnects.

## 12. Offline Storage and Synchronisation

The Android project includes Room database components for local data storage.

This allows the application to maintain a local database on the device. The intended functionality is to support access to locally stored information when an internet connection is unavailable.

The complete offline synchronisation process still needs to be verified to ensure that local changes are correctly transferred to the central MySQL database when connectivity becomes available.

## 13. Security Considerations

Security is an important part of the system because it handles personal information and animal medical records.

The following improvements are required before production deployment:

- Implement secure user authentication.
- Apply role-based access control.
- Protect API endpoints against unauthorised access.
- Store passwords using secure password hashing.
- Avoid exposing database credentials.
- Use HTTPS instead of HTTP for production communication.
- Restrict development CORS settings to trusted origins.
- Validate information received from users.

The current backend is configured for local development and testing. It should not be treated as a production-secure deployment.

## 14. Video Demonstration

A video presentation will be used to demonstrate the system and explain its development.

The demonstration should include:

- An introduction to the project.
- The purpose of the system.
- The Android application's main screens.
- Database structure.
- Backend API functionality.
- Examples of tested operations.
- Current development progress.
- Challenges and future improvements.

**Video presentation link:** https://youtu.be/Jh0mgA4jE9o?si=DPdFh2PVeMAx6JH4 / https://youtube.com/shorts/W8Rx-pt5jZM?si=OD3inXAEU56IKRSD

## 15. Release Notes

### Version 1.0 – Initial Development

The following development work has been completed or started:

- Created the Android Studio project.
- Developed the Android application using Kotlin and Jetpack Compose.
- Added local Room database components.
- Created the MySQL database and its main tables.
- Developed the Spring Boot backend.
- Added REST API controllers for the main database entities.
- Tested backend database connectivity.
- Tested pet owner API create, read, update and delete operations.
- Added Retrofit API integration classes to the Android project.
- Updated the GitHub repository with the latest changes.

### Remaining Development Work

The following work still needs to be completed or verified:

- Complete Android-to-backend integration.
- Verify offline data synchronisation.
- Complete authentication and role-based access control.
- Complete website development.
- Configure and test GitHub Actions.
- Complete application testing.
- Prepare the final signed APK and publication evidence where applicable.

## 16. Team Members

The project was developed by the following group members:

| Student Name | Student Number |
|---|---|
| Nelufule Mudzumbululi | ST10440481 |
| Nelufule Hakundwi | ST10440479 |
| Mugwedi Vhutshilo | ST10438484 |
| Mabasa Nhluvuko | ST10370431 |
| Mohale Karabo | ST10456242 |

## 17. Future Improvements

Possible improvements include:

- Completing the website.
- Implementing secure authentication.
- Improving the dashboard and reporting features.
- Adding appointment reminders.
- Improving offline synchronisation.
- Adding more detailed animal medical histories.
- Improving invoice and payment management.
- Deploying the backend to a secure hosting environment.
- Publishing the Android application.

## 18. Conclusion

The Polokwane Veterinary Clinic Management System is being developed to provide a more organised way of managing veterinary clinic information. The project has progressed through database development, Android application development and backend API implementation.

The MySQL database and Spring Boot backend have been created and tested, while the Android application includes local database components and API integration classes. Further development is required to complete the connection between the mobile application and backend, verify synchronisation, implement the website and finalise the remaining project requirements.

The project provides a foundation that can be improved and expanded to support the daily activities of a veterinary clinic.

---

**Project Repository:** https://github.com/mutshidzi-nelufule/Polokwane-Veterinary-Clinic

**Project:** Polokwane Veterinary Clinic Management System

**Module:** XISD6329 – Work Integrated Learning

**Semester:** Semester 2, 2026
