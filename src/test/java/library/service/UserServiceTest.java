package library.service;

import library.v2_0.domain.User;
import library.v2_0.service.UserService;
import library.v2_0.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

import static library.v2_0.domain.User.Type.ADMIN;
import static library.v2_0.domain.User.Type.MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepo;

  @InjectMocks
  private UserService userService;


  @SuppressWarnings("unchecked")
  @Test
  void addUserTest() {
    // Arrange
    Mockito.when(userRepo.save(Mockito.any())).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
        User user = invocationOnMock.getArgument(0);
        user.setId(52);
        return user;
      }
    });

    User admin = User.builder().username("admin#1").build();

    // Act
    User addedUser = userService.add(admin, "newUserName", "newUserPassword", MANAGER);

    // Assert
    assertThat(addedUser).extracting(User::getId).isNotEqualTo(0);
    assertThat(addedUser).extracting(User::getUsername).isEqualTo("newUserName");
    assertThat(addedUser).extracting(User::getPassword).isEqualTo("newUserPassword");
    assertThat(addedUser).extracting(User::getType).isEqualTo(MANAGER);

    Mockito.verify(userRepo).save(Mockito.any());
  }



//  @Test
//  void spyExample() {
//    BookRepository bookRepo = Mockito.spy(InMemoryBookRepository.class);
//    Mockito.when(bookRepo.findById(Mockito.longThat(new ArgumentMatcher<Long>() {
//      @Override
//      public boolean matches(Long id) {
//        return id == 42;
//      }
//    }))).thenReturn(Book.builder().year(2000).build());
//
//    assertThat(bookRepo.findAll()).isEmpty();
//    assertThat(bookRepo.findById(1L)).isNull();
//    assertThat(bookRepo.findById(42L).getYear()).isEqualTo(2000);
//  }
}
