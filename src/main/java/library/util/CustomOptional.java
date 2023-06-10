package library.util;

import library.Consumer;
import library.Function;
import library.Predicate;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomOptional<T> {
  private T value;

  public CustomOptional(T value) {
    this.value = value;
  }

  public void ifPresent(Consumer<T> action) {
    if (value != null) {
      action.accept(value);
    }
  }

  public CustomOptional<T> filter(Predicate<T> predicate) {
    if (value == null) {
      return this;
    }
    if (predicate.test(value)) {
      return this;
    }
    return new CustomOptional<>(null);
  }

  public <R> CustomOptional<R> map(Function<T, R> function) {
    if (value == null) {
      return new CustomOptional<>(null);
    }
    R result = function.apply(value);
    return new CustomOptional<>(result);
  }

  public T orElse(T other) {
    return value == null ? other : value;
  }

  public <E extends Throwable> T orElseThrow(E exception) throws E {
    if (value == null) {
      throw exception;
    }
    return value;
  }

  public boolean isPresent() {
    return value != null;
  }

  public boolean isEmpty() {
    return value == null;
  }

  public T get() {
    if (value == null) {
      throw new NoSuchElementException("Value is not present");
    }
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CustomOptional<?> that = (CustomOptional<?>) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value == null
            ? "Optional empty"
            : "CustomOptional[" + "value=" + value + ']';
  }
}
