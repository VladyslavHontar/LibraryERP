package library.v2_0.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {

  T save(T entity);

  Optional<T> findById(K id);

  List<T> findAll();

  void delete(T entity);
}
