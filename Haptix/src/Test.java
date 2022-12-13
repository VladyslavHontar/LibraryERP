public class Test {
    public static void main(String[] args) {
        Integer test1 = 127;
        Integer test2 = 127;
        Integer test3 = 220;
        Integer test4 = 220;

        System.out.println(test1 == test2);
        System.out.println(test3 == test4);

        String str1 = new String("hi");
        String str2 = new String("hi");

        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));

    }
}
