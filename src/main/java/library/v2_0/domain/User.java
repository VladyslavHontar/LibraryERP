package library.v2_0.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User {

  long id;
  String username;
  String passwordHash;
  Type type;
  Ticket ticket;

  public enum Type {
    ADMIN,
    MANAGER,
    CLIENT
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username " + username +
            ", passwordHash=" + passwordHash  +
            ", type=" + type +
            '}';
  }
}
