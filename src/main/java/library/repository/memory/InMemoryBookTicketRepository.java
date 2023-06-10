package library.repository.memory;

import library.domain.Book;
import library.domain.BookTicket;
import library.util.CustomOptional;
import library.util.DynamicArray;
import library.repository.TicketRepository;

public class InMemoryBookTicketRepository implements TicketRepository {

  private final DynamicArray storage = new DynamicArray();
  private long currentId = 1;

  @Override
  public BookTicket save(BookTicket takenBook) {
    return null;
  }

  @Override
  public DynamicArray findAll() {
    return storage;
  }

  @Override
  public CustomOptional<BookTicket> findById(Long id) {
    for (Object ticketObj : storage) {
      BookTicket ticket = (BookTicket) ticketObj;
      if (ticket.getId() == id) {
        return new CustomOptional<>(ticket);
      }
    }
    return null;
  }

  @Override
  public DynamicArray findByBook(Book book) {
    return null;
  }

  @Override
  public void delete(BookTicket ticket) {

  }

}
