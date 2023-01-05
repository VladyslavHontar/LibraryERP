import java.util.*;

public class Huffman {

  // Inner class to represent a node in the Huffman tree
  static class Node implements Comparable<Node> {

    char ch;
    int freq;
    Node left;
    Node right;

    Node(char ch, int freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
    }

    // Compare nodes based on their frequency
    public int compareTo(Node other) {
      return this.freq - other.freq;
    }
  }

  // Method to build the Huffman tree
  static Node buildTree(int[] charFreqs) {
    PriorityQueue<Node> queue = new PriorityQueue<>();
    // Create leaf nodes for each character and add them to the priority queue
    for (int i = 0; i < charFreqs.length; i++) {
      if (charFreqs[i] > 0) {
        queue.add(new Node((char) i, charFreqs[i], null, null));
      }
    }
    // Continuously merge the lowest frequency nodes until there is only one node left (the root of the tree)
    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();
      Node parent = new Node('\0', left.freq + right.freq, left, right);
      queue.add(parent);
    }
    return queue.poll();
  }

  // Method to generate the Huffman codes
  static Map<Character, String> getCodes(Node root) {
    Map<Character, String> codes = new HashMap<>();
    generateCodes(root, "", codes);
    return codes;
  }

  // Recursive method to generate the codes
  static void generateCodes(Node node, String code, Map<Character, String> codes) {
    if (node.left == null && node.right == null) {
      // Leaf node, add the code to the map
      codes.put(node.ch, code);
      return;
    }
    // Recurse on the left and right child
    generateCodes(node.left, code + '0', codes);
    generateCodes(node.right, code + '1', codes);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the text to compress: ");
    String text = scanner.nextLine();
    // Calculate the frequency of each character in the text
    int[][] charFreqs = new int[256][256];
    String[] chunks = text.split("(?<=\\G.{" + 10 + "})");
    int i = 0;
    for (char ch : chunks[i].toCharArray()) {
      charFreqs[ch][i]++;
      i++;
    }

    // Build the Huffman tree
    for (int n = 0; n < charFreqs.length; n++) {
      Node root = buildTree(charFreqs[n]);

      // Generate the Huffman codes
      Map<Character, String> codes = getCodes(root);

      // Encode the text using the Huffman codes
      StringBuilder encoded = new StringBuilder();
      int j = 0;
      for (char ch : chunks[j].toCharArray()) {
        encoded.append(codes.get(ch));
        j++;
      }

// Calculate the number of bits without compression
      int originalBits = text.length() * 8;

// Calculate the number of bits with compression
      int compressedBits = encoded.length();

      System.out.println();
      System.out.println("Number of bits without compression: " + originalBits);
      System.out.println("Number of bits with compression: " + compressedBits);
      System.out.println();
      System.out.println(encoded);
    }
  }
}

