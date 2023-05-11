package test;
public class DynamicArrayTest {
    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.add("Hello");
        dynamicArray.add("World");
        dynamicArray.add("Java");
        dynamicArray.add("Programming");
        dynamicArray.add("Language");
        dynamicArray.add("is");
        dynamicArray.add("the");
        dynamicArray.add("best");
        dynamicArray.add("programming");
        dynamicArray.add("language");
        System.out.println(dynamicArray);

        System.out.println(dynamicArray.getSize());
        System.out.println(dynamicArray.isEmpty());

        dynamicArray.remove(3);
        dynamicArray.remove(8);
        System.out.println(dynamicArray);
    }
}
