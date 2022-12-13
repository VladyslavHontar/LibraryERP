import java.lang.reflect.Array;
import java.util.*;

public class Test_Arry {
    public static void main(String[] args)throws Exception {
        ArrayList<Integer> Arr = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Arr.add(i);
        }

        Arr.remove(3);

        System.out.println(Arr);
    }
}
