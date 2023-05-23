package library;

import library.domain.Book;
import library.domain.User;
import library.repository.BookRepository;
import library.repository.memory.InMemoryBookRepository;
import library.repository.memory.InMemoryUserRepository;
import library.repository.UserRepository;
import library.service.LibraryService;
import library.service.UserService;

public class Launcher  {
  public static void main(String[] args) {
    System.err.println("Program started");

    Terminal terminal = new Terminal(System.in, System.out);
    UserRepository userRepository = new InMemoryUserRepository();
    BookRepository bookRepository = new InMemoryBookRepository();

    UserService userService = new UserService(userRepository);
    prepareUserRepositoryMockData(userRepository);
    prepareBookRepositoryMockData(bookRepository);

    LibraryService libraryService = new LibraryService(bookRepository);

    User user = login(terminal, userService);
    switch (user.getType()) {
      case ADMIN -> handleAdmin(terminal, userService, user);
      case MANAGER -> handleManager(terminal, libraryService, user);
      case CLIENT -> handleUser(terminal, libraryService, user);
    }

    System.err.println("Program finished");
  }

  private static User login(Terminal terminal, UserService userService) {
    do {
      terminal.print("Enter your username: ");
      String username = terminal.readLine();

      terminal.print("Enter your password: ");
      String password = terminal.readLine();

      User user = userService.authenticate(username, password);
      if (user != null) {
        terminal.println("Hello " + username + "!");
        return user;
      }
      terminal.println("Failed to login, please try again :(");
    } while (true);
  }

  private static void handleAdmin(Terminal terminal, UserService userService, User admin) {
    terminal.println("Handle mode Admin");

    do {
      terminal.println("Enter the command: ");
      String[] parts = terminal.readLine().split(" ");
      String command = parts[0].toUpperCase();
      switch (command) {
        case "LIST_USERS":
          terminal.println("List of users: ");
          userService.getUsers().forEach(terminal::println);
          break;
        case "ADD_USER":
          String username = parts[1];
          String password = parts[2];
          String type = parts[3];
          User newUser = userService.add(admin, username, password, User.Type.valueOf(type));
          terminal.println("New user was added " + newUser);
          break;
        case "DELETE_USER":
          long userId = Long.parseLong(parts[1]);
          User deletedUser = userService.delete(admin, userId);
          if (deletedUser == null) {
            terminal.println("Failed to delete user with id " + userId + " :(");
          }
          terminal.println("User: " + deletedUser + " has been deleted");
          break;
        case "EXIT":
          terminal.println("Closing admin mode...");
          return;

        default:
          terminal.println("Unknown command!");
      }
    } while (true);
  }

