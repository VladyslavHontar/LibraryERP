import java.util.Arrays;

public class DynamicArrayTest {
    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.add("Hello");
        for (String s : Arrays.asList("World", "Java", "Programming", "Language", "is", "the", "best", "programming", "language")) {
            dynamicArray.add(s);
        }
        System.out.println(dynamicArray);

        System.out.println(dynamicArray.getSize());
        System.out.println(dynamicArray.isEmpty());

        dynamicArray.remove(3);
        dynamicArray.remove(8);
        System.out.println(dynamicArray);
    }
}

