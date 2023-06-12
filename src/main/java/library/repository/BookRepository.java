package library.repository;

import library.domain.Book;
import library.util.DynamicArray;

public interface BookRepository extends CrudRepository<Book, Long> {

  Book findByIsbn(String isbn);

  DynamicArray<Book> findByTitle(String title);

  DynamicArray<Book> findByAuthor(String author);

}
