package library;

import library.domain.User;
import library.repository.DynamicArray;
import library.repository.InMemoryUserRepository;
import library.repository.UserRepository;
import library.service.UserService;

public class Launcher  {
  public static void main(String[] args) {
    System.err.println("Program started");

    Terminal terminal = new Terminal(System.in, System.out);
    UserRepository userRepository = new InMemoryUserRepository();
    UserService userService = new UserService(userRepository);
    prepareUserRepositoryMockData(userRepository);

    User user = login(terminal, userService);
    switch (user.getType()) {
      case ADMIN -> handleAdmin(terminal, userService, user);
//      case MANAGER -> handleManager();
//      case CLIENT -> handleUser();
    }

    System.err.println("Program finished");
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
          for (Object user : userService.getUsers()) {terminal.println(user);}
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
            terminal.println("Failed to delete user with id " + userId + " :(");    //fix this
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

  private static void prepareUserRepositoryMockData(UserRepository userRepository) {
    userRepository.save(User.builder().username("admin").password("admin").type(User.Type.ADMIN).build());
  }
}
