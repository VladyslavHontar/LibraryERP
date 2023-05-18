package library.repository;

import library.domain.User;

public interface UserRepository {

  User save(User user);

  DynamicArray findAll();

  User findById(long id);

  /**
   *
   * @param username used for search
   * @return null if user is not found else domain object represents user
   */
  User findByUsername(String username);

  void delete(User user);
}
