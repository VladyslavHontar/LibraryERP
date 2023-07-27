package library.v2_0.repository;

import library.v2_0.domain.Book;
import library.v2_0.domain.BookTicket;

import java.util.List;

public interface BookTicketRepository extends Repository<BookTicket, Long> {

  List<BookTicket> findByBook(Book book);
}
