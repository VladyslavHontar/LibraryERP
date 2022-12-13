import java.io.BufferedReader;
import java.io.InputStreamReader;

class testClass {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        int a=0;
        String str= " ";
        int len=name.length();
        for(int i=0; i<len; i++)
        {
            char c=name.charAt(i);

            if(c >= 65 && c <= 90)
                str = str + (char)(c+32);
            else
                str = str + (char)(c-32);
        }
        System.out.println(str);
    }
}
