package implementation;

import GUI.WestminsterSkinConsultationsGUI;
import interfaces.SkinConsultationManager;
import models.Consultation;
import models.Doctor;
import models.Patient;
import models.WorkSchedule;
import utils.DataEncryptionDecryption;
import utils.InputValidator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    int maxDoctors;
    double firstConsultationCostPerHour;
    double followUpConsultationCostPerHour;

    public void addDoctor() {
        if (doctors.size() < maxDoctors){
            Scanner scanner = new Scanner(System.in);
            String name = InputValidator.getValidStringByPrompt("Enter the doctor's first name: ");
            String surname = InputValidator.getValidStringByPrompt("Enter the doctor's surname: ");
            LocalDate dateOfBirth = InputValidator.getValidDateByPrompt("Enter the doctor's date of birth(yyyy-mm-dd): ");
            String mobileNumber = InputValidator.getValidStringByPrompt("Enter the doctor's mobile number: ");
            String medicalLicenseNumber = InputValidator.getValidStringByPrompt("Enter the doctor's medical license number: ");
            String specialization = InputValidator.getValidStringByPrompt("Enter the doctor's specialization: ");
            Doctor newDoctor = new Doctor(name, surname, dateOfBirth, mobileNumber, medicalLicenseNumber, specialization, new WorkSchedule());
            System.out.println("Do you want to add a new doctor with following information:\n" +
                    newDoctor +
                    "\n\tEnter 'y' to confirm or 'n' to cancel: ");
            String confirm = scanner.nextLine();
            boolean duplicateFound = false;
            if (confirm.equalsIgnoreCase("y")) {
                for (Doctor doctor : doctors) {
                    if(doctor.getMedicalLicenseNumber().equals(medicalLicenseNumber)){
                        duplicateFound = true;
                        break;
                    }
                }
                if (duplicateFound) {
                    System.out.println("A doctor with the same medical license number already exists!");
                } else {
                    doctors.add(newDoctor);
                    System.out.println("Doctor added successfully.");
                }
            } else {
                System.out.println("Doctor not added!");
            }
        } else {
            System.out.println("Maximum number of doctors reached! Please delete a doctor to add a new one.");
        }
    }

    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        String medicalLicenseNumber = InputValidator.getValidStringByPrompt("Enter the medical license number of the doctor you want to delete: ");
        boolean doctorFound = false;
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalLicenseNumber().equals(medicalLicenseNumber)) {
                doctorFound = true;
                System.out.println("Do you want to delete " + doctor.getName() + " " + doctor.getSurname() + " ("+ doctor.getSpecialization() +") ?\t(y/n)");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    doctors.remove(doctor);
                    System.out.println("Doctor deleted successfully.");
                    break;
                } else {
                    System.out.println("Delete operation not performed!");
                }
                System.out.println("Total number of doctors available: " + doctors.size());
            }
        }
        if (!doctorFound){
            System.out.println("Doctor (license: " + medicalLicenseNumber + ") not found!");
        }
    }

    public void printDoctorsList() {
        Collections.sort(doctors);
        if (doctors.size() > 0) {
            System.out.println("List of doctors:\n");
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println("\t" + (i+1) + ".\t" + doctors.get(i));
            }
            System.out.println("\nTotal number of doctors available: " + doctors.size());
        } else {
            System.out.println("No doctors available!");
        }
    }
    public void saveToFile(){
        try (FileWriter fileWriter = new FileWriter(".\\database\\doctors.txt")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Doctor doctor : doctors) {
                bufferedWriter.write(doctor.getName() + "," + doctor.getSurname() + "," + doctor.getDateOfBirth() + "," + doctor.getMobileNumber() + "," + doctor.getMedicalLicenseNumber() + "," + doctor.getSpecialization());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Doctors Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
        try (FileWriter fileWriter = new FileWriter(".\\database\\patients.txt")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Patient patient : patients) {
                bufferedWriter.write(patient.getName() + "," + patient.getSurname() + "," + patient.getDateOfBirth() + "," + patient.getMobileNumber() + "," + patient.getPatientNIC());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Patients Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
        try (FileWriter fileWriter = new FileWriter(".\\database\\consultations.txt")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Consultation consultation : consultations) {
                bufferedWriter.write(consultation.getDoctor() + "," + consultation.getPatient() + "," + consultation.getBookedDateTime() + "," + consultation.getDuration() + "," + consultation.getCost() + "," + consultation.getNotesFilePath() + "," + consultation.getNotesEncryptionKey() + "," + consultation.getImageFilePath() + "," + consultation.getImageEncryptionKey());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Consultations Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }

    public void loadFromFile() {
        try (FileReader fileReader = new FileReader(".\\database\\patients.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] patientData = line.split(",");
                Patient patient = new Patient(patientData[0], patientData[1], LocalDate.parse(patientData[2]), patientData[3], patientData[4]);
                patients.add(patient);
            }
            bufferedReader.close();
            fileReader.close();
            System.out.println("Patients data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        try (FileReader fileReader = new FileReader(".\\database\\consultations.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] consultationData = line.split(",");
                Consultation consultation = new Consultation(consultationData[0], consultationData[1], LocalDateTime.parse(consultationData[2]), Integer.parseInt(consultationData[3]), Double.parseDouble(consultationData[4]), consultationData[5], consultationData[6], consultationData[7], consultationData[8]);
                consultations.add(consultation);
            }
            bufferedReader.close();
            fileReader.close();
            System.out.println("Consultations data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
        try (FileReader fileReader = new FileReader(".\\database\\doctors.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] doctorData = line.split(",");
                ArrayList<Consultation> doctorConsultations = new ArrayList<>();
                for (Consultation consultation : consultations) {
                    if (consultation.getDoctor().equals(doctorData[4])) {
                        doctorConsultations.add(consultation);
                    }
                }
                Doctor doctor = new Doctor(doctorData[0], doctorData[1], LocalDate.parse(doctorData[2]), doctorData[3], doctorData[4], doctorData[5], new WorkSchedule());
                if (doctorConsultations.size() > 0) {
                    for (Consultation consultation : doctorConsultations) {
                        doctor.getWorkSchedule().setBookedHours(consultation.getBookedDateTime(), consultation.getDuration());
                    }
                }
                doctors.add(doctor);
            }
            bufferedReader.close();
            fileReader.close();
            System.out.println("Doctors data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }

    public void showGUI(){
        new WestminsterSkinConsultationsGUI(doctors,patients,consultations,firstConsultationCostPerHour,followUpConsultationCostPerHour);
    }

    public static Doctor getDoctorByLicenseNumber(String medicalLicenseNumber){
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalLicenseNumber().equals(medicalLicenseNumber)) {
                return doctor;
            }
        }
        return null;
    }

    public WestminsterSkinConsultationManager(int maxDoctors, double firstConsultationCostPerHour, double followUpConsultationCostPerHour) {
        this.maxDoctors = maxDoctors;
        this.firstConsultationCostPerHour = firstConsultationCostPerHour;
        this.followUpConsultationCostPerHour = followUpConsultationCostPerHour;
        loadFromFile();
    }

    public static boolean checkAvailability(String selectedDoctorLicenseNumber, LocalDateTime selectedDateTime, int durationInHours) {
        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalLicenseNumber().equals(selectedDoctorLicenseNumber)) {
                selectedDoctor = doctor;
                break;
            }
        }
        WorkSchedule workSchedule = selectedDoctor.getWorkSchedule();
        if (workSchedule.isBooked(selectedDateTime, durationInHours)) {
            return false;
        }
        return true;
    }

    public static Doctor assignRandomDoctor(Doctor selectedDoctor, LocalDateTime selectedDateTime, int durationInHours) {
        Doctor newDoctor = null;
        Collections.shuffle(doctors);
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalLicenseNumber().equals(selectedDoctor.getMedicalLicenseNumber())) {
                continue;
            } else {
                WorkSchedule workSchedule = doctor.getWorkSchedule();
                if (!workSchedule.isBooked(selectedDateTime, durationInHours)) {
                    newDoctor = doctor;
                    break;
                }
            }
        }
        return newDoctor;
    }

    public static boolean addPatient(String firstName, String surname, LocalDate patientDateOfBirth, String nic, String contactNumber) {
        for (Patient patient : patients) {
            if (patient.getPatientNIC().equals(nic)) {
                return false;
            }
        }
        patients.add(new Patient(firstName, surname, patientDateOfBirth, nic, contactNumber));
        return true;
    }

    public static boolean addConsultation(String doctorsMedicalLicenseNumber, String patientNic, LocalDateTime dateAndTime, int duration, double cost, String notes, String imageFilePath) {
        Doctor doctor = getDoctorByLicenseNumber(doctorsMedicalLicenseNumber);
        boolean isAddedToDoctorsSchedule = doctor.getWorkSchedule().setBookedHours(dateAndTime,duration);
        if (isAddedToDoctorsSchedule){
            try{
                String encryptedImageFilePath = ".\\encrypted\\" + "img_" + patientNic + "_" + dateAndTime.toString().replace(":","") + "_" + doctorsMedicalLicenseNumber;
                String encryptedNotesFilePath = ".\\encrypted\\" + "notes_" + patientNic + "_" + dateAndTime.toString().replace(":","") + "_" + doctorsMedicalLicenseNumber;
                String imageEncryptionKey = null;
                String notesEncryptionKey = null;
                if (imageFilePath != null){
                    imageEncryptionKey = DataEncryptionDecryption.encryptDataFromFile(imageFilePath,encryptedImageFilePath);

                } else {
                    encryptedImageFilePath = "";
                }
                if (!notes.isBlank()){
                    notesEncryptionKey = DataEncryptionDecryption.encryptData(notes,encryptedNotesFilePath);
                } else {
                    encryptedNotesFilePath = "";
                }
                consultations.add(new Consultation(doctorsMedicalLicenseNumber, patientNic, dateAndTime, duration, cost, encryptedNotesFilePath, notesEncryptionKey, encryptedImageFilePath, imageEncryptionKey));
                return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

}