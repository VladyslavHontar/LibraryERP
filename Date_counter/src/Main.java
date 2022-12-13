import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
//TODO Your Code Starts HERE
/*
 * Create prompt for user to select Day, Month and Year as ints
 * Repeat the prompt twice, to get two dates
 * Dates needs to be formatted in "dd MM yyyy" like : "23 01 2021"
 * Use provided method int getDaysBetweenTwoDates(String dateString1, String
 dateString2) to calculate difference in days
 * Print out the answer.
 * Repeat while selection != 0
/*
 * Calculates days difference between two string dates in "dd MM yyyy" format * @param dateString1 string date1
 * @param dateString1 string date1
 * @param dateString2 string date2
 * @return int representation of days in between the input dates
 */
        Scanner sc = new Scanner(System.in);
        int t;
        do {
            System.out.print("Enter Date 1 Day: ");
            int d1 = sc.nextInt();
            System.out.print("Enter Date 1 Month: ");
            int m1 = sc.nextInt();
            System.out.print("Enter Date 1 Year: ");
            int y1 = sc.nextInt();
            String i1 = d1 + " " + m1 + " " + y1;

            System.out.println();

            System.out.print("Enter Date 2 Day: ");
            int d2 = sc.nextInt();
            System.out.print("Enter Date 2 Month: ");
            int m2 = sc.nextInt();
            System.out.print("Enter Date 2 Year: ");
            int y2 = sc.nextInt();
            String i2 = d2 + " " + m2 + " " + y2;
            int daysBetween = getDaysBetweenTwoDates(i1, i2);
            System.out.println("Days: " + daysBetween);

            System.out.println();

            System.out.print("For new calculation enter 1, otherwise enter 0 to quit: ");
            t = sc.nextInt();
        } while (t != 0);

    }

    private static int getDaysBetweenTwoDates(String dateString1, String dateString2) {
        int days = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
            try {
                Date date1 = myFormat.parse(dateString1);
                Date date2 = myFormat.parse(dateString2);
                long diff = date2.getTime() - date1.getTime();
                days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Math.abs(days);
    }
}