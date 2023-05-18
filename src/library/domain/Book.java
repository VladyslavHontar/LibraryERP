package library.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Book {

  private long id;
  private String isbn;
  private String title;
  private int year;
  private double price;
  private int count;

}
