import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Times {
    public static boolean validateTime(String time) {
        String regex = "(0?[1-9]|1[0-2]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(regex);

        if (time.equals(null)) {
            return false;
        }

        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static String toMilitaryTime(String time, String meridiem) {
        String timeH;
        String timeM;
        int hh;

        // If it is 10, 11, or 12 there will be two digits for the substring
        if (time.charAt(0) == '1') {
            // Getting the hour from indexes 0 & 1
            timeH = time.substring(0, 2);
            hh = Integer.parseInt(timeH);

            //Getting the minute from the indexes 3 & 4
            timeM = time.substring(3, 5);
        }

        // Any other time there is only one digit for the substring
        else {
            //Getting the hour from index 0
            timeH = time.substring(0, 1);
            hh = Integer.parseInt(timeH);

            // Getting the minute from the indexes 2 & 3
            timeM = time.substring(2, 4);
        }

        if (meridiem.equals("PM")) {
            hh += 12;
            return hh + ":" + timeM;
        }
        else {
            // Format string to hh:mm by adding a 0 in front of hours that do not begin with 1. *E.g. 09:00
            if (time.charAt(0) != '1') return "0" + hh + ":" + timeM;
        }

        return hh + ":" + timeM;
    }

    public static void main(String[] args) {
        System.out.println("Enter two times to find the difference between them. Format like the following - \"11:00 AM to 2:00 PM\".");

        Scanner scan = new Scanner(System.in);

        String timesS = scan.nextLine();

        String[] timesArray = timesS.split(" ");

        String time1 = timesArray[0]; // Time 1
        String meridiem1 = timesArray[1]; // AM or PM
        // timesArray[2] = "to"
        String time2 = timesArray[3]; // Time 2
        String meridiem2 = timesArray[4]; // AM or PM

        while (Times.validateTime(time1) ==  false || Times.validateTime(time2) == false) {
            System.out.println("Invalid time entered. Please try again.");

            timesS = scan.nextLine();

            timesArray = timesS.split(" ");

            time1 = timesArray[0]; //Time
            meridiem1 = timesArray[1]; //AM or PM
            time2 = timesArray[3]; //Time 2
            meridiem2 = timesArray[4];  //AM or PM
        }

        time1 = toMilitaryTime(time1, meridiem1);
        time2 = toMilitaryTime(time2, meridiem2);

        LocalTime time1LT = LocalTime.parse(time1);
        LocalTime time2LT = LocalTime.parse(time2);

        long hours = ChronoUnit.HOURS.between(time1LT, time2LT);
        long minutes = ChronoUnit.MINUTES.between(time1LT, time2LT) % 60;

        //If the times are 11:00 AM and 4:02 AM, I want the difference to be the next time it is 4:02 AM, not a negative value
        if (time1LT.isAfter(time2LT)) {
            hours = 23 - Math.abs(hours);
            minutes = 60 - Math.abs(minutes);

            System.out.println("The time difference is " + hours + " hours and " + minutes + " minutes.");
        }
        else {
            System.out.println("The time difference is " + hours + " hours and " + minutes + " minutes.");
        }

        scan.close();
    }

}