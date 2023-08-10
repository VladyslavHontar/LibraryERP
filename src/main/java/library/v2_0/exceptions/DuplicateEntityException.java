package library.v2_0.exceptions;

import java.sql.SQLException;

public class DuplicateEntityException extends RuntimeException {
  public DuplicateEntityException(SQLException ex) {
    super(ex);
  }
}
