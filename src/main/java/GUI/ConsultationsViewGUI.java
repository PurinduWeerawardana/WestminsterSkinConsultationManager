package GUI;

import models.Consultation;
import models.Doctor;
import models.Patient;
import utils.DataEncryptionDecryption;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;


public class ConsultationsViewGUI extends JFrame {
    ArrayList<Patient> patients;
    ArrayList<Doctor> doctors;

    public ConsultationsViewGUI(ArrayList<Consultation> consultations, ArrayList<Patient> patients, ArrayList<Doctor> doctors) {
        this.patients = patients;
        this.doctors = doctors;
        this.setTitle("View Consultations");
        JPanel viewConsultationsPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JLabel enterNICLabel = new JLabel("Enter NIC: ");
        enterNICLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JTextField enterNICField = new JTextField(10);
        enterNICField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        searchButton.addActionListener(e -> {
            String nic = enterNICField.getText();
            ArrayList<Consultation> patientConsultations = new ArrayList<>();
            if (nic.length() != 0) {
                for (Consultation consultation : consultations) {
                    if (consultation.getPatient().equals(nic)) {
                        patientConsultations.add(consultation);
                    }
                }
                if (patientConsultations.size() != 0) {
                    viewConsultationsPanel.remove(mainPanel);
                    viewConsultationsPanel.add(showConsultationsTable(patientConsultations), BorderLayout.CENTER);
                    viewConsultationsPanel.revalidate();
                    viewConsultationsPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "No consultations found for the given NIC", "No Consultations Found", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid NIC");
            }
        });
        viewConsultationsPanel.add(GUIPanels.getTitlePanel("View Consultations"), BorderLayout.NORTH);
        mainPanel.add(enterNICLabel);
        mainPanel.add(enterNICField);
        mainPanel.add(searchButton);
        viewConsultationsPanel.add(mainPanel);
        this.add(viewConsultationsPanel, BorderLayout.CENTER);
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private JPanel showConsultationsTable(ArrayList<Consultation> patientConsultations) {
        JPanel consultationsTablePanel = new JPanel(new CardLayout());
        JPanel patientDetailsPanel = new JPanel();
        patientDetailsPanel.setLayout(new BoxLayout(patientDetailsPanel, BoxLayout.Y_AXIS));
        String patientNIC = patientConsultations.get(0).getPatient();
        Patient selectedPatient = null;
        for (Patient patient : patients) {
            if (patient.getPatientNIC().equals(patientNIC)) {
                selectedPatient = patient;
                break;
            }
        }
        JLabel patientNameLabel = new JLabel("Patient Name: " + selectedPatient.getName() + " " + selectedPatient.getSurname());
        patientNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientNICLabel = new JLabel("Patient NIC: " + selectedPatient.getPatientNIC());
        patientNICLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel infoLabel = new JLabel("Double click on a consultation to view the details");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        String[] columnNames = {"Consultation ID", "Doctor", "Date", "Time", "Duration", "Fee"};
        JTable consultationsTable = new JTable(){
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        consultationsTable.setRowHeight(40);
        consultationsTable.setCellSelectionEnabled(false);
        consultationsTable.setRowSelectionAllowed(true);
        DefaultTableModel consultationsTableModel = new DefaultTableModel(columnNames, 0);
        consultationsTable.setModel(consultationsTableModel);
        JTableHeader header = consultationsTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        consultationsTable.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(consultationsTable);
        for (Consultation patientConsultation : patientConsultations) {
            Object rowData[] = new Object[6];
            rowData[0] = patientConsultation.getConsultationId();
            Doctor selectedDoctor = null;
            for (Doctor doctor : doctors) {
                if (doctor.getMedicalLicenseNumber().equals(patientConsultation.getDoctor())) {
                    selectedDoctor = doctor;
                    break;
                }
            }
            rowData[1] = selectedDoctor.getName() + " " + selectedDoctor.getSurname();
            rowData[2] = patientConsultation.getBookedDateTime().toLocalDate();
            rowData[3] = patientConsultation.getBookedDateTime().toLocalTime();
            rowData[4] = patientConsultation.getDuration();
            rowData[5] = patientConsultation.getCost();
            consultationsTableModel.addRow(rowData);
        }
        Patient finalSelectedPatient = selectedPatient;
        consultationsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2){
                int row = consultationsTable.rowAtPoint(e.getPoint());
                String consultationID = consultationsTable.getValueAt(row, 0).toString();
                Consultation selectedConsultation = null;
                for (Consultation consultation : patientConsultations) {
                    if (consultation.getConsultationId().equals(consultationID)) {
                        selectedConsultation = consultation;
                        break;
                    }
                }
                if (selectedConsultation != null) {
                    try {
                        consultationDetailsGUI(selectedConsultation, finalSelectedPatient);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }}
        });
        patientDetailsPanel.add(patientNameLabel);
        patientDetailsPanel.add(patientNICLabel);
        patientDetailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        patientDetailsPanel.add(infoLabel);
        patientDetailsPanel.add(scrollPane);
        consultationsTablePanel.add(patientDetailsPanel);
        this.remove(this.getContentPane());
        this.setVisible(true);
        return consultationsTablePanel;
    }

    private void consultationDetailsGUI(Consultation consultation, Patient patient) throws Exception {
        JFrame consultationDetailsFrame = new JFrame("Consultation Details");
        consultationDetailsFrame.setLayout(new BorderLayout());
        JPanel consultationDetailsPanel = new JPanel(new GridLayout());
        consultationDetailsPanel.setLayout(new BoxLayout(consultationDetailsPanel, BoxLayout.Y_AXIS));
        JLabel consultationIDLabel = new JLabel("Consultation ID: " + consultation.getConsultationId());
        consultationIDLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientNameLabel = new JLabel("Patient Name: " + patient.getName() + " " + patient.getSurname());
        patientNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel patientNICLabel = new JLabel("Patient NIC: " + patient.getPatientNIC());
        patientNICLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel doctorNameLabel = new JLabel("Doctor Name: ");
        doctorNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getMedicalLicenseNumber().equals(consultation.getDoctor())) {
                selectedDoctor = doctor;
                break;
            }
        }
        JLabel doctorName = new JLabel(selectedDoctor.getName() + " " + selectedDoctor.getSurname());
        doctorName.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel consultationDateLabel = new JLabel("Consultation Date: " + consultation.getBookedDateTime().toLocalDate());
        consultationDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel consultationTimeLabel = new JLabel("Consultation Time: " + consultation.getBookedDateTime().toLocalTime());
        consultationTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel consultationDurationLabel = new JLabel("Consultation Duration: " + consultation.getDuration());
        consultationDurationLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel consultationFeeLabel = new JLabel("Consultation Fee: " + consultation.getCost());
        consultationFeeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        String notesPath = consultation.getNotesFilePath();
        String notes = "No notes available";
        if (notesPath.length() > 0) {
            notes = DataEncryptionDecryption.decryptData(notesPath,consultation.getNotesEncryptionKey());
        }
        JLabel consultationNotesLabel = new JLabel("Consultation Notes: " + notes);
        consultationNotesLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel imageCaptionLabel = new JLabel("Image: ");
        imageCaptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel imageLabel = new JLabel();
        String imagePath = consultation.getImageFilePath();
        String decryptedImagePath = ".\\decrypted\\temp.png";
        if (imagePath.length() > 0) {
            DataEncryptionDecryption.decryptData(imagePath, decryptedImagePath, consultation.getImageEncryptionKey());
            ImageIcon imageIcon = new ImageIcon(decryptedImagePath);
            Image image = imageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("No image available");
        }
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        scrollPane.createHorizontalScrollBar();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(500, 500));
        imageLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        consultationDetailsPanel.add(consultationIDLabel);
        consultationDetailsPanel.add(patientNameLabel);
        consultationDetailsPanel.add(patientNICLabel);
        consultationDetailsPanel.add(doctorNameLabel);
        consultationDetailsPanel.add(doctorName);
        consultationDetailsPanel.add(consultationDateLabel);
        consultationDetailsPanel.add(consultationTimeLabel);
        consultationDetailsPanel.add(consultationDurationLabel);
        consultationDetailsPanel.add(consultationFeeLabel);
        consultationDetailsPanel.add(consultationNotesLabel);
        consultationDetailsPanel.add(imageCaptionLabel);
        consultationDetailsPanel.add(scrollPane);
        consultationDetailsFrame.add(consultationDetailsPanel);
        consultationDetailsFrame.setSize(900, 600);
        consultationDetailsFrame.setLocationRelativeTo(null);
        consultationDetailsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (imagePath != null) {
                    File file = new File(decryptedImagePath);
                    file.delete();
                }
            }
        });
        consultationDetailsFrame.setVisible(true);
        consultationDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

//    JLabel notesLabel = new JLabel("Notes: ");
//            notesLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
//                    JTextArea notesTextArea = new JTextArea();
//                    notesTextArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
//                    notesTextArea.setLineWrap(true);
//                    notesTextArea.setWrapStyleWord(true);
//                    notesTextArea.setEditable(false);
//                    notesTextArea.setRows(10);
//                    notesTextArea.setColumns(50);
//                    notesTextArea.setText(new String(Files.readAllBytes(Paths.get(notesPath))));
//                    JScrollPane scrollPane = new JScrollPane(notesTextArea);
//                    consultationDetailsPanel.add(notesLabel);
//                    consultationDetailsPanel.add(scrollPane);