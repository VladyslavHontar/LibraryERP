package library.repository;

import library.domain.Book;
import library.domain.BookTicket;
import library.util.DynamicArray;

public interface TicketRepository extends CrudRepository<BookTicket, Long> {

  DynamicArray findByBook(Book book);

}
