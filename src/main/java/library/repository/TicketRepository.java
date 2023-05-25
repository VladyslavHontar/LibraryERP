package library.repository;

import library.domain.BookTicket;
import library.domain.User;

public interface TicketRepository {

  DynamicArray findAll();

  BookTicket findById(long id);

  BookTicket issueTicket(User user, String part);

  void returnTicket(User user, String part, BookTicket ticket);
}
