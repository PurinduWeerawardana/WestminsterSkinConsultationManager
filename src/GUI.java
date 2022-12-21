import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI extends JFrame {
    private JPanel mainPanel;
    private JButton viewDoctorsButton;
    private JButton bookConsultationButton;
    private JButton viewConsultationsButton;
    public GUI() {
        mainPanel = new JPanel();
        viewDoctorsButton = new JButton("View Doctors");
        viewDoctorsButton.addActionListener(e -> {
            viewDoctors();
        });
        bookConsultationButton = new JButton("Book Consultation");
        viewConsultationsButton = new JButton("View Consultations");
        mainPanel.add(viewDoctorsButton);
        mainPanel.add(bookConsultationButton);
        mainPanel.add(viewConsultationsButton);
        add(mainPanel);
        setTitle("Skin Consultation Manager");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void viewDoctors(){
        JFrame doctorsFrame = new JFrame("Doctors");
        JPanel doctorsPanel = new JPanel();
        JTable doctorsTable = new JTable();
        String [] columnNames = {"Name", "Speciality", "Mobile Number"};
        Object [] [] doctorsData = {};
        TableModel doctorsTableModel = new DefaultTableModel(doctorsData, columnNames);
        doctorsTable.setModel(doctorsTableModel);
        doctorsPanel.add(doctorsTable);
        doctorsFrame.add(doctorsPanel);
        doctorsFrame.setSize(500, 500);
        doctorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(false);
        doctorsFrame.setLocationRelativeTo(null);
        doctorsFrame.setVisible(true);
    }
}
