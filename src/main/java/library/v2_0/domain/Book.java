package library.v2_0.domain;

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
  private String author;
  private int year;
  private double price;
  private int count;

}
