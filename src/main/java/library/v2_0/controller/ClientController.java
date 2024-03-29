package library.v2_0.controller;

import library.v2_0.domain.Book;
import library.v2_0.domain.BookTicket;
import library.v2_0.domain.User;
import library.v2_0.infrastructure.Model;
import library.v2_0.service.LibraryService;
import library.v2_0.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public class ClientController implements Controller {

  private final LibraryService libraryService;

  @Override
  public String handle(Model inModel, Model outModel) {
    User user = inModel.get("user");
    String action = inModel.get("action");

    if ("logout".equals(action)) {
      return "login_view";
    }

    switch (action) {
      case "takeBook":
        Object bookId = inModel.get("bookId");
        long bookIdAsLong = (Long) bookId;
        libraryService.takeBook(user, bookIdAsLong);
        outModel.put("response", "takeBook");
        break;

        case "returnBook":
        libraryService.returnBook(user, inModel.get("bookId"));
        outModel.put("response", "returnBook");
        break;

      case "listBooks":
        Map<Book, List<BookTicket>> booksWithTickets = libraryService.getBooks()
                                                                     .stream()
                                                                     .collect(toMap(identity(), libraryService::getBookTickets, ($,$$) -> null, LinkedHashMap::new));

        outModel.put("booksWithTickets", booksWithTickets);
        outModel.put("response", "listBooks");
        break;
    }
    return "user_view";
  }
  private Predicate<Book> getFilterForProperty(String propertyName, String expectedValue) {
    switch (propertyName) {
      case "isbn":
        return book -> book.getIsbn().equals(expectedValue);

      case "title":
        return book -> book.getTitle().equals(expectedValue);

      case "author":
        return book -> book.getAuthor().equals(expectedValue);

      case "year":
        return book -> book.getYear() == Integer.parseInt(expectedValue);

      default:
        throw new IllegalArgumentException("Unknown property name for book: " + propertyName);
    }
  }
}
