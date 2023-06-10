package library.repository;

import library.domain.User;
import library.util.DynamicArray;

public interface UserRepository extends CrudRepository<User, Long> {

  /**
   *
   * @param username used for search
   * @return null if user is not found else domain object represents user
   */
  User findByUsername(String username);

}
