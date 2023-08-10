package library.v2_0.repository.jdbc;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class DataSource {
  private final String url;
  private final String user;
  private final String password;

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }
}
