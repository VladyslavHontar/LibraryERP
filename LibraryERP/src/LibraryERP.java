import java.util.Arrays;
import java.util.Scanner;

public class LibraryERP {
  private static String[] isbns = {};
  private static String[] titles = {};
  private static String[] authors = {};
  private static int[] years = {};
  private static double[] prices = {};
  private static int countBooks = 0;

  public static void main(String[] args) {
    System.out.println("Program started!");

    Scanner sc = new Scanner(System.in);
    String userInput;
    do {
      System.out.print("Input command: ");
      userInput = sc.nextLine();

      String[] tokens = userInput.split(", ");
      String command = tokens[0].toUpperCase();
      switch (command) {
        case "ADD" -> {
          String isbn = tokens[1];
          String title = tokens[2];
          String author = tokens[3];
          int year = Integer.parseInt(tokens[4]);
          double price = Double.parseDouble(tokens[5]);

          addBook(isbn, title, author, year, price);
        }
        case "REMOVE" -> {
          String isbn = tokens[1];
          removeBook(isbn);
        }
        case "PRINT" -> printStorage();
        case "GET" -> {
          String isbn = tokens[1];
          getBookByIsbn(isbn);
        }
        case "GET_BY_AUTHOR" -> {
          String author = tokens[1];
          getBookByAuthor(author);
        }
        case "GET_BY_YEAR" -> {
          int year = Integer.parseInt(tokens[1]);
          getBoobByYear(year);
        }
        case "GET_TOTAL_PRICE" -> getTotalPrice();
        case "EXIT" -> {
          return;
        }
        default -> System.err.println("Provided unknown command");
      }
      } while (!userInput.equalsIgnoreCase("exit"));
      System.out.println("Program finished!");
  }

  private static void getTotalPrice() {
    double totPrice = 0;
    for (int i = 0; i < countBooks; i++) {
      totPrice += prices[i];
    }
    System.out.println("Total price is: " + totPrice);
  }

  private static void getBoobByYear(int year) {
    for (int i = 0; i < countBooks; i++) {
      if (years[i] == year) {
        System.out.println(String.join(", ", isbns[i], titles[i], authors[i], String.valueOf(years[i]), String.valueOf(prices[i])));
      }
    }
  }

  private static void getBookByAuthor(String author) {
    for (int i = 0; i < countBooks; i++) {
      if (authors[i].equals(author)) {
        System.out.println(String.join(", ", isbns[i], titles[i], authors[i], String.valueOf(years[i]), String.valueOf(prices[i])));
      }
    }
  }

  private static void getBookByIsbn(String isbn) {
    for (int i = 0; i < countBooks; i++) {
      if (isbns[i].equals(isbn)) {
        System.out.println(String.join(", ", isbns[i], titles[i], authors[i], String.valueOf(years[i]), String.valueOf(prices[i])));
      }
    }
  }

  private static void removeBook(String isbn) {
    int indexBookToRemove = -1;
      for (int i = 0; i < countBooks; i++) {
        if (isbns[i].equals(isbn)) {
          indexBookToRemove = i;
          break;
        }
      }
        if (indexBookToRemove < 0) {
          System.out.println("No book founded to remove");
          return;
        }

      if (indexBookToRemove != countBooks - 1) {
        System.arraycopy(isbns, indexBookToRemove + 1, isbns, indexBookToRemove, countBooks - indexBookToRemove);
        System.arraycopy(titles, indexBookToRemove + 1, titles, indexBookToRemove, countBooks - indexBookToRemove);
        System.arraycopy(authors, indexBookToRemove + 1, authors, indexBookToRemove, countBooks - indexBookToRemove);
        System.arraycopy(years, indexBookToRemove + 1, years, indexBookToRemove, countBooks - indexBookToRemove);
        System.arraycopy(prices, indexBookToRemove + 1, prices, indexBookToRemove, countBooks - indexBookToRemove);
      }
        countBooks--;
      }

  private static void printStorage() {
    for (int i = 0; i < countBooks; i++) {
      System.out.println(String.join(", ", isbns[i], titles[i], authors[i], String.valueOf(years[i]), String.valueOf(prices[i])));
    }
  }

  private static void addBook(String isbn, String title, String author, int year, double price) {
    if (countBooks == isbns.length) {
      increaseStorageCapacity();
    }
      isbns[countBooks] = isbn;
      titles[countBooks] = title;
      authors[countBooks] = author;
      years[countBooks] = year;
      prices[countBooks] = price;

      ++countBooks;
  }

  private static void increaseStorageCapacity() {
    isbns = Arrays.copyOf(isbns, countBooks * 2 + 1);
    titles = Arrays.copyOf(titles, countBooks * 2 + 1);
    authors = Arrays.copyOf(authors, countBooks * 2 + 1);
    years = Arrays.copyOf(years, countBooks * 2 + 1);
    prices = Arrays.copyOf(prices, countBooks * 2 + 1);
  }
}