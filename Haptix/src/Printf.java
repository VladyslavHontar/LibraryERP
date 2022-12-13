import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Printf {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String java = br.readLine();
        String cpp = br.readLine();
        String pyt = br.readLine();

        System.out.println("================================");
        System.out.printf("%-15s%03d%n", java);
        System.out.printf("%-15s%03d%n", cpp);
        System.out.printf("%-15s%03d%n", pyt);
        System.out.println("================================");
    }
}
