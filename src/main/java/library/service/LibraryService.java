package library.service;

import library.domain.Book;
import library.domain.BookTicket;
import library.domain.User;
import library.repository.BookRepository;
import library.util.CustomOptional;
import library.util.DynamicArray;
import library.repository.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class LibraryService {
  private final BookRepository bookRepository;
  private final TicketRepository ticketRepository;

  public DynamicArray getBooks() {
    return bookRepository.findAll();
  }

  public Book addBook(User manager, String isbn, String title, String author, int year, double price, int count) {
    System.err.println("Manager " + manager + " trying to add a new book");
    Book book = bookRepository.findByIsbn(isbn);
    if (book != null) {
      System.err.println("Manager " + manager + " tried to add a book with existed isbn: " + isbn);
      return book;
    }
    Book newBook = bookRepository.save(Book.builder()
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
    CustomOptional<Book> optionalBook = bookRepository.findById(bookId);
    if (optionalBook.isEmpty()) {
      throw new IllegalArgumentException("Book with id " + bookId + " not found");
    }
    Book bookToBeDeleted = optionalBook.get();
    bookRepository.delete(bookToBeDeleted);
    System.err.println("Manager " + manager + " deleted book: " + bookToBeDeleted);
    return bookToBeDeleted;
  }

  public Book takeBook(User user, String bookISBN) {
    System.err.println("User " + user + " trying to take a book");
    Book bookToBeTaken = bookRepository.findByIsbn(bookISBN);
    if (bookToBeTaken == null) {
      System.err.println("User " + user + " tried to take not existing book: " + bookISBN);
      return null;
    }
    if (bookToBeTaken.getCount() == 0) {
      System.err.println("User " + user + " tried to take a book that is not available: " + bookISBN);
      return null;
    }
    bookToBeTaken.setCount(bookToBeTaken.getCount() - 1);
    System.err.println("User " + user + " took a book: " + bookToBeTaken);
    return bookToBeTaken;
  }

  public Book returnBook(User user, String bookISBN) {
    System.err.println("User " + user + " trying to return a book");
    Book bookToBeReturned = bookRepository.findByIsbn(bookISBN);

    if (bookToBeReturned == null) {
      System.err.println("User " + user + " tried to return not existing book: " + bookISBN);
      return null;
    }
      bookToBeReturned.setCount(bookToBeReturned.getCount() + 1);
      System.err.println("User " + user + " returned a book: " + bookToBeReturned);
      return bookToBeReturned;
  }
  public BookTicket takeBook(User user, long bookId) {
    System.err.println("User " + user + " trying to take a book");
    Book bookToBeTaken = bookRepository
            .findById(bookId)
            .filter(book -> book.getCount() > 0)
            .orElseThrow(new NoSuchElementException());

    BookTicket ticket = ticketRepository.save(BookTicket.builder()
                                                        .book(bookToBeTaken)
                                                        .user(user)
                                                        .build());
    bookToBeTaken.setCount(bookToBeTaken.getCount() - 1);

    System.err.println("User " + user + " took a book: " + bookToBeTaken + " with ticket: " + ticket);
    return ticket;
  }
}
