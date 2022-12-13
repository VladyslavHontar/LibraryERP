import com.sun.source.util.SourcePositions;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Type in your count: ");
        int x = sc.nextInt();
        int[] count = new int[x];

        for (int i = 0; i < count.length; i++) {
            count[i] = (int) (Math.random() * 100) + 1;
            System.out.println(count[i] + "");

        }
        int min_number = count[0];

        for (int i = 0; i < count.length; i++) {
            if (count[i] < min_number) {
                min_number = count[i];
            }
        }
        System.out.println("Min number: " + min_number);
    }
}