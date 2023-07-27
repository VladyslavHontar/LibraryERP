package library.v2_0.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookInUseCannotBeDeletedException extends RuntimeException {

  private final int numberOfBooksInUse;
}
