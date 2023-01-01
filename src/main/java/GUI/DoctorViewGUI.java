package GUI;

import models.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class DoctorViewGUI extends JFrame {
    public DoctorViewGUI(ArrayList <Doctor> doctors) {
        this.setTitle("View Doctors");
        // create main panel of doctors frame
        JPanel mainDoctorsPanel = new JPanel(new BorderLayout());
        // add a title panel to the main panel
        mainDoctorsPanel.add(CommonGUIPanels.getTitlePanel("Available Doctors"), BorderLayout.NORTH);
        // create doctors table panel
        JPanel doctorsTablePanel = new JPanel(new CardLayout(30, 0));
        // create items of doctors table panel
        JTable doctorsTable = new JTable();
        JScrollPane doctorsTableScrollPane = new JScrollPane(doctorsTable);
        String [] columnNames = {"Surname", "Name", "Specialization", "Mobile Number"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Doctor doctor : doctors) {
            Object[] data = {doctor.getSurname(), doctor.getName(), doctor.getSpecialization(), doctor.getMobileNumber()};
            model.addRow(data);
        }
        doctorsTable.setModel(model);
        doctorsTable.setEnabled(false);
        doctorsTable.setRowHeight(40);
        doctorsTable.setAutoCreateRowSorter(true);
        JTableHeader header = doctorsTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        doctorsTable.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // add items to doctors table panel
        doctorsTablePanel.add(doctorsTableScrollPane);
        // add doctors table panel to main panel
        mainDoctorsPanel.add(doctorsTablePanel, BorderLayout.CENTER);
        // create button panel
        JPanel buttonPanel = new JPanel();
        // create items of button panel
        JButton sortAlphabeticallyButton = new JButton("Sort Alphabetically");
        sortAlphabeticallyButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        sortAlphabeticallyButton.addActionListener(e -> {
            // sort doctors alphabetically
            Collections.sort(doctors);
            // update doctors table
            model.setRowCount(0);
            for (Doctor doctor : doctors) {
                Object[] data = {doctor.getSurname(), doctor.getName(), doctor.getSpecialization(), doctor.getMobileNumber()};
                model.addRow(data);
            }
        });
        // add items to button panel
        buttonPanel.add(sortAlphabeticallyButton);
        // add button panel to main panel
        mainDoctorsPanel.add(buttonPanel, BorderLayout.SOUTH);
        // add main panel to doctors frame
        this.add(mainDoctorsPanel);
        // set doctors frame settings
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set doctors frame visible
        this.setVisible(true);
    }
}
