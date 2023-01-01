import implementation.WestminsterSkinConsultationManager;

public class Main {
    public static void main(String[] args){
        int maxDoctors = 10;
        double firstConsultationCostPerHour = 15;
        double followUpConsultationCostPerHour = 25;
        WestminsterSkinConsultationManager wscm = new WestminsterSkinConsultationManager(maxDoctors,firstConsultationCostPerHour,followUpConsultationCostPerHour);
        wscm.showMenu();
    }
}
