public final class Literals {

    public static String getMainMenu() {
        return """
            \n----------------------------------Westminster Skin Consultations----------------------------------
            Select an Option:
            \tA -> Add a new doctor.
            \tD -> Delete a doctor.
            \tP -> Print the list of the doctors.
            \tS -> Save to file.
            \tG -> Open GUI.
            \tQ -> Exit.
            
            Enter the letter of your choice:""";
    }

    public static String getInvalidOptionMenu() {
        return "Invalid option. Please try again!";
    }
    public static String getExitMenu(){
        return """
                Quitting...
                Thank You!
                """;
    }
}
