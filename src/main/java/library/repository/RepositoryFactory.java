package library.repository;

import library.repository.memory.InMemoryBookRepository;
import library.repository.memory.InMemoryBookTicketRepository;
import library.repository.memory.InMemoryUserRepository;

public class RepositoryFactory {
  public <T, K> CrudRepository<T, K> createRepository(RepositoryType type) {
    switch (type) {
      case BOOK:
        return (CrudRepository<T, K>) new InMemoryBookRepository();
      case TICKET:
        return (CrudRepository<T, K>) new InMemoryBookTicketRepository();
        case USER:
        return (CrudRepository<T, K>) new InMemoryUserRepository();
      default:
        throw new IllegalArgumentException("Unknown repository type: " + type);
    }
  }
}
 enum RepositoryType {
   BOOK, TICKET, USER
}