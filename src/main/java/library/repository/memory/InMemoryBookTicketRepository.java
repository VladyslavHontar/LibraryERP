package library.repository.memory;

import library.domain.Book;
import library.domain.BookTicket;
import library.domain.User;
import library.repository.DynamicArray;
import library.repository.TicketRepository;

public class InMemoryBookTicketRepository implements TicketRepository {

  private final DynamicArray storage = new DynamicArray();
  private long currentId = 1;
  @Override
  public DynamicArray findAll() {
    return storage;
  }

  @Override
  public BookTicket findById(long id) {
    for (Object ticketObj : storage) {
      BookTicket ticket = (BookTicket) ticketObj;
      if (ticket.getId() == id) {
        return ticket;
      }
    }
    return null;
  }

  @Override
  public BookTicket issueTicket(User user, String book) {
    System.err.println("Ticket issued for user " + user.getUsername() + " for book " + book);
    BookTicket newTicket = BookTicket.builder()
        .id(currentId)
        .user(user)
        .bookTitle(book)
        .takenTimestamp(System.currentTimeMillis())
        .build();
    currentId++;
    storage.add(newTicket);
    return newTicket;
  }

  @Override
  public void returnTicket(User user, String part, BookTicket ticket) {
    System.err.println("Ticket returned for user " + user.getUsername() + " for book " + part);
    storage.remove(ticket);
    System.err.println("Ticket removed from storage: " + ticket + "\nTicket time: " + (ticket.getTakenTimestamp() - System.currentTimeMillis()));
  }
}
