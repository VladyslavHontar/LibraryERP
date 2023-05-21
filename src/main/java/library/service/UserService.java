package library.service;

import library.domain.User;
import library.repository.DynamicArray;
import library.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public User authenticate(String username, String password) {
    User user = repository.findByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }

  public DynamicArray getUsers() {
    return repository.findAll();
  }

  public User add(User admin ,String username, String password, User.Type type) {
    User user = repository.findByUsername(username);
    if (user != null) {
      System.err.println("Admin " + admin + " tried to add user with existed name");
      return null;
    }
    User newUser = repository.save(User.builder()
                                       .username(username)
                                       .password(password)
                                       .type(type)
                                       .build());
    System.err.println("Admin " + admin + " added a new user " + newUser);
    return newUser;
  }

  public User delete(User admin, long userId) {
    if (admin.getId() == userId) {
      System.err.println("Admin tried to delete himself: " + userId);
      return null;
    }
    User userToBeDeleted = repository.findById(userId);
    if (userToBeDeleted == null) {
      System.err.println("Admin " + admin + " tried to delete not existing user: " + userId);
      return null;
    }
    repository.delete(userToBeDeleted);
    System.err.println("Admin " + admin + " deleted user: " + userToBeDeleted);
    return userToBeDeleted;
  }
}
