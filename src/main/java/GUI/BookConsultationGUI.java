package GUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import implementation.WestminsterSkinConsultationManager;
import models.Doctor;
import models.Patient;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class BookConsultationGUI extends JFrame {

    private final double firstConsultationCostPerHour;
    private final double followUpConsultationCostPerHour;
    private ArrayList<Patient> patients;
    JPanel mainBookConsultationsPanel;
    public BookConsultationGUI(ArrayList<Doctor> doctors, ArrayList<Patient> patients, double firstConsultationCostPerHour, double followUpConsultationCostPerHour) {
        this.patients = patients;
        this.firstConsultationCostPerHour = firstConsultationCostPerHour;
        this.followUpConsultationCostPerHour = followUpConsultationCostPerHour;

        this.setTitle("Book Consultation");
        // create main panel of book consultations frame
        mainBookConsultationsPanel = new JPanel(new BorderLayout());
        // create and add title panel main panel
        mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("Select A Doctor"), BorderLayout.NORTH);
        // create consultation panel
        JPanel doctorDateTimeSelectionPanel = new JPanel();
        doctorDateTimeSelectionPanel.setLayout(new BoxLayout(doctorDateTimeSelectionPanel, BoxLayout.Y_AXIS));
        // create doctor selection panel
        JPanel doctorSelectionPanel = new JPanel();
        // create items of doctor selection panel
        String [] doctorDetails = new String[doctors.size()];
        String [] doctorsLicenseNumbers = new String[doctors.size()];
        JComboBox<String> doctorsComboBox = new JComboBox<>();
        for (int i=0; i<doctorDetails.length; i++){
            doctorDetails[i] = doctors.get(i).getSurname() + ", " + doctors.get(i).getName() + " - " + doctors.get(i).getSpecialization();
            doctorsLicenseNumbers[i] = doctors.get(i).getMedicalLicenseNumber();
            doctorsComboBox.addItem(doctorDetails[i]);
        }
        AutoCompleteDecorator.decorate(doctorsComboBox);
        doctorsComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        doctorsComboBox.setSelectedItem(null);
        // add items to doctor selection panel
        doctorSelectionPanel.add(doctorsComboBox);
        // add doctor selection panel to consultation panel
        doctorDateTimeSelectionPanel.add(doctorSelectionPanel);
        // create date Time selection panel
        JPanel dateTimeSelectionPanel = new JPanel();
        // create items of date time selection panel
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        dateSettings.setAllowEmptyDates(false);
        dateSettings.setSizeTextFieldMinimumWidth(200);
        dateSettings.setFontValidDate(new Font("SansSerif", Font.PLAIN, 20));
        dateSettings.setFontInvalidDate(new Font("SansSerif", Font.PLAIN, 20));
        dateSettings.setFontMonthAndYearMenuLabels(new Font("SansSerif", Font.PLAIN, 20));
        DatePicker appointmentDatePicker = new DatePicker(dateSettings);
        appointmentDatePicker.getComponentDateTextField().setEditable(false);
        appointmentDatePicker.setText("Select Date");
        appointmentDatePicker.setFont(new Font("SansSerif", Font.PLAIN, 20));
        String [] appointmentTimes = {"Select Time","09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM"};
        JComboBox<String> appointmentTimeComboBox = new JComboBox<>(appointmentTimes);
        appointmentTimeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        appointmentTimeComboBox.setSelectedItem(1);
        // create appointment duration panel
        JPanel appointmentDurationPanel = new JPanel();
        // create items of appointment duration panel
        String [] appointmentDurations = {"Select Duration","1 Hour", "2 Hours", "3 Hours", "4 Hours"};
        JComboBox<String> appointmentDurationComboBox = new JComboBox<>(appointmentDurations);
        appointmentDurationComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        appointmentDurationComboBox.setSelectedItem(1);
        appointmentDurationPanel.add(appointmentDurationComboBox);
        JPanel availabilityCheckPanel = new JPanel(new GridLayout(3, 1));
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
        dateTimeSelectionPanel.add(appointmentDatePicker);
        dateTimeSelectionPanel.add(appointmentTimeComboBox);
        dateTimeSelectionPanel.add(appointmentDurationPanel);
        dateTimeSelectionPanel.add(availabilityCheckPanel);
        doctorDateTimeSelectionPanel.add(dateTimeSelectionPanel);
        mainBookConsultationsPanel.add(doctorDateTimeSelectionPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        checkAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorsComboBox.getSelectedItem();
                String selectedDate = appointmentDatePicker.getText();
                String selectedTime = appointmentTimeComboBox.getSelectedItem().toString();
                String selectedDuration = appointmentDurationComboBox.getSelectedItem().toString();
                if (selectedDoctor == null || selectedDate.equals("Select Date") || selectedTime.equals("Select Time") || selectedDuration.equals("Select Duration")){
                    JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Please select all the fields to check availability.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedDoctorLicenseNumber = doctorsLicenseNumbers[doctorsComboBox.getSelectedIndex()];
                    LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDate + " " + selectedTime, DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.ENGLISH));
                    int selectedAppointmentDuration = Integer.parseInt(String.valueOf(selectedDuration.charAt(0)));
                    boolean isAvailable = WestminsterSkinConsultationManager.checkAvailability(selectedDoctorLicenseNumber, selectedDateTime, selectedAppointmentDuration);
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
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorsComboBox.getSelectedItem();
                String selectedDate = appointmentDatePicker.getText();
                String selectedTime = appointmentTimeComboBox.getSelectedItem().toString();
                String selectedDuration = appointmentDurationComboBox.getSelectedItem().toString();
                if (selectedDoctor == null || selectedDate.equals("Select Date") || selectedTime.equals("Select Time") || selectedDuration.equals("Select Duration")) {
                    JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Please select all the fields to continue.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedDoctorLicenseNumber = doctorsLicenseNumbers[doctorsComboBox.getSelectedIndex()];
                    Doctor selectedDoctorObject = WestminsterSkinConsultationManager.getDoctorByLicenseNumber(selectedDoctorLicenseNumber);
                    LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDate + " " + selectedTime, DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a"));
                    int selectedAppointmentDuration = Integer.parseInt(selectedDuration.substring(0, 1));
                    mainBookConsultationsPanel.removeAll();
                    mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("Patient Information"), BorderLayout.NORTH);
                    mainBookConsultationsPanel.add(existingOrNewPatientSelectionPanel(selectedDoctorObject, selectedDateTime, selectedAppointmentDuration), BorderLayout.CENTER,1);
                    mainBookConsultationsPanel.revalidate();
                    mainBookConsultationsPanel.repaint();
                }
            }
        });
        buttonPanel.add(checkAvailabilityButton);
        buttonPanel.add(continueButton);
        mainBookConsultationsPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainBookConsultationsPanel);
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public JPanel existingOrNewPatientSelectionPanel (Doctor selectedDoctorObject, LocalDateTime selectedDateTime, int selectedAppointmentDuration){
        boolean isAvailable = WestminsterSkinConsultationManager.checkAvailability(selectedDoctorObject.getMedicalLicenseNumber(), selectedDateTime, selectedAppointmentDuration);
        if (!isAvailable){
            selectedDoctorObject = WestminsterSkinConsultationManager.assignRandomDoctor(selectedDoctorObject, selectedDateTime, selectedAppointmentDuration);
        }
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        JPanel appointmentDetailsPanel = new JPanel(new GridLayout(3,1,10,10));
        JLabel appointmentDetailsLabel = new JLabel("Appointment Details", SwingConstants.CENTER);
        appointmentDetailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        appointmentDetailsLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel doctorLabel = new JLabel("Doctor: " + selectedDoctorObject.getSurname() + ", " + selectedDoctorObject.getName() + " (" + selectedDoctorObject.getSpecialization() + ")");
        doctorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        doctorLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel dateTimeLabel = new JLabel("Date,Time & Duration: " + selectedDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | hh:mm a")) + " | " + selectedAppointmentDuration + " Hours");
        dateTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JButton existingPatientButton = new JButton("Existing Patient");
        existingPatientButton.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        existingPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        existingPatientButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        Doctor finalSelectedDoctorObject = selectedDoctorObject;
        existingPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBookConsultationsPanel.removeAll();
                mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("Existing Patient NIC"), BorderLayout.NORTH);
                mainBookConsultationsPanel.add(existingPatientSelectionPanel(finalSelectedDoctorObject, selectedDateTime, selectedAppointmentDuration, appointmentDetailsPanel), BorderLayout.CENTER);
                mainBookConsultationsPanel.revalidate();
                mainBookConsultationsPanel.repaint();
            }
        });
        JButton newPatientButton = new JButton("New Patient");
        newPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPatientButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        newPatientButton.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        newPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBookConsultationsPanel.removeAll();
                mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("New Patient Details"), BorderLayout.NORTH);
                JPanel [] newPatientPanels = newPatientDetailsPanel(finalSelectedDoctorObject,selectedDateTime,selectedAppointmentDuration,appointmentDetailsPanel);
                mainBookConsultationsPanel.add(newPatientPanels[0], BorderLayout.CENTER);
                mainBookConsultationsPanel.add(newPatientPanels[1], BorderLayout.SOUTH);
                mainBookConsultationsPanel.revalidate();
                mainBookConsultationsPanel.repaint();
            }
        });
        appointmentDetailsPanel.add(appointmentDetailsLabel);
        appointmentDetailsPanel.add(doctorLabel);
        appointmentDetailsPanel.add(dateTimeLabel);
        selectionPanel.add(appointmentDetailsPanel, SwingConstants.CENTER);
        selectionPanel.add(Box.createRigidArea(new Dimension(0,30)));
        selectionPanel.add(existingPatientButton);
        selectionPanel.add(Box.createRigidArea(new Dimension(0,30)));
        selectionPanel.add(newPatientButton);
        mainPanel.add(selectionPanel);
        return mainPanel;
    }

    private JPanel existingPatientSelectionPanel(Doctor selectedDoctorObject, LocalDateTime selectedDateTime, int selectedAppointmentDuration, JPanel appointmentDetailsPanel) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JLabel nicLabel = new JLabel("NIC: ");
        nicLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField nicTextField = new JTextField(10);
        nicTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nic = nicTextField.getText();
                if (nic.equals("")) {
                    JOptionPane.showMessageDialog(mainPanel, "Please enter the NIC to continue.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (Patient patient : patients) {
                        if (patient.getPatientNIC().equals(nic)) {
                            mainBookConsultationsPanel.removeAll();
                            mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("Patient Information"), BorderLayout.NORTH);
                            String dateOfBirth = patient.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            mainBookConsultationsPanel.add(appointmentConfirmationPanel(selectedDoctorObject,selectedDateTime,selectedAppointmentDuration,appointmentDetailsPanel, patient.getName(), patient.getSurname(), dateOfBirth,nic,patient.getMobileNumber(),true), BorderLayout.CENTER);
                            mainBookConsultationsPanel.revalidate();
                            mainBookConsultationsPanel.repaint();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Patient not found. Please enter a valid NIC.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        selectionPanel.add(nicLabel);
        selectionPanel.add(nicTextField);
        selectionPanel.add(continueButton);
        mainPanel.add(selectionPanel);
        return mainPanel;
    }

    public JPanel[] newPatientDetailsPanel(Doctor selectedDoctorObject, LocalDateTime selectedDateTime, int selectedAppointmentDuration, JPanel appointmentDetailsPanel){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        JPanel detailsPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField firstNameTextField = new JTextField(20);
        firstNameTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel surnameLabel = new JLabel("Surname: ");
        surnameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField surnameTextField = new JTextField(20);
        surnameTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel dateOfBirthLabel = new JLabel("Date of Birth: ");
        dateOfBirthLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        datePickerSettings.setAllowEmptyDates(false);
        datePickerSettings.setSizeTextFieldMinimumWidth(200);
        datePickerSettings.setFontValidDate(new Font("SansSerif", Font.PLAIN, 20));
        datePickerSettings.setFontInvalidDate(new Font("SansSerif", Font.PLAIN, 20));
        datePickerSettings.setFontMonthAndYearMenuLabels(new Font("SansSerif", Font.PLAIN, 20));
        DatePicker dateOfBirthPicker = new DatePicker(datePickerSettings);
        dateOfBirthPicker.getComponentDateTextField().setEditable(false);
        dateOfBirthPicker.setText("Select Date of Birth");
        dateOfBirthPicker.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel nicLabel = new JLabel("NIC: ");
        nicLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField nicTextField = new JTextField(20);
        nicTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel contactNumberLabel = new JLabel("Contact Number: ");
        contactNumberLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField contactNumberTextField = new JTextField(20);
        contactNumberTextField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JPanel buttonPanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameTextField.getText();
                String surname = surnameTextField.getText();
                String dateOfBirth = dateOfBirthPicker.getText();
                String nic = nicTextField.getText();
                String contactNumber = contactNumberTextField.getText();
                if(firstName.equals("") || surname.equals("") || dateOfBirth.equals("") || dateOfBirth.equals("Select Date of Birth") || nic.equals("") || contactNumber.equals("")){
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    mainBookConsultationsPanel.removeAll();
                    mainBookConsultationsPanel.add(GUIPanels.getTitlePanel("Appointment Confirmation"), BorderLayout.NORTH);
                    mainBookConsultationsPanel.add(appointmentConfirmationPanel(selectedDoctorObject,selectedDateTime,selectedAppointmentDuration,appointmentDetailsPanel,firstName,surname,dateOfBirth,nic,contactNumber,false), BorderLayout.CENTER);
                    mainBookConsultationsPanel.revalidate();
                    mainBookConsultationsPanel.repaint();
                }
            }
        });
        buttonPanel.add(continueButton);
        detailsPanel.add(firstNameLabel);
        detailsPanel.add(firstNameTextField);
        detailsPanel.add(surnameLabel);
        detailsPanel.add(surnameTextField);
        detailsPanel.add(dateOfBirthLabel);
        detailsPanel.add(dateOfBirthPicker);
        detailsPanel.add(nicLabel);
        detailsPanel.add(nicTextField);
        detailsPanel.add(contactNumberLabel);
        detailsPanel.add(contactNumberTextField);
        wrapperPanel.add(appointmentDetailsPanel, SwingConstants.CENTER);
        wrapperPanel.add(detailsPanel);
        mainPanel.add(wrapperPanel);
        return new JPanel[]{mainPanel, buttonPanel};
    }

    public JPanel appointmentConfirmationPanel(Doctor selectedDoctorObject, LocalDateTime selectedDateTime, int selectedAppointmentDuration, JPanel appointmentDetailsPanel, String firstName, String surname, String dateOfBirth, String nic, String contactNumber, Boolean isExistingPatient) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        JPanel patientDetailsPanel = new JPanel(new GridLayout(2,1));
        JLabel patientDetailsLabel = new JLabel("Patient Details", SwingConstants.CENTER);
        patientDetailsLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JPanel patientDetailsLabelsPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        JLabel patientNameLabel = new JLabel("Patient Name: " + firstName + " " + surname);
        patientNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientDateOfBirthLabel = new JLabel("Date of Birth: " + dateOfBirth);
        patientDateOfBirthLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientNicLabel = new JLabel("NIC: " + nic);
        patientNicLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientContactNumberLabel = new JLabel("Contact Number: " + contactNumber);
        patientContactNumberLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        double cost;
        if (isExistingPatient) {
            cost = selectedAppointmentDuration * followUpConsultationCostPerHour;
        } else {
            cost = selectedAppointmentDuration * firstConsultationCostPerHour;
        }
        JLabel appointmentCostLabel = new JLabel("Appointment Cost: \u00a3 " + cost);
        appointmentCostLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JPanel additionalDetailsPanel = new JPanel(new GridLayout(2,2));
        JLabel additionalDetailsLabel = new JLabel("Enter a note (Optional)", SwingConstants.LEFT);
        additionalDetailsLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel additionalDetailsNoteLabel = new JLabel("Note: This note & images will be encrypted and only visible to the doctor", SwingConstants.RIGHT);
        additionalDetailsNoteLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        JTextArea additionalDetailsTextArea = new JTextArea(5, 20);
        additionalDetailsTextArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        additionalDetailsTextArea.setLineWrap(true);
        additionalDetailsTextArea.setWrapStyleWord(true);
        JScrollPane additionalDetailsScrollPane = new JScrollPane(additionalDetailsTextArea);
        additionalDetailsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        additionalDetailsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // upload image button
        JButton uploadImageButton = new JButton("Upload Image");
        uploadImageButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        final String[] imageFilePath = new String[1];
        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select an image");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",  "png");
                fileChooser.addChoosableFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imageFilePath[0] = selectedFile.getAbsolutePath();
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm and Place Appointment");
        confirmButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String additionalDetails = additionalDetailsTextArea.getText();
                if (isExistingPatient) {
                    boolean isConsultationAdded = WestminsterSkinConsultationManager.addConsultation(selectedDoctorObject.getMedicalLicenseNumber(), nic, selectedDateTime, selectedAppointmentDuration, cost, additionalDetails, imageFilePath[0]);
                    if (isConsultationAdded){
                        JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Appointment successfully placed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Appointment could not be placed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    mainBookConsultationsPanel.removeAll();
                    dispose();
                } else {
                    LocalDate patientDateOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    WestminsterSkinConsultationManager.addPatient(firstName, surname, patientDateOfBirth, contactNumber, nic);
                    boolean isConsultationAdded = WestminsterSkinConsultationManager.addConsultation(selectedDoctorObject.getMedicalLicenseNumber(), nic, selectedDateTime, selectedAppointmentDuration, cost, additionalDetails, imageFilePath[0]);
                    if (isConsultationAdded){
                        JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Appointment successfully placed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainBookConsultationsPanel, "Appointment could not be placed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    // go back to main menu
                    mainBookConsultationsPanel.removeAll();
                    dispose();
                }
            }
        });
        buttonPanel.add(confirmButton);
        additionalDetailsPanel.add(additionalDetailsLabel);
        additionalDetailsPanel.add(additionalDetailsNoteLabel);
        additionalDetailsPanel.add(additionalDetailsScrollPane);
        additionalDetailsPanel.add(uploadImageButton);
        patientDetailsPanel.add(patientDetailsLabel);
        patientDetailsLabelsPanel.add(patientNameLabel);
        patientDetailsLabelsPanel.add(patientDateOfBirthLabel);
        patientDetailsLabelsPanel.add(patientNicLabel);
        patientDetailsLabelsPanel.add(patientContactNumberLabel);
        patientDetailsLabelsPanel.add(appointmentCostLabel);
        patientDetailsPanel.add(patientDetailsLabelsPanel);
        wrapperPanel.add(appointmentDetailsPanel);
        wrapperPanel.add(patientDetailsPanel);
        wrapperPanel.add(additionalDetailsPanel);
        mainBookConsultationsPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(wrapperPanel);
        return mainPanel;
    }
}
