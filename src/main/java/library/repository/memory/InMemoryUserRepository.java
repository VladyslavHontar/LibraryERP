package library.repository.memory;

import library.domain.User;
import library.util.CustomOptional;
import library.util.DynamicArray;
import library.repository.UserRepository;

public class InMemoryUserRepository implements UserRepository {

  private final DynamicArray storage = new DynamicArray();
  private long currentId = 1;

  @Override
  public User save(User user) {
    long id = user.getId();
    if (id > 0) {
      User updatedUser = findById(id).get();
      updatedUser.setUsername(user.getUsername());
      updatedUser.setPassword(user.getPassword());
      updatedUser.setType(user.getType());
      return updatedUser;
    } else {
      user.setId(currentId);
      currentId++;
      storage.add(user);
      return user;
    }
  }

  @Override
  public DynamicArray findAll() {
    return storage;   //fix it later with clone
  }

  @Override
  public CustomOptional<User> findById(Long id) {
    for (Object userObj : storage) {
      User user = (User) userObj;
      if (user.getId() == id) {
        return new CustomOptional<>(user);
      }
    }
    return null;
  }

  @Override
  public User findByUsername(String username) {
    for (Object userObj : storage) {
      User user = (User) userObj;
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public void delete(User user) {
    storage.remove(user);
  }
}
