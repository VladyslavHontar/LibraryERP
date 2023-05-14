import java.util.Arrays;
import java.util.Iterator;

public class DynamicArray implements Iterable{
  private Object[] values;
  private int size;

  public DynamicArray() {
    values = new Object[0];
    size = 0;
  }

  public int getSize() {
    return size;
  }
  public void add(Object value){
    resizeIfNeeded();
    values[size++] = value;
  }
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
  public void set(Object value, int position) {
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
      return;
    }
    values[position] = value;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public Object get(int position) {
    if (position < 0 || position >= size) {
      System.err.println("IllegalPositionArgument: " + position);
      return null;
    }
    return values[position];
  }
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
  public boolean contains(Object value) {
    for (Object val : values) {
      if (val.equals(value)) {
        return true;
      }
    }
    return false;
  }
  private void resizeIfNeeded() {
    if (values.length == size) {
      values = Arrays.copyOf(values, size * 2 + 1);
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(values);
  }
  @Override
  public Iterator iterator() {
    return new DynamicArrayIterator(this);
  }
  private static class DynamicArrayIterator implements Iterator {
    private final DynamicArray array;
    private int pointer;
    private DynamicArrayIterator(DynamicArray array) {
      this.array = array;
    }
    @Override
    public boolean hasNext() {
      return pointer != array.size;
    }
    @Override
    public Object next() {
      return array.values[pointer++];
    }
  }
}

