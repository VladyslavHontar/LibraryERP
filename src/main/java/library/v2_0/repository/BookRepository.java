package library.v2_0.repository;

import library.v2_0.domain.Book;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface BookRepository extends Repository<Book, Long> {

  Optional<Book> findByTitle(String title);

  Optional<Book> findByIsbn(String isbn);

  Optional<Book> findByAuthor(String author);
}
