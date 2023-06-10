package library.repository;

import library.domain.Book;
import library.util.DynamicArray;

public interface BookRepository extends CrudRepository<Book, Long> {

  Book findByIsbn(String isbn);

  DynamicArray findByTitle(String title);

  DynamicArray findByAuthor(String author);

}
