package library.repository;

import library.domain.Book;

public interface BookRepository {

  Book save(Book book);

  DynamicArray findAll();

  Book findById(long id);

  DynamicArray findByTitle(String title);

  DynamicArray findByAuthor(String author);

  Book findByIsbn(String isbn);

  void delete(Book book);
}
