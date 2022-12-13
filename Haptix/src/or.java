public class or {
    public static void main(String[] args) {
        boolean a = true;
        boolean b = false;
        boolean c = false;
        boolean d = true;
        boolean e = true;
        boolean f = false;

        System.out.println("12" + (a && b || d && (c && f) || !e));
    }
}
