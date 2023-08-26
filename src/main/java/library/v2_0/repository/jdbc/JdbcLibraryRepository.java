package library.v2_0.repository.jdbc;

import library.v2_0.domain.Book;
import library.v2_0.repository.BookRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class JdbcLibraryRepository extends AbstractJdbcRepository implements BookRepository {
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
    public JdbcLibraryRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Book> findAll() {
        try (Connection con = dataSource.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("USE mysql");
            ResultSet rs = st.executeQuery("SELECT id, isbn, title, author, year, price, count FROM book");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        try (Connection con = dataSource.getConnection()) {
            Statement st = con.createStatement();
            st.executeUpdate("USE mysql");
            ResultSet rs = st.executeQuery("SELECT ")
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.empty();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> findBy(Predicate<Book> predicate) {
        return null;
    }

    @Override
    public Book save(Book entity) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Book entity) {

    }
}
