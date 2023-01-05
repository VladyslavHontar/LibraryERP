import java.util.Scanner;
import java.util.function.Consumer;
public class LetterCounter {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a string: ");
    String input = scanner.nextLine();
    input = input.toLowerCase();
    int[] letterCounts = new int[26];
    Consumer<Character> countLetters = (c) -> {
      if (c >= 'a' && c <= 'z') {
        letterCounts[c - 'a']++;
      }
    };
    input.chars().mapToObj(i -> (char)i).forEach(countLetters);
    for (int i = 0; i < 26; i++) {
      char c = (char)('a' + i);
      if (letterCounts[i] > 1) {
          System.out.println(c + ": " + letterCounts[i] + " " + Integer.toBinaryString(c));
      }
    }
  }
}