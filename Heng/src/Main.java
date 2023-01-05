import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a string: ");
    String str = scanner.nextLine();
    int[] letterCount = new int[26];
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter(str.charAt(i))) {
        int index = Character.toLowerCase(str.charAt(i)) - 'a';
        letterCount[index]++;
      }
    }
    for (int i = 0; i < 26; i++) {
      if (letterCount[i] != 0) {
        char letter = (char) ('a' + i);
        System.out.println(letter + ": " + letterCount[i]);
      }
    }
  }
}