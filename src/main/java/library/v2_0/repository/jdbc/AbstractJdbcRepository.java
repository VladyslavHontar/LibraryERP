package library.v2_0.repository.jdbc;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractJdbcRepository {
  protected final DataSource dataSource;
}
