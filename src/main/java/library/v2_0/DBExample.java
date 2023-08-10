package library.v2_0;

import library.v2_0.domain.User;
import library.v2_0.repository.jdbc.DataSource;
import library.v2_0.repository.jdbc.DataSourceFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBExample {
  public static void main(String[] args) throws IOException, SQLException {
    DataSourceFactory dataSourceFactory = new DataSourceFactory();
    DataSource dataSource = dataSourceFactory.create();

    User testUser = User.builder()
                    .username("test_user1")
                    .passwordHash("$2a$12$EEwzc2AT.4HzI7/ODCM1Hu3y4kCW9yt19pIWlq.vf1UcaoWPDIgJW")
                    .type(User.Type.CLIENT)
                    .build();
    User addedUser = addUser(dataSource, testUser);
    System.out.println("Added user: " + addedUser);

    List<User> users = getUsers(dataSource);

    users.forEach(System.out::println);
  }

  private static User addUser(DataSource dataSource ,User user) throws SQLException {
    try (Connection connection = dataSource.getConnection();
         Statement st = connection.createStatement()) {
      st.executeUpdate("USE mysql");
      PreparedStatement ps = connection.prepareStatement("INSERT INTO user (username, password_hash, type) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getPasswordHash());
      ps.setString(3, user.getType().toString());
      ps.executeUpdate();

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (!generatedKeys.next()) {
           throw new AssertionError();
        }
        user.setId(generatedKeys.getLong(1));
      }
    }
    return user;
  }

  private static List<User> getUsers(DataSource dataSource) throws SQLException {
    List<User> list= new ArrayList<>();

    try (Connection connection = dataSource.getConnection();
         Statement st = connection.createStatement()) {
      st.executeUpdate("USE mysql");
      ResultSet rs = st.executeQuery("SELECT id, username, password_hash, type FROM user");
      while (rs.next()) {
        list.add(
                User.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .passwordHash(rs.getString("password_hash"))
                    .type(User.Type.valueOf(rs.getString("type")))
                    .build()
                );
      }

      System.out.println(connection.isClosed());
    }
    return list;
  }
}
