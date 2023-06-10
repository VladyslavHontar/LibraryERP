package library.service;

import library.domain.User;
import library.util.CustomOptional;
import library.util.DynamicArray;
import library.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
      return user;
    }
    return null;
  }

  public DynamicArray getUsers() {
    return userRepository.findAll();
  }

  public User add(User admin ,String username, String password, User.Type type) {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      System.err.println("Admin " + admin + " tried to add user with existed name");
      return null;
    }
    User newUser = userRepository.save(User.builder()
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
    CustomOptional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new IllegalArgumentException("User with id " + userId + " not found");
    }
    User userToBeDeleted = optionalUser.get();
    userRepository.delete(userToBeDeleted);
    System.err.println("Admin " + admin + " deleted user: " + userToBeDeleted);
    return userToBeDeleted;
  }
}
