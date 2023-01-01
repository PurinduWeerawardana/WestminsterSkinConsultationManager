package implementation;

import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {

    WestminsterSkinConsultationManager wscm = new WestminsterSkinConsultationManager(10,15,25);

    @org.junit.jupiter.api.Test
    void addDoctorTest() {
        int numberOfDoctors = wscm.doctors.size();
        if (numberOfDoctors < wscm.maxDoctors) {
            wscm.addDoctor();
            assertEquals(numberOfDoctors + 1, wscm.doctors.size());
        } else {
            wscm.addDoctor();
            assertEquals(numberOfDoctors, wscm.doctors.size());
        }
    }

    @org.junit.jupiter.api.Test
    void deleteDoctor() {
        int numberOfDoctors = wscm.doctors.size();
        wscm.deleteDoctor();
        assertEquals(numberOfDoctors - 1, wscm.doctors.size());
    }

    @org.junit.jupiter.api.Test
    void addConsultation() {
        int numberOfConsultations = wscm.consultations.size();
    }

    @org.junit.jupiter.api.Test
    void saveToFile() {
    }

    @org.junit.jupiter.api.Test
    void loadFromFile() {
    }
}