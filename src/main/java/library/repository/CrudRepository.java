package library.repository;

import library.util.CustomOptional;
import library.util.DynamicArray;

public interface CrudRepository<E, K> {

  E save(E entity);

  DynamicArray findAll();

  CustomOptional<E> findById(K id);

  void delete(E entity);
}
