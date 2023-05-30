package library;

import library.domain.Book;
import library.domain.User;
import library.repository.BookRepository;
import library.repository.DynamicArray;
import library.repository.UserRepository;
import library.repository.memory.InMemoryBookRepository;
import library.repository.memory.InMemoryUserRepository;
import library.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

import static library.domain.User.Type.ADMIN;
import static library.domain.User.Type.MANAGER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

  @SuppressWarnings("unchecked")
  @Test
  void addUserTest() {
    // Arrange
    UserRepository userRepo = Mockito.mock(UserRepository.class);
    Mockito.when(userRepo.save(Mockito.any())).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocationOnMock) throws Throwable {
        User user = invocationOnMock.getArgument(0);
        user.setId(52);
        return user;
      }
    });

    UserService userService = new UserService(userRepo);
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

  @Test
  void getUsers() {
    // Arrange
    DynamicArray users = new DynamicArray();
    users.add(User.builder().username("admin#1").type(ADMIN).build());
    users.add(User.builder().username("admin#2").type(ADMIN).build());
    users.add(User.builder().username("admin#3").type(ADMIN).build());

    UserRepository userRepo = Mockito.mock(UserRepository.class);
    Mockito.when(userRepo.findAll()).thenReturn(users);

    UserService userService = new UserService(userRepo);

    // Act
    DynamicArray result = userService.getUsers();

    // Assert
    assertThat(result).allSatisfy(new Consumer() {
      @Override
      public void accept(Object o) {
        User user = (User) o;
        assertThat(user.getType()).isEqualTo(User.Type.ADMIN);
        assertThat(user.getUsername()).startsWith("admin#");
      }
    });

    Mockito.verify(userRepo).findAll();
  }

  @Test
  void spyExample() {
    BookRepository bookRepo = Mockito.spy(InMemoryBookRepository.class);
    Mockito.when(bookRepo.findById(Mockito.longThat(new ArgumentMatcher<Long>() {
      @Override
      public boolean matches(Long id) {
        return id == 42;
      }
    }))).thenReturn(Book.builder().year(2000).build());

    assertThat(bookRepo.findAll()).isEmpty();
    assertThat(bookRepo.findById(1)).isNull();
    assertThat(bookRepo.findById(42).getYear() ).isEqualTo(2000);
  }
}