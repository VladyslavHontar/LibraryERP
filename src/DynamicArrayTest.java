import java.util.Arrays;

public class DynamicArrayTest {
    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.add("A");
        dynamicArray.add("B");
        dynamicArray.add("C");

        System.out.println(dynamicArray);
        System.out.println(dynamicArray.getSize());
        System.out.println(dynamicArray.isEmpty());

        dynamicArray.remove(2);
        dynamicArray.remove(0);
        System.out.println(dynamicArray);
    }
}

