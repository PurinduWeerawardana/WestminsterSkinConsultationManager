import java.util.ArrayList;

public interface SkinConsultationManager {
    ArrayList <Doctor> doctors = new ArrayList<>();
    void addDoctor();
    void deleteDoctor();
    void printDoctorsList();
    void saveToFile();
}
