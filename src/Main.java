import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        WestminsterSkinConsultationManager wscm = new WestminsterSkinConsultationManager(10);
        System.out.println("Welcome to Westminster Skin Consultation Manager!\nLoading Previous Data....");
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
                case "G" -> loadGUI();
                case "Q" -> {
                    System.out.println(Literals.getExitMenu());
                    showMenu = false;
                }
                default -> System.out.println(Literals.getInvalidOptionMenu());
            }
        }

    }
    public static void loadGUI() {
        GUI gui = new GUI();
    }
}
