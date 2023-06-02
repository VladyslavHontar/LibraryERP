package library.service;

import library.domain.BookTicket;
import library.repository.BookRepository;
import library.repository.DynamicArray;
import library.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

  @Mock
  private BookRepository bookRepo;

  @Mock
  private TicketRepository ticketRepo;

  @InjectMocks
  private LibraryService service;

  @Test
  void name() {
    Mockito.when(bookRepo.findAll()).thenReturn(new DynamicArray());

    DynamicArray books = service.getBooks();

    assertThat(books).isEmpty();

    Mockito.verify(bookRepo).findAll();
    Mockito.verifyNoInteractions(ticketRepo);
  }

  @Test
  void waitFor() {
    new Thread(() -> {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      DynamicArray books = service.getBooks();
    }).start();

    Mockito.verify(bookRepo, Mockito.after(2000).times(1)).findAll();

    InOrder inOrder = Mockito.inOrder(bookRepo);
    inOrder.verify(bookRepo).findAll();
    inOrder.verify(bookRepo).findById(1);
  }
}

