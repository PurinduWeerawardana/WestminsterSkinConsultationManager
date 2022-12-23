import java.time.LocalDate;

public class Patient extends Person{
    private String patientNic;

    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String patientNic) {
        super(name, surname, dateOfBirth, mobileNumber);
    }

    public String getPatientId() {
        return patientNic;
    }
}
