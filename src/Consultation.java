import java.time.LocalDateTime;

public class Consultation {
    private static int consultationNumber;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime bookedDateTime;
    private double cost;
    private String notes;

    public Consultation(Doctor doctor, Patient patient, LocalDateTime bookedDateTime) {
        this.doctor = doctor;
        this.patient = patient;
        this.bookedDateTime = bookedDateTime;
        ++consultationNumber;
    }

    public static int getConsultationNumber() {
        return consultationNumber;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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