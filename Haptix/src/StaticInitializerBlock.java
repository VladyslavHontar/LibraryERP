import java.util.*;

public class StaticInitializerBlock {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double b = sc.nextDouble();
        double h = sc.nextDouble();

        if (b > 0) {
            if (h > 0) {
                double area = b * h;
                System.out.printf("%.0f", area);
            } else {
                System.out.println("java.lang.Exception: Breadth and height must be positive");
            }
        } else {
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        }
    }
}