  private static void handleManager(Terminal terminal, LibraryService libraryService, User manager) {
    terminal.println("Handle mode Manager");
    do {
      terminal.println("Enter the command: ");
      String inputLine = terminal.readLine();
      String[] parts = inputLine.split(" ");
      String command = parts[0].toUpperCase();
      switch (command) {
        case "GET_BOOKS": {
          String isbn = null;
          String title = null;
          String author = null;
          Integer year = null;
          if (parts.length > 1) {
            String[] filters = inputLine.substring(command.length() + 1).split("&");
            for (String filter : filters) {
              String[] keyParts = filter.split("=");
              String keyName = keyParts[0];
              String keyValue = keyParts[1];
              switch (keyName) {
                case "isbn":
                  isbn = keyValue;
                  break;
                case "title":
                  title = keyValue;
                  break;
                case "author":
                  author = keyValue;
                  break;
                case "year":
                  year = Integer.parseInt(keyValue);
                  break;
              }
            }
          }
          terminal.println("List of books: ");
          for (Object bookObj : libraryService.getBooks()) {
            Book book = (Book) bookObj;
            if (isbn != null && !book.getIsbn().equals(isbn)
                    || title != null && !book.getTitle().equals(title)
                    || author != null && !book.getAuthor().equals(author)
                    || year != null && book.getYear() != year) {
              continue;
            }
            terminal.println(book);
          }
          break;
        }
        case "ADD_BOOK": {
          String isbn = parts[1];
          String title;
          String author;
          int year;
          double price;
          int count;
          if (parts.length > 8) {
            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 2; i < parts.length - 5; i++) {
              titleBuilder.append(parts[i]).append(" ");
            }
            title = titleBuilder.toString();
            author = parts[parts.length - 5] + " " + parts[parts.length - 4];
            year = Integer.parseInt(parts[parts.length - 3]);
            price = Double.parseDouble(parts[parts.length - 2]);
            count = Integer.parseInt(parts[parts.length - 1]);
          } else {
            title = parts[2];
            author = parts[3] + " " + parts[4];
            year = Integer.parseInt(parts[5]);
            price = Double.parseDouble(parts[6]);
            count = Integer.parseInt(parts[7]);
          }
          Book newBook = libraryService.addBook(manager, isbn, title, author, year, price, count);
          if (newBook != null) {
            terminal.println("Failed to add new book because of already existed ISBN " + isbn + " :(");
          } else {
            terminal.println("New book was added");
          }
          break;
        }
        case "DELETE_BOOK":
          long bookId = Long.parseLong(parts[1]);
          Book deletedBook = libraryService.delete(manager, bookId);
          if (deletedBook == null) {
            terminal.println("Failed to delete book with id " + bookId + " :(");
          } else {
            terminal.println("Book: " + deletedBook + " has been deleted");
          }
          break;
        case "EXIT":
          terminal.println("Closing manager mode...");
          return;

        default:
          terminal.println("Unknown command!");
      }
    } while (true);
  }

  private static void handleUser(Terminal terminal, LibraryService libraryService, User user) {
    do {
      terminal.println("Enter the command: ");
      String inputLine = terminal.readLine();
      String[] parts = inputLine.split(" ");
      String command = parts[0].toUpperCase();
      switch (command) {
        case "GET_BOOKS": {
          String isbn = null;
          String title = null;
          String author = null;
          Integer year = null;
          if (parts.length > 1) {
            String[] filters = inputLine.substring(command.length() + 1).split("&");
            for (String filter : filters) {
              String[] keyParts = filter.split("=");
              String keyName = keyParts[0];
              String keyValue = keyParts[1];
              switch (keyName) {
                case "isbn":
                  isbn = keyValue;
                  break;
                case "title":
                  title = keyValue;
                  break;
                case "author":
                  author = keyValue;
                  break;
                case "year":
                  year = Integer.parseInt(keyValue);
                  break;
              }
            }
          }
          terminal.println("List of books: ");
          for (Object bookObj : libraryService.getBooks()) {
            Book book = (Book) bookObj;
            if (isbn != null && !book.getIsbn().equals(isbn)
                    || title != null && !book.getTitle().equals(title)
                    || author != null && !book.getAuthor().equals(author)
                    || year != null && book.getYear() != year) {
              continue;
            }
            terminal.println(book);
          }
          break;
        }
        case "TAKE_BOOK":
          Book takenBook = libraryService.takeBook(user, parts[1]);
          if (takenBook == null) {
            terminal.println("Failed to take a book with ISBN " + parts[1] + " :(");
            break;
          }
          terminal.println("You have taken a book: " + takenBook);
          break;
        case "RETURN_BOOK":
          Book returnedBook = libraryService.returnBook(user, parts[1]);
          if (returnedBook != null) {
            terminal.println("You have returned a book: " + returnedBook);
            break;
          }
          terminal.println("We dont have any books with this ISBN " + parts[1] + " :(");
          break;
        case "EXIT":
          terminal.println("Closing admin mode...");
          return;

        default:
          terminal.println("Unknown command!");
      }
    } while (true);
  }

  private static void prepareUserRepositoryMockData(UserRepository userRepository) {
    userRepository.save(User.builder().username("admin").password("admin").type(User.Type.ADMIN).build());
    userRepository.save(User.builder().username("manager").password("manager").type(User.Type.MANAGER).build());
    userRepository.save(User.builder().username("user").password("user").type(User.Type.CLIENT).build());
  }

  private static void prepareBookRepositoryMockData(BookRepository bookRepository) {
    bookRepository.save(Book.builder().isbn("1234567890").title("Java").author("Ion Creanga").year(2020).price(99.99).count(10).build());
    bookRepository.save(Book.builder().isbn("1234567891").title("Зов Предков").author("Mihai Eminescu").year(2018).price(87.99).count(5).build());
    bookRepository.save(Book.builder().isbn("1234567892").title("Гроза").author("Ion Creanga").year(2011).price(30.99).count(4).build());
    bookRepository.save(Book.builder().isbn("1234567893").title("Война и Мир").author("Lev Tolstoi").year(2019).price(99.99).count(10).build());
  }
}
