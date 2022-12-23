import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    int maxDoctors;
    public WestminsterSkinConsultationManager(int maxDoctors) {
        this.maxDoctors = maxDoctors;
        loadFromFile();
    }

    public static boolean checkAvailability(String selectedDoctorLicenseNumber, String selectedDateTime, String selectedAppointmentDurationInMinutesWithZeroAndColon) {
        // TODO: Implement this method
        return false;
    }

    public void addDoctor() {
        if (doctors.size() < maxDoctors){
            Scanner scanner = new Scanner(System.in);
            String name = InputValidator.getValidStringByPrompt("Enter the doctor's first name: ");
            String surname = InputValidator.getValidStringByPrompt("Enter the doctor's surname: ");
            LocalDate dateOfBirth = InputValidator.getValidDateByPrompt("Enter the doctor's date of birth(yyyy-mm-dd): ");
            String mobileNumber = InputValidator.getValidStringByPrompt("Enter the doctor's mobile number: ");
            String medicalLicenseNumber = InputValidator.getValidStringByPrompt("Enter the doctor's medical license number: ");
            String specialization = InputValidator.getValidStringByPrompt("Enter the doctor's specialization: ");
            Doctor newDoctor = new Doctor(name, surname, dateOfBirth, mobileNumber, medicalLicenseNumber, specialization);
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
        try (FileWriter fileWriter = new FileWriter("doctors.txt")) {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Doctor doctor : doctors) {
                bufferedWriter.write(doctor.getName() + "," + doctor.getSurname() + "," + doctor.getDateOfBirth() + "," + doctor.getMobileNumber() + "," + doctor.getMedicalLicenseNumber() + "," + doctor.getSpecialization());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            fileWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }

    public void loadFromFile() {
        try (FileReader fileReader = new FileReader("doctors.txt")) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] doctorData = line.split(",");
                Doctor doctor = new Doctor(doctorData[0], doctorData[1], LocalDate.parse(doctorData[2]), doctorData[3], doctorData[4], doctorData[5]);
                doctors.add(doctor);
            }
            bufferedReader.close();
            fileReader.close();
            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }

    public void showGUI(){
        new WestminsterSkinConsultationsGUI();
    }

    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public static void addPatient(Patient patient) {
        patients.add(patient);
    }

    public static void addConsultation(String doctorsMedicalLicenseNumber, String patientNic, LocalDateTime dateAndTime, double cost, String notes){
        Consultation consultation = new Consultation(doctorsMedicalLicenseNumber, patientNic, dateAndTime, cost, notes);
        consultations.add(consultation);
        System.out.println("Consultation added successfully.");
        System.out.println(consultation);
    }
}