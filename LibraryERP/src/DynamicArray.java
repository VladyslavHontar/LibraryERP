
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

  private Object[] values;
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
  private void resizeIfNeeded() {

  }
}
