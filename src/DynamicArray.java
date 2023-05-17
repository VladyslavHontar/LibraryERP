import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Iterator;

@NoArgsConstructor
@ToString
@SuppressWarnings(value = {"unused", "rawtypes"})
public class DynamicArray implements Iterable{
  private Object[] values = new Object[0];
  private @Getter int size = 0;

  /**
   * Add an element to the end of the values array
   * @param value the element to add into the values array
   * @see #add(Object, int)              
   */
  public void add(Object value){
    resizeIfNeeded();
    values[size++] = value;
  }
    /**
     * Add an element to the values array at the specified position
     * @param value the element to add into the values array
     * @param position the position to add the element into the values array. If there is already the element at the position, the element will be shifted to the right
     * @see #resizeIfNeeded() 
     */
  public void add(Object value, int position) {
    if (position > size) {
      System.err.println("IllegalPositionArgument: " + position);
    } else if (position == size) {
      add(value);
    } else {
      resizeIfNeeded();
      System.arraycopy(values, position, values, position + 1, size - position);
      values[position] = value;
      size++;
    }
  }
  /**
   * Set the element at the specified position
   * @param value the element to set into the values array
   * @param position the position to set the element into the values array. If there is already the element at the position, the element will be replaced
   */
  public void set(Object value, int position) {
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
      return;
    }
    values[position] = value;
  }
  /**
   * Check if the values array is empty
   * @return true if the values array is empty, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }
  /**
   * Get the element at the specified position
   * @param position the position to get the element from the values array
   * @return the element at the specified position
   */
  public Object get(int position) {
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
      return null;
    }
    return values[position];
  }
  /**
   * Remove the element at the specified position
   * @param position the position to remove the element from the values array
   */
  public void remove(int position) {
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
    } else if (position == size - 1) {
      values[position] = null;
      size--;
    } else {
      System.arraycopy(values, position + 1, values, position, size - position - 1);
      values[size - 1] = null;
      size--;
    }
  }
    /**
     * Check if the values array contains the specified element
     * @param value the element to check if it is in the values array
     * @return true if the values array contains the specified element, false otherwise
     */
  public boolean contains(Object value) {
    for (Object val : values) {
      if (val.equals(value)) {
        return true;
      }
    }
    return false;
  }
    /**
     * Resize the values array if the length of the values array is equal to the size of the values array
     */
  private void resizeIfNeeded() {
    if (values.length == size) {
      values = Arrays.copyOf(values, size * 2 + 1);
    }
  }

  @Override
  public Iterator iterator() {
    return new DynamicArrayIterator();
  }
  private class DynamicArrayIterator implements Iterator {
    private int pointer;
    @Override
    public boolean hasNext() {
      return pointer != size;
    }
    @Override
    public Object next() {
      return values[pointer++];
    }
  }
}

