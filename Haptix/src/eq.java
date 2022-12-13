import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
class Class {
    public static int x=0;
    public static int y=0;
    public static void main(String args[] ) throws Exception {
        //BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();                // Reading input from STDIN

        char ch[]=new char[0];
        ch=name.toCharArray();
        for(int i=0;i<ch.length;i++)
        {
            if(ch[i]=='z'){
                x = x + 1;
            }
            else if(ch[i]=='o'){
                y = y + 1;
            }
        }
        int end=2*x;
        if(end==y){
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }

    }
}
