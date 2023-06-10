package library.util;

import library.Consumer;
import library.Function;
import library.Predicate;
import library.domain.User;
import library.util.CustomOptional;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class CustomOptionalTest {
  @Test
  void createObject() {
    String str = "Saved string";

    CustomOptional<String> optional = new CustomOptional<>(str);
    assertThat(optional.get()).isEqualTo(str);
    assertThat(optional.isEmpty()).isFalse();
    assertThat(optional.isPresent()).isTrue();
    assertThat(optional).hasToString("CustomOptional[value=Saved string]");

//    if ((optional instanceof CustomOptional)) {
//      fail("optional is not instance of CustomOptional");
//    }
  }

  @Test
  void heapPollution() {
    CustomOptional<Integer> intOptional = new CustomOptional<>(12);
    CustomOptional raw = intOptional;
    CustomOptional<ArrayList> listOptional = raw;
//    CustomOptional<String> stringOptional = raw;

    System.out.println(listOptional.get());
  }

  @Test
  void ifPresent() {
    String[] holder = new String[1];
    assertThat(holder[0]).isNull();
    assertThat(holder.length).isEqualTo(1);

    CustomOptional<String> nonEmpty = new CustomOptional<>("Saved string");
    assertThat(nonEmpty.isEmpty()).isFalse();
    assertThat(nonEmpty.isPresent()).isTrue();
    Consumer<String> putToHolder = new Consumer<>() {
      @Override
      public void accept(String s) {
        holder[0] = s;
      }
    };
    nonEmpty.ifPresent(putToHolder);
    assertThat(holder[0]).isEqualTo("Saved string");

    CustomOptional<String> empty = new CustomOptional<>(null);
    assertThat(empty.isEmpty()).isTrue();
    assertThat(empty.isPresent()).isFalse();
  }

  @Test
  void filter() {
    User user = User.builder().username("JaZaebalsa").id(30).build();

    Predicate<User> hasPositiveId = new Predicate<>() {
      @Override
      public boolean test(User user) {
        return user.getId() > 0;
      }
    };

    CustomOptional<User> nonEmpty = new CustomOptional<>(user);
    assertThat(nonEmpty.isEmpty()).isFalse();
    assertThat(nonEmpty.isPresent()).isTrue();

    CustomOptional<User> filtered = nonEmpty.filter(hasPositiveId);
    assertThat(filtered.isEmpty()).isFalse();
    assertThat(filtered.isPresent()).isTrue();
    assertThat(filtered.get().getUsername()).isEqualTo("JaZaebalsa");

    findById().filter(user1 -> user1.getId() > 0)
            .filter(user1 -> user1.getUsername().equals("JaZaebalsa"))
            .ifPresent(user1 -> System.out.println(user1));
  }

  public CustomOptional<User> findById() {
    return new CustomOptional<>(User.builder().username("JaZaebalsa").id(30).build());
  }
  public CustomOptional<User> nonFoundById() {
    return new CustomOptional<>(null);
  }
  @Test
  void map() {
    findById()
            .filter(user -> user.getId() > 0)
            .map(new Function<User, String>() {
              @Override
              public String apply(User user) {
                return user.getUsername();
              }
            }).ifPresent(username -> System.out.println(username));
  }

  @Test
  void orElse() {
    User user = findById().orElseThrow(new NoSuchElementException());
    assertThat(user).isNotNull();

    assertThatCode(() -> nonFoundById().orElseThrow(new NoSuchElementException()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage(null);

  }
}