-- Polokwane Veterinary Clinic - Simulated Test Data Seed Script
-- Contains at least 10 realistic records per table.

USE polokwane_vet_db;

-- Clear existing sample records
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE invoices;
TRUNCATE TABLE treatments;
TRUNCATE TABLE appointments;
TRUNCATE TABLE animals;
TRUNCATE TABLE pet_owners;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Insert 10 Users
INSERT INTO users (user_id, full_name, email, password_hash, role) VALUES
('u101', 'Dr. Mutshidzi Tshivhase', 'vet@polokwanevet.co.za', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'VET'),
('u102', 'Dr. Kevan Naidoo', 'knaidoo@polokwanevet.co.za', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'VET'),
('u103', 'John Smith', 'john.smith@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u104', 'Sarah Ndlovu', 'sarah.n@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u105', 'David Botha', 'dbotha@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u106', 'Thabo Molefe', 'tmolefe@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u107', 'Emily van der Merwe', 'emilyv@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u108', 'Sello Mabena', 'smabena@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u109', 'Lindiwe Khumalo', 'lkhumalo@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER'),
('u110', 'Johan Coetzee', 'jcoetzee@example.com', '$2a$12$eImiTXuWVxfM37uY4JANjO...hash', 'PET_OWNER');

-- 2. Insert 10 Pet Owners
INSERT INTO pet_owners (owner_id, full_name, email, phone, address, registered_date) VALUES
('o1', 'John Smith', 'john.smith@example.com', '0821234567', '123 Market St, Polokwane', '2024-01-15'),
('o2', 'Sarah Ndlovu', 'sarah.n@example.com', '0719876543', '45 Grobler St, Polokwane', '2024-02-10'),
('o3', 'David Botha', 'dbotha@example.com', '0835551234', '78 Landdros Mare St, Polokwane', '2024-03-01'),
('o4', 'Thabo Molefe', 'tmolefe@example.com', '0842223344', '12 Hospital St, Polokwane', '2024-03-15'),
('o5', 'Emily van der Merwe', 'emilyv@example.com', '0724445566', '89 Schoeman St, Polokwane', '2024-04-02'),
('o6', 'Sello Mabena', 'smabena@example.com', '0813334455', '56 Biccard St, Polokwane', '2024-04-20'),
('o7', 'Lindiwe Khumalo', 'lkhumalo@example.com', '0736667788', '101 Hans van Rensburg St, Polokwane', '2024-05-05'),
('o8', 'Johan Coetzee', 'jcoetzee@example.com', '0828889900', '34 Marshall St, Polokwane', '2024-05-18'),
('o9', 'Grace Pillay', 'gpillay@example.com', '0791112233', '67 Thabo Mbeki St, Polokwane', '2024-06-01'),
('o10', 'Pieter Pretorius', 'ppretorius@example.com', '0834445566', '90 Dorp St, Polokwane', '2024-06-14');

-- 3. Insert 10 Animals
INSERT INTO animals (animal_id, owner_id, name, species, breed, date_of_birth_or_age, sex, medical_notes) VALUES
('a1', 'o1', 'Max', 'Dog', 'German Shepherd', '3 years', 'Male', 'Up to date with rabies shots'),
('a2', 'o2', 'Bella', 'Cat', 'Siamese', '2 years', 'Female', 'Spayed'),
('a3', 'o3', 'Rocky', 'Dog', 'Boerboel', '5 years', 'Male', 'Mild skin allergy'),
('a4', 'o4', 'Milo', 'Dog', 'Jack Russell', '1 year', 'Male', 'Microchipped'),
('a5', 'o5', 'Luna', 'Cat', 'Persian', '4 years', 'Female', 'Requires dental scaling'),
('a6', 'o6', 'Charlie', 'Dog', 'Golden Retriever', '2 years', 'Male', 'Friendly temperament'),
('a7', 'o7', 'Simba', 'Cat', 'Domestic Short Hair', '3 years', 'Male', 'Neutered'),
('a8', 'o8', 'Daisy', 'Dog', 'Poodle', '6 years', 'Female', 'Routine checkup required'),
('a9', 'o9', 'Coco', 'Bird', 'African Grey', '8 years', 'Female', 'Healthy beak and plumage'),
('a10', 'o10', 'Duke', 'Dog', 'Rottweiler', '4 years', 'Male', 'Vaccinated');

