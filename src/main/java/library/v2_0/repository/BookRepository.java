package library.v2_0.repository;

import library.v2_0.domain.Book;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface BookRepository extends Repository<Book, Long> {

  List<Book> findByTitle(String title);

  Optional<Book> findByIsbn(String isbn);

  List<Book> findByAuthor(String author);

  List<Book> findBy(Predicate<Book> predicate);
}
