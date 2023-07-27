package library.v2_0.terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Terminal {
  private final Scanner in;
  private final PrintWriter out;
  public Terminal(InputStream in, PrintStream out) {
    this.in = new Scanner(in);
    this.out = new PrintWriter(out, true);
  }
  public void print(String str) {
    out.print(str);
    out.flush();
  }
  public void println() {
    out.println();
  }
  public void println(Object obj) {
    out.println(obj.toString());
  }
  public void println(String str) {
    out.println(str);
  }
  public String readLine() {
    return in.nextLine();
  }
}
