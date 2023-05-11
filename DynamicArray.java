import java.util.Arrays;

public class DynamicArray {

  /*
    add(value) -> void
    insert(value, i) -> void
    remove(i) -> void
    size() -> int
    contains(value) -> boolean
    set(value, i) -> value
    isEmpty() -> boolean
   */

  private Object[] values = {};
  private int size;

  public void add(Object value){
    resizeIfNeeded();
    values[size++] = value;
  }

  public void set(Object value, int position){
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
      return;
    }
    values[position] = value;

  }
  public void insert(Object value, int position) {
     if (position > size) {
         System.err.println("IllegalPositionArgument: " + position);
         return;
     } else if (position == size) {
       add(value);
     } else {
       resizeIfNeeded();
       System.arraycopy(values, position, values, position + 1, size - position);
       values[position] = value;
       size++;
     }

  }
  private void resizeIfNeeded() {
    if (values.length == size) {
      values = Arrays.copyOf(values, size * 2 + 1);
    }
  }
}
