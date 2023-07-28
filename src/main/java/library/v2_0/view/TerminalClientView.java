package library.v2_0.view;

import library.v2_0.domain.Book;
import library.v2_0.domain.BookTicket;
import library.v2_0.domain.User;
import library.v2_0.infrastructure.Model;
import library.v2_0.terminal.Terminal;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class TerminalClientView implements View {

  private static final String CLIENT_VIEW = "client_view";
  private static final String USER_CONTROLLER = "user_controller";

  private final Terminal terminal;
  private final Map<String, Consumer<Model>> responseHandlers = Map.of(
          "takeBook", this::takeBook,
          "returnBook", this::returnBook,
          "findBook", this::findBook
                                                                      );
  private final Map<String, BiFunction<Model, String[], String>> commands = new LinkedHashMap<>(){{
    put("TAKE_BOOK", TerminalClientView.this::commandTakeBook);
    put("RETURN_BOOK", TerminalClientView.this::commandReturnBook);
    put("FIND_BOOK", TerminalClientView.this::commandFindBook);
  }};

  @Override
  public String update(Model inModel, Model outModel) {
    User client = inModel.get("user");

    inModel.<String>tryGet("response").ifPresentOrElse(handleResponse(inModel), () -> showFirstEntranceGreeting(client));
    String target;
    do {
      String listOfCommands = commands.keySet().stream().collect(joining(", ", "[", "]"));
      showFilterFormat("#", "# Please enter on of the command " + listOfCommands);

      String[] parts = terminal.readLine().split(" ");
      String command = parts[0].toUpperCase();
      target = commands.getOrDefault(command, this::commandUnknown).apply(outModel, parts);
    } while (CLIENT_VIEW.equals(target));
    return target;
  }

  private String commandFindBook(Model model, String...arguments) {
    Map<String, String> filters = new HashMap<>();
    if (arguments.length > 1) {
      for (String filter : arguments[1].split("&")) {
        String[] filterParts = filter.split("=");
        String filterName = filterParts[0];
        String filterValue = filterParts[1];
        switch (filterName) {
          case "isbn":
            filters.put("isbn", filterValue);
            break;

          case "title":
            filters.put("title", filterValue);
            break;

          case "author":
            filters.put("author", filterValue);
            break;

          case "year":
            filters.put("year", filterValue);
            break;
        }
      }
    }
    model.put("filters", filters);
    model.put("action", "showBooks");
    return USER_CONTROLLER;
  }

  private void showFilterFormat(String str, String str1) {
    terminal.println(str);
    terminal.println(str1);
    terminal.print("> ");
  }

  private String commandReturnBook(Model model, String...arguments) {
    model.put("bookId", arguments[1]);
    model.put("action", "returnBook");
    return USER_CONTROLLER;
  }

  private String commandTakeBook(Model model, String...arguments) {
    model.put("bookId", arguments[1]);
    model.put("action", "takeBook");
    return USER_CONTROLLER;
  }

  private void findBook(Model model) {
    Map<Book, List<BookTicket>> booksWithTickets = model.get("booksWithTickets");

    if (booksWithTickets.isEmpty()) {
      terminal.println("There are no books in library");
    } else {
      terminal.println("# List of books: ");
      booksWithTickets.forEach((book, tickets) -> terminal.println("# " + book));
    }
  }

  private void returnBook(Model model) {
    Optional<String> error = model.tryGet("error");
    if (error.isPresent()) {
      terminal.println("# Failed to return a book!");
      terminal.println("# Error: " + error.get());
    } else {
      Book returnedBook = model.get("returnBook");
      terminal.println("# Book was returned: " + returnedBook);
    }
  }

  private void takeBook(Model model) {
    Optional<String> error = model.tryGet("error");
    if (error.isPresent()) {
      terminal.println("# Failed to take a book!");
      terminal.println("# Error: " + error.get());
    } else {
      Book newBook = model.get("takeBook");
      terminal.println("# Book was taken: " + newBook);
    }
  }
  private Consumer<String> handleResponse(Model inModel) {
    return response -> responseHandlers.get(response).accept(inModel);
  }
  private void showFirstEntranceGreeting(User admin) {
    terminal.println("############################");
    terminal.println("# Entered to the client mode.");
    terminal.println("# Hello " + admin.getUsername() + "!");
  }
  private String commandUnknown(Model model, String...arguments) {
    terminal.println("# Unknown command, please try again...");
    return CLIENT_VIEW;
  }
}
