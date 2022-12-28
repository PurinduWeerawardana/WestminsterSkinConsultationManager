package models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class WorkSchedule{
    // array list of work days
    private ArrayList <LocalTime> workingHours;
    private ArrayList <LocalDateTime> bookedHours;

    public WorkSchedule() {
        // hardcoding working hours assuming all doctors working hours are from 9am to 5pm
        workingHours = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            workingHours.add(LocalTime.of(i, 0));
        }
        bookedHours = new ArrayList<>();
    }


    public ArrayList <LocalTime> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(ArrayList <LocalTime> workingHours) {
        this.workingHours = workingHours;
    }

    public ArrayList <LocalDateTime> getBookedHours() {
        return bookedHours;
    }

    public boolean isBooked(LocalDateTime dateTime, int durationInHours) {
        LocalDateTime[] hours = new LocalDateTime[durationInHours];
        String dateTimeToCheck = dateTime.toString().replace("T", " ");
        hours[0] = LocalDateTime.parse(dateTimeToCheck, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm",Locale.ENGLISH));
        if (durationInHours > 1) {
            for (int i = 1; i < durationInHours; i++) {
                hours[i] = hours[i-1].plusHours(1);
            }
        }
        for (LocalDateTime hour : hours) {
            if (bookedHours.contains(hour)) {
                return true;
            }
        }
        return false;
    }

    public boolean setBookedHours(LocalDateTime dateTime, int durationInHours) {
        LocalDateTime[] hours = new LocalDateTime[durationInHours];
        hours[0] = LocalDateTime.parse(dateTime.toString().replace("T", " "),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm",Locale.ENGLISH));
        if (isBooked(hours[0], durationInHours)) {
            return false;
        }
        if (durationInHours == 1) {
            bookedHours.add(hours[0]);
            for (LocalDateTime hour : hours) {
                System.out.println(hour);
            }
            return true;
        } else{
            for (int i=1; i<durationInHours; i++){
                hours[i] = hours[i-1].plusHours(1);
                System.out.println(hours[i]);
                Collections.addAll(bookedHours, hours);
            }
            for (LocalDateTime hour : hours) {
                System.out.println(hour);
            }
        return true;
        }
    }

    public String toString() {
        return "Working Hours: " + workingHours + "," +
                "\tBooked Hours: " + bookedHours;
    }
}
