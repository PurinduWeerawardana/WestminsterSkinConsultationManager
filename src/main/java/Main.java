import implementation.WestminsterSkinConsultationManager;
import literals.Literals;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int maxDoctors = 10;
        double firstConsultationCostPerHour = 15;
        double followUpConsultationCostPerHour = 25;
        System.out.println("Welcome to Westminster Skin Consultation Manager!\nLoading Previous Data....");
        WestminsterSkinConsultationManager wscm = new WestminsterSkinConsultationManager(maxDoctors,firstConsultationCostPerHour,followUpConsultationCostPerHour);
        Scanner scanner = new Scanner(System.in);
        boolean showMenu = true;
        String menuOption;
        while (showMenu) {
            System.out.println(Literals.getMainMenu());
            menuOption = scanner.nextLine();
            switch (menuOption.toUpperCase()) {
                case "A" -> wscm.addDoctor();
                case "D" -> wscm.deleteDoctor();
                case "P" -> wscm.printDoctorsList();
                case "S" -> wscm.saveToFile();
                case "G" -> wscm.showGUI();
                case "Q" -> {
                    System.out.println(Literals.getExitMenu());
                    showMenu = false;
                }
                default -> System.out.println(Literals.getInvalidOptionMenu());
            }
        }

    }
}