-- 4. Insert 10 Appointments
INSERT INTO appointments (appointment_id, animal_id, owner_id, appointment_date, time_slot, reason, status, vet_name) VALUES
('apt1', 'a1', 'o1', '2024-11-10', '09:00 AM', 'Annual Checkup & Rabies Vaccine', 'SCHEDULED', 'Dr. M. Tshivhase'),
('apt2', 'a2', 'o2', '2024-11-11', '10:30 AM', 'Dental Examination', 'SCHEDULED', 'Dr. M. Tshivhase'),
('apt3', 'a3', 'o3', '2024-11-05', '02:00 PM', 'Allergy Treatment Follow-up', 'COMPLETED', 'Dr. K. Naidoo'),
('apt4', 'a4', 'o4', '2024-11-12', '11:15 AM', 'Deworming & Health Assessment', 'SCHEDULED', 'Dr. K. Naidoo'),
('apt5', 'a5', 'o5', '2024-11-13', '03:00 PM', 'Grooming & Nail Trim', 'SCHEDULED', 'Dr. M. Tshivhase'),
('apt6', 'a6', 'o6', '2024-11-14', '08:30 AM', 'Hip Joint Evaluation', 'SCHEDULED', 'Dr. K. Naidoo'),
('apt7', 'a7', 'o7', '2024-11-01', '01:00 PM', 'Feline Leukemia Vaccine', 'COMPLETED', 'Dr. M. Tshivhase'),
('apt8', 'a8', 'o8', '2024-11-15', '04:00 PM', 'Routine Blood Work', 'SCHEDULED', 'Dr. K. Naidoo'),
('apt9', 'a9', 'o9', '2024-11-16', '10:00 AM', 'Avian Health Check', 'SCHEDULED', 'Dr. M. Tshivhase'),
('apt10', 'a10', 'o10', '2024-11-02', '11:00 AM', 'Tick & Flea Prevention', 'COMPLETED', 'Dr. K. Naidoo');

-- 5. Insert 10 Treatments
INSERT INTO treatments (treatment_id, animal_id, vet_name, diagnosis, treatment_description, cost, treatment_date) VALUES
('t1', 'a1', 'Dr. M. Tshivhase', 'Canine Parvovirus Booster', 'Administered DHPP vaccine and deworming tablet.', 450.00, '2024-10-15'),
('t2', 'a3', 'Dr. K. Naidoo', 'Allergies & Dermatitis', 'Prescribed antihistamines and anti-itch ointment.', 680.00, '2024-11-05'),
('t3', 'a2', 'Dr. M. Tshivhase', 'Gingivitis Stage 1', 'Dental prophylaxis cleaning and oral gel application.', 850.00, '2024-09-20'),
('t4', 'a4', 'Dr. K. Naidoo', 'Puppy Deworming', 'Administered Broad Spectrum Dewormer.', 250.00, '2024-10-01'),
('t5', 'a5', 'Dr. M. Tshivhase', 'Hairball Intestinal Stasis', 'Laxative gel prescribed with dietary adjustment.', 320.00, '2024-10-12'),
('t6', 'a6', 'Dr. K. Naidoo', 'Ear Infection (Otitis Externa)', 'Ear flushing and antibacterial drop prescription.', 520.00, '2024-10-18'),
('t7', 'a7', 'Dr. M. Tshivhase', 'Annual Feline Vaccination', 'Tricat vaccine administered.', 400.00, '2024-11-01'),
('t8', 'a8', 'Dr. K. Naidoo', 'Minor Skin Laceration', 'Wound cleaned, stitched with local anesthesia, antibiotics.', 950.00, '2024-10-25'),
('t9', 'a9', 'Dr. M. Tshivhase', 'Beak Trim & Vitamin Supplement', 'Beak trimmed safely and multivitamin drops given.', 300.00, '2024-09-15'),
('t10', 'a10', 'Dr. K. Naidoo', 'Tick Fever Prophylaxis', 'Bravecto chewable tablet administered.', 600.00, '2024-11-02');

-- 6. Insert 10 Invoices
INSERT INTO invoices (invoice_id, owner_id, animal_name, invoice_date, total_amount, status) VALUES
('INV-1001', 'o1', 'Max', '2024-10-15', 450.00, 'PAID'),
('INV-1002', 'o3', 'Rocky', '2024-11-05', 680.00, 'UNPAID'),
('INV-1003', 'o2', 'Bella', '2024-09-20', 850.00, 'PAID'),
('INV-1004', 'o4', 'Milo', '2024-10-01', 250.00, 'PAID'),
('INV-1005', 'o5', 'Luna', '2024-10-12', 320.00, 'UNPAID'),
('INV-1006', 'o6', 'Charlie', '2024-10-18', 520.00, 'PAID'),
('INV-1007', 'o7', 'Simba', '2024-11-01', 400.00, 'PAID'),
('INV-1008', 'o8', 'Daisy', '2024-10-25', 950.00, 'OVERDUE'),
('INV-1009', 'o9', 'Coco', '2024-09-15', 300.00, 'PAID'),
('INV-1010', 'o10', 'Duke', '2024-11-02', 600.00, 'UNPAID');
