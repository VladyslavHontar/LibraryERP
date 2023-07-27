package library.v2_0.controller;

import library.v2_0.domain.Book;
import library.v2_0.domain.BookTicket;
import library.v2_0.domain.User;
import library.v2_0.exceptions.BookInUseCannotBeDeletedException;
import library.v2_0.infrastructure.Model;
import library.v2_0.service.LibraryService;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public class ManagerController implements Controller {

  private final LibraryService libraryService;

  @Override
  public String handle(Model inModel, Model outModel) {
    User manager = inModel.get("user");
    String action = inModel.get("action");

    if ("logout".equals(action)) {
      return "login_view";
    }

    switch (action) {
      case "showBooks":
        Map<String, String> filters = inModel.get("filters");
        Predicate<Book> bookFilter = filters.entrySet()
                                            .stream()
                                            .map(entry -> getFilterForProperty(entry.getKey(), entry.getValue()))
                                            .reduce($ -> true, Predicate::and);

        Map<Book, List<BookTicket>> booksWithTickets = libraryService.findBooksBy(bookFilter)
                                                                     .stream()
                                                                     .collect(toMap(identity(), libraryService::getBookTickets, ($,$$) -> null, LinkedHashMap::new));

        outModel.put("booksWithTickets", booksWithTickets);
        outModel.put("response", "showBooks");
        break;

      case "addBook":
        String isbn = inModel.get("isbn");
        String title = inModel.get("title");
        String author = inModel.get("author");
        int year = inModel.get("year");
        double price = inModel.get("price");
        int count = inModel.get("count");

        Book newBook = libraryService.addBook(manager, isbn, title, author, year, price, count);
        outModel.put("newBook", newBook);
        outModel.put("response", "addBook");
        break;

      case "deleteBook":
        Long bookId = inModel.get("bookId");
        try {
          Book deletedBook = libraryService.delete(manager, bookId);
          outModel.put("deletedBook", deletedBook);
        } catch (NoSuchElementException ex) {
          outModel.put("error", "incorrect bookId");
        } catch (BookInUseCannotBeDeletedException ex) {
          outModel.put("error", "cannot delete a book that currently used (" + ex.getNumberOfBooksInUse() + " copies currently in use.");
        }
        outModel.put("response", "deleteBook");
        break;
    }
    return "manager_view";
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
