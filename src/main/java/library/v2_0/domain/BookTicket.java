package library.v2_0.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class BookTicket {

  private long id;
  private String bookTitle;
  private User user;
  private Book book;
  private long takenTimestamp;

}
