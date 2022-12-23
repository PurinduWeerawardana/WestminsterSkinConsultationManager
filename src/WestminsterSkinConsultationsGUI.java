import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class WestminsterSkinConsultationsGUI {
    JFrame homeFrame;
    JFrame doctorsFrame;
    JFrame bookConsultationsFrame;
    JFrame viewConsultationsFrame;
    public WestminsterSkinConsultationsGUI() {
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
            viewDoctors();
        });
        JButton bookConsultationButton = new JButton("Book Consultation");
        bookConsultationButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        bookConsultationButton.addActionListener(e -> {
            bookConsultation();
        });
        JButton viewConsultationsButton = new JButton("View Consultations");
        viewConsultationsButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        viewConsultationsButton.addActionListener(e -> {
            viewConsultations();
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
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);
    }

    public void viewDoctors(){
        // create doctors frame
        doctorsFrame = new JFrame("View Doctors");
        // create main panel of doctors frame
        JPanel mainDoctorsPanel = new JPanel(new BorderLayout());
        // add a title panel to the main panel
        mainDoctorsPanel.add(getTitlePanel("Available Doctors"), BorderLayout.NORTH);
        // create doctors table panel
        JPanel doctorsTablePanel = new JPanel(new CardLayout(30, 0));
        // create items of doctors table panel
        JTable doctorsTable = new JTable();
        JScrollPane doctorsTableScrollPane = new JScrollPane(doctorsTable);
        String [] columnNames = {"Surname", "Name", "Specialization", "Mobile Number"};
        ArrayList <Doctor> doctors = WestminsterSkinConsultationManager.doctors;
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Doctor doctor : doctors) {
            Object[] data = {doctor.getSurname(), doctor.getName(), doctor.getSpecialization(), doctor.getMobileNumber()};
            model.addRow(data);
        }
        doctorsTable.setModel(model);
        doctorsTable.setEnabled(false);
        doctorsTable.setRowHeight(40);
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
        doctorsFrame.add(mainDoctorsPanel);
        // set doctors frame settings
        doctorsFrame.setSize(1200, 700);
        doctorsFrame.setLocationRelativeTo(null);
        doctorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set doctors frame visible
        doctorsFrame.setVisible(true);
    }

    public void bookConsultation(){
        bookConsultationsFrame = new JFrame("Book Consultation");
        // create main panel of book consultations frame
        JPanel mainBookConsultationsPanel = new JPanel(new BorderLayout());
        // create and add title panel main panel
        mainBookConsultationsPanel.add(getTitlePanel("Select A Doctor"), BorderLayout.NORTH);
        // create consultation panel
        JPanel consultationPanel = new JPanel();
        consultationPanel.setLayout(new BoxLayout(consultationPanel, BoxLayout.Y_AXIS));
        // create doctor selection panel
        JPanel doctorSelectionPanel = new JPanel();
        // create items of doctor selection panel
        JLabel doctorSelectionLabel = new JLabel("Select a doctor");
        ArrayList <Doctor> doctors = WestminsterSkinConsultationManager.doctors;
        String [] doctorDetails = new String[doctors.size()];
        String [] doctorsLisenceNumbers = new String[doctors.size()];
        JComboBox<String> doctorsComboBox = new JComboBox<>();
        for (int i=0; i<doctorDetails.length; i++){
            doctorDetails[i] = doctors.get(i).getSurname() + ", " + doctors.get(i).getName() + " - " + doctors.get(i).getSpecialization();
            doctorsLisenceNumbers[i] = doctors.get(i).getMedicalLicenseNumber();
            doctorsComboBox.addItem(doctorDetails[i]);
        }
        AutoCompleteDecorator.decorate(doctorsComboBox);
        doctorsComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        doctorsComboBox.setSelectedItem(null);
        // add items to doctor selection panel
        doctorSelectionPanel.add(doctorsComboBox);
        // add doctor selection panel to consultation panel
        consultationPanel.add(doctorSelectionPanel);
        // create date Time selection panel
        JPanel dateTimeSelectionPanel = new JPanel();
        dateTimeSelectionPanel.setLayout(new BoxLayout(dateTimeSelectionPanel, BoxLayout.Y_AXIS));
        // create items of date time selection panel
        JLabel dateTimeLabel = new JLabel("Select Date & Time");
        dateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        DatePickerSettings appointmentDateTimePickerSettings = new DatePickerSettings();
        appointmentDateTimePickerSettings.setAllowKeyboardEditing(false);
        appointmentDateTimePickerSettings.setAllowEmptyDates(false);
        DateTimePicker appointmentDateTimePicker = new DateTimePicker();
        appointmentDateTimePicker.datePicker.setSettings(appointmentDateTimePickerSettings);
        appointmentDateTimePicker.setLayout(new FlowLayout());
        appointmentDateTimePicker.datePicker.setText("select date");
        appointmentDateTimePicker.timePicker.setFont(new Font("SansSerif", Font.PLAIN, 30));
        appointmentDateTimePicker.timePicker.setText("select time");
        appointmentDateTimePicker.datePicker.setFont(new Font("SansSerif", Font.PLAIN, 20));
        appointmentDateTimePicker.datePicker.setPreferredSize(new Dimension(270, 40));
        appointmentDateTimePicker.timePicker.setPreferredSize(new Dimension(170, 40));
        // create appointment duration panel
        JPanel appointmentDurationPanel = new JPanel();
        // create items of appointment duration panel
        JLabel appointmentDurationLabel = new JLabel("Select Appointment Duration (Hours): ");
        appointmentDurationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appointmentDurationLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        String [] appointmentDurations = {"1 Hour", "2 Hours", "3 Hours", "4 Hours"};
        JComboBox<String> appointmentDurationComboBox = new JComboBox<>(appointmentDurations);
        appointmentDurationComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        appointmentDurationComboBox.setSelectedItem(null);
        // add items to appointment duration panel
        appointmentDurationPanel.add(appointmentDurationLabel);
        appointmentDurationPanel.add(appointmentDurationComboBox);
        // create availability check panel
        JPanel availabilityCheckPanel = new JPanel(new GridLayout(3, 1));
        // create items of availability check panel
        JLabel availabilityCheckLabel = new JLabel("Availability", SwingConstants.CENTER);
        availabilityCheckLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel availabilityCheckResultLabel = new JLabel("Not Checked", SwingConstants.CENTER);
        availabilityCheckResultLabel.setForeground(Color.ORANGE);
        availabilityCheckResultLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        JLabel availabilityNoteLabel = new JLabel("Note: If the selected doctor is not available at your selected time, a random doctor will be assigned automatically.", SwingConstants.CENTER);
        availabilityNoteLabel.setLayout(new CardLayout());
        availabilityNoteLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // add items to availability check panel
        availabilityCheckPanel.add(availabilityCheckLabel);
        availabilityCheckPanel.add(availabilityCheckResultLabel);
        availabilityCheckPanel.add(availabilityNoteLabel);
        // add items to date time selection panel
        dateTimeSelectionPanel.add(dateTimeLabel);
        dateTimeSelectionPanel.add(appointmentDateTimePicker);
        dateTimeSelectionPanel.add(appointmentDurationPanel);
        dateTimeSelectionPanel.add(availabilityCheckPanel);
        // add date time selection panel to consultation panel
        consultationPanel.add(dateTimeSelectionPanel);
        // add consultation panel to main panel
        mainBookConsultationsPanel.add(consultationPanel, BorderLayout.CENTER);
        // create button panel
        JPanel buttonPanel = new JPanel();
        // create items of button panel
        JButton checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        checkAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (doctorsComboBox.getSelectedItem() == null || appointmentDateTimePicker.datePicker.getText().equals("select date") || appointmentDateTimePicker.timePicker.getText().equals("select time") || appointmentDateTimePicker.timePicker.getText() == null || appointmentDurationComboBox.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedDoctor = doctorsComboBox.getSelectedItem().toString();
                    String selectedDoctorLicenseNumber = doctorsLisenceNumbers[doctorsComboBox.getSelectedIndex()];
                    String selectedDate = appointmentDateTimePicker.datePicker.getText();
                    String selectedTime = appointmentDateTimePicker.timePicker.getText();
                    String selectedAppointmentDuration = appointmentDurationComboBox.getSelectedItem().toString();
                    String selectedAppointmentDurationInHours = selectedAppointmentDuration.substring(0, 1);
                    int selectedAppointmentDurationInHoursInt = Integer.parseInt(selectedAppointmentDurationInHours);
                    String selectedAppointmentDurationInMinutes = String.valueOf(selectedAppointmentDurationInHoursInt * 60);
                    String selectedAppointmentDurationInMinutesWithZero = selectedAppointmentDurationInMinutes + "0";
                    String selectedAppointmentDurationInMinutesWithZeroAndColon = selectedAppointmentDurationInMinutesWithZero + ":00";
                    String selectedDateTime = selectedDate + " " + selectedTime;
                    String selectedDateTimeWithAppointmentDuration = selectedDate + " " + selectedTime + " - " + selectedAppointmentDurationInMinutesWithZeroAndColon;
                    boolean isAvailable = WestminsterSkinConsultationManager.checkAvailability(selectedDoctorLicenseNumber, selectedDateTime, selectedAppointmentDurationInMinutesWithZeroAndColon);
                    if (isAvailable){
                        availabilityCheckResultLabel.setText("Available");
                        availabilityCheckResultLabel.setForeground(Color.GREEN);
                    } else {
                        availabilityCheckResultLabel.setText("Not Available");
                        availabilityCheckResultLabel.setForeground(Color.RED);
                    }
                }
            }
        });
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        continueButton.setEnabled(false);
        // add items to button panel
        buttonPanel.add(checkAvailabilityButton);
        buttonPanel.add(continueButton);
        // add button panel to main panel
        mainBookConsultationsPanel.add(buttonPanel, BorderLayout.SOUTH);
        bookConsultationsFrame.add(mainBookConsultationsPanel);
        bookConsultationsFrame.setSize(1200, 700);
        bookConsultationsFrame.setLocationRelativeTo(null);
        bookConsultationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookConsultationsFrame.setVisible(true);
    }

    public void viewConsultations(){
        viewConsultationsFrame = new JFrame("View Consultations");
        JPanel viewConsultationsPanel = new JPanel(new BorderLayout());
        // add title panel main panel
        viewConsultationsPanel.add(getTitlePanel("View Consultations"), BorderLayout.NORTH);

        viewConsultationsFrame.add(viewConsultationsPanel);
        viewConsultationsFrame.setSize(1200, 700);
        viewConsultationsFrame.setLocationRelativeTo(null);
        viewConsultationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewConsultationsFrame.setVisible(true);
    }

    public JPanel getTitlePanel(String title){
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        // create items of title panel
        JLabel titleLabel1 = new JLabel("Westminster Skin Consultations");
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel1.setFont(new Font("SansSerif", Font.PLAIN, 50));
        JLabel titleLabel2 = new JLabel(title, JLabel.CENTER);
        titleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel2.setFont(new Font("SansSerif", Font.PLAIN, 30));
        // add items to title panel
        titlePanel.add(Box.createRigidArea(new Dimension(0,30)));
        titlePanel.add(titleLabel1);
        titlePanel.add(Box.createRigidArea(new Dimension(0,30)));
        titlePanel.add(titleLabel2);
        return titlePanel;
    }

//    public static void main(String[] args) {
//        WestminsterSkinConsultationsGUI gui = new WestminsterSkinConsultationsGUI();
//    }
}
