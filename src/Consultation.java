import java.time.LocalDateTime;

public class Consultation {
    private static int consultationNumber;
    private String doctorsMedicalLicenseNumber;
    private String patientNic;
    private LocalDateTime bookedDateTime;
    private double cost;
    private String notes;

    public Consultation(String doctorsMedicalLicenseNumber, String patientNic, LocalDateTime bookedDateTime , double cost, String notes) {
        this.doctorsMedicalLicenseNumber = doctorsMedicalLicenseNumber;
        this.patientNic = patientNic;
        this.bookedDateTime = bookedDateTime;
        this.cost = cost;
        this.notes = notes;
        ++consultationNumber;
    }

    public static int getConsultationNumber() {
        return consultationNumber;
    }

    public String getDoctor() {
        return doctorsMedicalLicenseNumber;
    }

    public void setDoctor(String doctorsMedicalLicenseNumber) {
        this.doctorsMedicalLicenseNumber = doctorsMedicalLicenseNumber;
    }

    public String getPatient() {
        return patientNic;
    }

    public void setPatient(Patient patient) {
        this.patientNic = patientNic;
    }

    public LocalDateTime getBookedDateTime() {
        return bookedDateTime;
    }

    public void setBookedDateTime(LocalDateTime bookedDateTime) {
        this.bookedDateTime = bookedDateTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}