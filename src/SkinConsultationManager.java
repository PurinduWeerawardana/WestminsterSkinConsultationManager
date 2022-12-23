import java.util.ArrayList;

public interface SkinConsultationManager {
    ArrayList <Doctor> doctors = new ArrayList<>();
    ArrayList <Patient> patients = new ArrayList<>();
    ArrayList <Consultation> consultations = new ArrayList<>();
    void addDoctor();
    void deleteDoctor();
    void printDoctorsList();
    void saveToFile();
}
