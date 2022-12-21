import java.time.LocalDate;

public class Patient extends Person{
    private static int patientId;

    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber) {
        super(name, surname, dateOfBirth, mobileNumber);
        ++patientId;
    }

    public static int getPatientId() {
        return patientId;
    }
}
