package GUI;

import models.Consultation;
import models.Doctor;
import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class WestminsterSkinConsultationsGUI {
    ArrayList<Doctor> doctors;
    ArrayList<Patient> patients;
    ArrayList<Consultation> consultations;
    double firstConsultationCostPerHour;
    double followUpConsultationCostPerHour;
    JFrame homeFrame;

    public WestminsterSkinConsultationsGUI(ArrayList<Doctor> doctors, ArrayList<Patient> patients, ArrayList<Consultation> consultations, double firstConsultationCostPerHour, double followUpConsultationCostPerHour) {
        this.doctors = doctors;
        this.patients = patients;
        this.consultations = consultations;
        this.firstConsultationCostPerHour = firstConsultationCostPerHour;
        this.followUpConsultationCostPerHour = followUpConsultationCostPerHour;
        homeFrame = new JFrame("Westminster Skin Consultations");
        homeFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel homePanel = new JPanel(new GridLayout(2,1));
        JPanel welcomePanel = new JPanel(new GridLayout(2,1));
        JLabel welcomeLabel1 = new JLabel("Welcome to", JLabel.CENTER);
        welcomeLabel1.setFont(new Font("SansSerif", Font.PLAIN, 30));
        JLabel welcomeLabel2 = new JLabel("Westminster Skin Consultations", JLabel.CENTER);
        welcomeLabel2.setFont(new Font("SansSerif", Font.PLAIN, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        JButton viewDoctorsButton = new JButton("View Doctors");
        viewDoctorsButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        viewDoctorsButton.addActionListener(e -> {
            new DoctorViewGUI(doctors);
        });
        JButton bookConsultationButton = new JButton("Book Consultation");
        bookConsultationButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        bookConsultationButton.addActionListener(e -> {
            new BookConsultationGUI(doctors, patients, firstConsultationCostPerHour, followUpConsultationCostPerHour);
        });
        JButton viewConsultationsButton = new JButton("View Consultations");
        viewConsultationsButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        viewConsultationsButton.addActionListener(e -> {
            new ConsultationsViewGUI(consultations, patients, doctors);
        });
        buttonPanel.add(viewDoctorsButton);
        buttonPanel.add(viewConsultationsButton);
        buttonPanel.add(bookConsultationButton);
        welcomePanel.add(welcomeLabel1);
        welcomePanel.add(welcomeLabel2);
        homePanel.add(welcomePanel);
        homePanel.add(buttonPanel);
        homeFrame.add(homePanel, gbc);
        homeFrame.setSize(1200, 700);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        homeFrame.setVisible(true);
    }

}
