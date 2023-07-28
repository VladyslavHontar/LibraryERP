package library.service;

import library.v2_0.repository.BookRepository;
import library.v2_0.service.LibraryService;
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

  @InjectMocks
  private LibraryService service;

}

