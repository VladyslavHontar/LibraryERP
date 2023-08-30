package library.v2_0.repository.jdbc;

import library.v2_0.domain.Book;
import library.v2_0.repository.BookRepository;
import library.v2_0.util.LambdaUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JdbcBookRepository extends AbstractJdbcRepository implements BookRepository {
  private static Function<ResultSet, Book> bookMapper = rs -> {
    try {
      return Book.builder()
              .id(rs.getLong("id"))
              .isbn(rs.getString("isbn"))
              .title(rs.getString("title"))
              .author(rs.getString("author"))
              .year(rs.getInt("year"))
              .price(rs.getDouble("price"))
              .count(rs.getInt("count"))
              .build();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  };

  public JdbcBookRepository(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public List<Book> findAll() {
    try (Connection con = dataSource.getConnection()) {
      Statement st = con.createStatement();
      st.executeUpdate("USE mysql");
      ResultSet rs = st.executeQuery("SELECT id, isbn, title, author, year, price, count FROM book");

      return Stream.generate(() -> rs).
              takeWhile(LambdaUtils.wrapChecked(ResultSet::next))
              .map(bookMapper)
              .collect(Collectors.toList());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Book> findByTitle(String title) {
    try (Connection con = dataSource.getConnection()) {
      Statement st = con.createStatement();
      st.executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("SELECT isbn, title, author, year, price FROM book WHERE title=?");
      ps.setString(1, title);
      try (ResultSet rs = ps.executeQuery()) {
        return Stream.generate(() -> rs)
                .takeWhile(LambdaUtils.wrapChecked(ResultSet::next))
                .map(bookMapper)
                .findFirst();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Book> findByIsbn(String isbn) {
    try (Connection con = dataSource.getConnection()) {
      Statement st = con.createStatement();
      st.executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("SELECT isbn, title, author, year, price FROM book WHERE isbn=?");
      ps.setString(1, isbn);
      try (ResultSet rs = ps.executeQuery()) {
        return Stream.generate(() -> rs)
                .takeWhile(LambdaUtils.wrapChecked(ResultSet::next))
                .map(bookMapper)
                .findFirst();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Book> findByAuthor(String author) {
    try (Connection con = dataSource.getConnection()) {
      Statement st = con.createStatement();
      st.executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("SELECT isbn, title, author, year, price FROM book WHERE author=?");
      ps.setString(1, author);
      try (ResultSet rs = ps.executeQuery()) {
        return Stream.generate(() -> rs)
                .takeWhile(LambdaUtils.wrapChecked(ResultSet::next))
                .map(bookMapper)
                .findFirst();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Book save(Book entity) {
    try (Connection con = dataSource.getConnection()) {
      con.createStatement().executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("INSERT INTO book (isbn, title, author, year, price, count) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, entity.getIsbn());
      ps.setString(2, entity.getTitle());
      ps.setString(3, entity.getAuthor());
      ps.setInt(4, entity.getYear());
      ps.setDouble(5, entity.getPrice());
      ps.setInt(6, entity.getCount());
      if (ps.executeUpdate() != 1) {
        throw new RuntimeException("Failed to save entity User [" + entity + "]");
      }
      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (!generatedKeys.next()) {
          throw new RuntimeException("Primary key wasn't generated for saved entity User [" + entity + "]");
        }
        entity.setId(generatedKeys.getLong(1));
      }
      return entity;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Book> findById(Long id) {
    try (Connection con = dataSource.getConnection()) {
      con.createStatement().executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("SELECT isbn, title, author, year, price FROM book WHERE id = ?");
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        return Stream.generate(() -> rs)
                .takeWhile(LambdaUtils.wrapChecked(ResultSet::next))
                .map(bookMapper)
                .findFirst();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Book entity) {
    try (Connection con = dataSource.getConnection()) {
      con.createStatement().executeUpdate("USE mysql");
      PreparedStatement ps = con.prepareStatement("DELETE FROM book WHERE id = ?");
      ps.setLong(1, entity.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
