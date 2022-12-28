package models;

import java.time.LocalDate;

public class Patient extends Person{
    private final String patientNic;

    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String patientNic) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.patientNic = patientNic;
    }

    public String getPatientNIC() {
        return patientNic;
    }

    public String toString() {
        return super.toString() + "Patient NIC: " + patientNic + "\n";
    }
}
