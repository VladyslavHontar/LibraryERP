import java.io.*;
import java.util.*;

public class quest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int i =1;
        while (sc.hasNext()){
            String s = sc.nextLine();
            System.out.println(i + " " + s);
            i++;
        }
    }
}