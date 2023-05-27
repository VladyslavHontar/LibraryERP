package library;

import library.domain.User;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;
import library.repository.DynamicArray;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
  private DynamicArray array = new DynamicArray();

  @MethodSource
  @ParameterizedTest
  void addUser(User user) {
    array.add(user);

    assertFalse(array.isEmpty());
    assertTrue(array.contains(user));
    assertEquals(array.getSize(), 1);
  }

  static Stream<User> addUser() {
    return Stream.of(
            new User(1L, "user1", "password1", User.Type.CLIENT),
            new User(2L, "user2", "password2", User.Type.CLIENT)
    );
  }
}
