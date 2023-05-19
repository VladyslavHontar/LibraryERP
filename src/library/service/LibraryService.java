package library.service;

import library.domain.Book;
import library.domain.User;
import library.repository.BookRepository;
import library.repository.DynamicArray;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryService {
  private final BookRepository repository;

  public DynamicArray getBooks() {
    return repository.findAll();
  }

  public Book addBook(User manager, String isbn, String title, String author, int year, double price, int count) {
    System.err.println("Manager " + manager + " trying to add a new book");
    Book book = repository.findByIsbn(isbn);
    if (book != null) {
      System.err.println("Manager " + manager + " tried to add a book with existed isbn: " + isbn);
      return null;
    }
    Book newBook = repository.save(Book.builder()
            .isbn(isbn)
            .title(title)
            .author(author)
            .year(year)
            .price(price)
            .count(count)
            .build());
    System.err.println("Manager " + manager + " added a new book: " + newBook);
    return null;
  }

  public Book delete(User manager, long bookId) {
    System.err.println("Manager " + manager + " trying to delete a book");
    Book bookToBeDeleted = repository.findById(bookId);

    if (bookToBeDeleted == null) {
      System.err.println("Manager " + manager + " tried to delete not existing/taken book: " + bookId);
      return null;
    }
    repository.delete(bookToBeDeleted);
    System.err.println("Manager " + manager + " deleted book: " + bookToBeDeleted);
    return bookToBeDeleted;
  }
}
