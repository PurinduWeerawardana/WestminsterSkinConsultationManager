import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class GUI{
    private JFrame frame;
    private JPanel mainPanel;
    private JButton viewDoctorsButton;
    private JButton bookConsultationButton;
    private JButton viewConsultationsButton;
    public GUI() {
        frame = new JFrame("Westminster Skin Consultations");
        mainPanel = new JPanel();
        viewDoctorsButton = new JButton("View Doctors");
        viewDoctorsButton.addActionListener(e -> {
            viewDoctors();
        });
        bookConsultationButton = new JButton("Book Consultation");
        bookConsultationButton.addActionListener(e -> {
            bookConsultation();
        });
        viewConsultationsButton = new JButton("View Consultations");
        mainPanel.add(viewDoctorsButton);
        mainPanel.add(bookConsultationButton);
        mainPanel.add(viewConsultationsButton);
        frame.add(mainPanel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void viewDoctors(){
        JFrame frame = new JFrame("View Doctors");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        JTable doctorsTable = new JTable();
        doctorsTable.setRowHeight(30);
        doctorsTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(doctorsTable);
        JButton sortButton = new JButton("Sort Alphabetically");
        String [] columnNames = {"Surname", "Name", "Specialization", "Mobile Number", "Book Consultation"};
        ArrayList <Doctor> doctors = WestminsterSkinConsultationManager.doctors;
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Doctor doctor : doctors) {
            Object[] data = {doctor.getSurname(), doctor.getName(), doctor.getSpecialization(), doctor.getMobileNumber(), new JButton("Book")};
            model.addRow(data);
        }
        sortButton.addActionListener(e -> {
            Collections.sort(doctors);
            model.setRowCount(0);
            for (Doctor doctor : doctors) {
                Object[] data = {doctor.getSurname(), doctor.getName(), doctor.getSpecialization(), doctor.getMobileNumber(), new JButton("Book")};
                model.addRow(data);
            }
        });
        doctorsTable.setModel(model);
        doctorsTable.setRowSelectionAllowed(true);
        frame.add(scrollPane);
        frame.add(sortButton);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void bookConsultation(){
        JFrame frame = new JFrame("Book Consultation");
        frame.setLayout(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel doctorLabel = new JLabel("Doctor: ");
        ArrayList <Doctor> doctors = WestminsterSkinConsultationManager.doctors;
        JComboBox<Object> doctorComboBox = new JComboBox<>();
        for (int i = 0; i < doctors.size(); i++) {
            doctorComboBox.addItem((i+1) + ". " + doctors.get(i).getName()+" "+doctors.get(i).getSurname() +" (" + doctors.get(i).getMedicalLicenseNumber() + ")");
        }
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(20);
        JLabel surnameLabel = new JLabel("Surname: ");
        JTextField surnameField = new JTextField(20);
        JLabel dateOfBirthLabel = new JLabel("Date of Birth: ");
//        JTextField dateOfBirthField = new JTextField(20);
        DatePickerSettings dateOfBirthPickerSettings = new DatePickerSettings();
        dateOfBirthPickerSettings.setAllowKeyboardEditing(false);
        dateOfBirthPickerSettings.setAllowEmptyDates(false);
        DatePicker dateOfBirthField = new DatePicker(dateOfBirthPickerSettings);
        dateOfBirthField.setText("Select Date of Birth");
        JLabel mobileNumberLabel = new JLabel("Mobile Number: ");
        JTextField mobileNumberField = new JTextField(20);
        JLabel nicLabel = new JLabel("NIC Number: ");
        JTextField nicField = new JTextField(20);
        JLabel notesLabel = new JLabel("Specialization: ");
        JLabel appointmentDateLabel = new JLabel("Appointment Date: ");
        JTextField appointmentDateField = new JTextField(20);
        JLabel appointmentTimeLabel = new JLabel("Appointment Time: ");
//        JTextField appointmentTimeField = new JTextField(20);
        DatePickerSettings appointmentDatePickerSettings = new DatePickerSettings();
        appointmentDatePickerSettings.setAllowKeyboardEditing(false);
        appointmentDatePickerSettings.setAllowEmptyDates(false);
        DatePickerSettings appointmentDateTimePickerSettings = new DatePickerSettings();
        appointmentDateTimePickerSettings.setAllowKeyboardEditing(false);
        appointmentDateTimePickerSettings.setAllowEmptyDates(false);
        DateTimePicker appointmentDateTimeField = new DateTimePicker();
        appointmentDateTimeField.datePicker.setSettings(appointmentDatePickerSettings);
        appointmentDateTimeField.datePicker.setText("Select Date");
        appointmentDateTimeField.timePicker.setText("Select Time");
        JTextField notesField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String selectedDoctor = Objects.requireNonNull(doctorComboBox.getSelectedItem()).toString();
            String doctorsMedicalLicenseNumber = selectedDoctor.substring(selectedDoctor.indexOf("(")+1, selectedDoctor.indexOf(")"));
            System.out.println(doctorsMedicalLicenseNumber);
            String name = nameField.getText();
            String surname = surnameField.getText();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthField.getText());
            String mobileNumber = mobileNumberField.getText();
            String nicNumber = nicField.getText();
//            LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateField.getText()+"T"+appointmentTimeField.getText());
            String notes = notesField.getText();
            Patient patient = new Patient(name, surname, dateOfBirth, mobileNumber, nicNumber);
            WestminsterSkinConsultationManager.addPatient(patient);
            WestminsterSkinConsultationManager.addConsultation(doctorsMedicalLicenseNumber, nicNumber, LocalDateTime.now(), 25, notes);
            frame.dispose();
        });
        formPanel.add(doctorLabel);
        formPanel.add(doctorComboBox);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(surnameLabel);
        formPanel.add(surnameField);
        formPanel.add(dateOfBirthLabel);
        formPanel.add(dateOfBirthField);
        formPanel.add(mobileNumberLabel);
        formPanel.add(mobileNumberField);
        formPanel.add(nicLabel);
        formPanel.add(nicField);
        formPanel.add(notesLabel);
        formPanel.add(notesField);
        formPanel.add(appointmentDateLabel);
        formPanel.add(appointmentDateField);
        formPanel.add(appointmentTimeLabel);
        formPanel.add(appointmentDateTimeField);
        buttonPanel.add(submitButton);
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}
