package library.v2_0.repository;

import library.v2_0.domain.User;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

  /**
   *
   * @param username used for search
   * @return null if user is not found else domain object represents user
   */
  Optional<User> findByUsername(String username);

}
