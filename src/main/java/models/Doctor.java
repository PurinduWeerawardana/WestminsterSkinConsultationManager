package models;

import java.time.LocalDate;

public class Doctor extends Person implements Comparable<Doctor>{
    private final String medicalLicenseNumber;
    private String specialization;
    private WorkSchedule workSchedule;

    public Doctor(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String medicalLicenseNumber, String specialization, WorkSchedule workSchedule) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
        this.workSchedule = workSchedule;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public WorkSchedule getWorkSchedule() {
        return workSchedule;
    }

    public String toString() {
        return "First Name: " + super.getName() + "," +
                "\tSurname: " + super.getSurname() + "," +
                "\tDate of Birth: " + super.getDateOfBirth() + "," +
                "\tMobile Number: " + super.getMobileNumber() + "," +
                "\tMedical License Number: " + medicalLicenseNumber + "," +
                "\tSpecialization: " + specialization;
    }

    public int compareToLicense(Doctor doctor) {
        return this.medicalLicenseNumber.compareTo(doctor.medicalLicenseNumber);
    }

    @Override
    public int compareTo(Doctor doctor) {
        return this.getSurname().compareTo(doctor.getSurname());
    }

}
