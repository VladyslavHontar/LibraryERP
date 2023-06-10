package library.repository.memory;

import library.domain.Book;
import library.repository.BookRepository;
import library.util.CustomOptional;
import library.util.DynamicArray;

public class InMemoryBookRepository implements BookRepository {

  private final DynamicArray storage = new DynamicArray();
  private long currentId = 1;

  @Override
  public Book save(Book book) {
    long id = book.getId();
    if (id > 0) {
      Book updatedBook = findById(id).get();
      updatedBook.setIsbn(book.getIsbn());
      updatedBook.setTitle(book.getTitle());
      updatedBook.setAuthor(book.getAuthor());
      updatedBook.setYear(book.getYear());
      updatedBook.setCount(book.getCount());
      updatedBook.setPrice(book.getPrice());
      System.err.println("Book updated: " + updatedBook);
      return book;
    } else {
      book.setId(currentId);
      currentId++;
      storage.add(book);
      return book;
    }
  }

  @Override
  public DynamicArray findAll() {
    return storage;   //fix it later with clone
  }

  @Override
  public CustomOptional<Book> findById(Long id) {
    for (Object bookObj : storage) {
      Book book = (Book) bookObj;
      if (book.getId() == id) {
        return new CustomOptional<>(book);
      }
    }
    return null;
  }


  @Override
  public DynamicArray findByTitle(String title) {
    DynamicArray result = new DynamicArray();
    for (Object bookObj : storage) {
      Book book = (Book) bookObj;
      if (book.getTitle().equals(title)) {
        result.add(book);
      }
    }
    return result;
  }

  @Override
  public DynamicArray findByAuthor(String author) {
    DynamicArray result = new DynamicArray();
    for (Object bookObj : storage) {
      Book book = (Book) bookObj;
      if (book.getAuthor().equals(author)) {
        result.add(book);
      }
    }
    return result;
  }

  @Override
  public Book findByIsbn(String isbn) {
    for (Object bookObj : storage) {
      Book book = (Book) bookObj;
      if (book.getIsbn().equals(isbn)) {
        return book;
      }
    }
    return null;
  }

  @Override
  public void delete(Book user) {
    storage.remove(user);
  }
}
