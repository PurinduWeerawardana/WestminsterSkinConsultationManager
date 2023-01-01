package models;

import java.time.LocalDateTime;

public class Consultation {
    private static int consultationNumber;
    private String consultationID;
    private String doctorsMedicalLicenseNumber;
    private String patientNIC;
    private LocalDateTime bookedDateTime;
    private int duration;
    private double cost;
    private String notesFilePath;
    private String notesEncryptionKey;
    private String imageFilePath;
    private String imageEncryptionKey;

    public Consultation(String doctorsMedicalLicenseNumber, String patientNIC, LocalDateTime bookedDateTime, int duration, double cost, String notesFilePath, String notesEncryptionKey, String imagePath, String imageEncryptionKey) {
        this.doctorsMedicalLicenseNumber = doctorsMedicalLicenseNumber;
        this.patientNIC = patientNIC;
        this.bookedDateTime = bookedDateTime;
        this.duration = duration;
        this.cost = cost;
        this.notesFilePath = notesFilePath;
        this.notesEncryptionKey = notesEncryptionKey;
        this.imageFilePath = imagePath;
        this.imageEncryptionKey = imageEncryptionKey;
        ++consultationNumber;
        this.consultationID = "CONS" + consultationNumber;
    }

    public String getConsultationId() {
        return consultationID;
    }

    public String getDoctorLicenseNumber() {
        return doctorsMedicalLicenseNumber;
    }

    public void setDoctor(String doctorsMedicalLicenseNumber) {
        this.doctorsMedicalLicenseNumber = doctorsMedicalLicenseNumber;
    }

    public String getPatientNIC() {
        return patientNIC;
    }

    public LocalDateTime getBookedDateTime() {
        return bookedDateTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotesFilePath() {
        return notesFilePath;
    }

    public void setNotesFilePath(String notesFilePath) {
        this.notesFilePath = notesFilePath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getImageEncryptionKey() {
        return imageEncryptionKey;
    }

    public String getNotesEncryptionKey() {
        return notesEncryptionKey;
    }
}