package GUI;

import models.Consultation;
import models.Doctor;
import models.Patient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class WestminsterSkinConsultationsGUI extends JFrame {
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private ArrayList<Consultation> consultations;
    private double firstConsultationCostPerHour;
    private double followUpConsultationCostPerHour;

    public WestminsterSkinConsultationsGUI(ArrayList<Doctor> doctors, ArrayList<Patient> patients, ArrayList<Consultation> consultations, double firstConsultationCostPerHour, double followUpConsultationCostPerHour) {
        this.doctors = doctors;
        this.patients = patients;
        this.consultations = consultations;
        this.firstConsultationCostPerHour = firstConsultationCostPerHour;
        this.followUpConsultationCostPerHour = followUpConsultationCostPerHour;
        this.setTitle("Westminster Skin Consultations");
        // set background image
        try {
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(".\\database\\bg.jpg")))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setLayout(new GridBagLayout());
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
            showDoctorViewGUI();
        });
        JButton bookConsultationButton = new JButton("Book Consultation");
        bookConsultationButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        bookConsultationButton.addActionListener(e -> {
            showBookConsultationGUI();
        });
        JButton viewConsultationsButton = new JButton("View Consultations");
        viewConsultationsButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        viewConsultationsButton.addActionListener(e -> {
            showConsultationsViewGUI();
        });
        homePanel.setOpaque(false);
        welcomePanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        buttonPanel.add(viewDoctorsButton);
        buttonPanel.add(viewConsultationsButton);
        buttonPanel.add(bookConsultationButton);
        welcomePanel.add(welcomeLabel1);
        welcomePanel.add(welcomeLabel2);
        homePanel.add(welcomePanel);
        homePanel.add(buttonPanel);
        this.add(homePanel, gbc);
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void showDoctorViewGUI() {
        new DoctorViewGUI(doctors);
    }

    private void showBookConsultationGUI() {
        new BookConsultationGUI(doctors, patients, firstConsultationCostPerHour, followUpConsultationCostPerHour);
    }

    private void showConsultationsViewGUI() {
        new ConsultationsViewGUI(consultations, patients, doctors);
    }
}
