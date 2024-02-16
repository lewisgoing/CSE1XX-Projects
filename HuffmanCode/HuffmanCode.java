// Lewis Going
// CSE 123
// P03: Huffman Code
// TAs: Logan Dinh && Ken Oh
// 6/2/2023

import java.util.*;
import java.io.*;

// This class represents a Huffman Code, an algorithm used for lossless data compression.
// It is used to compress and decompress files, and can be used to create a Huffman Code
// from a given array of character frequencies or from a given .code file.
public class HuffmanCode {

    private HuffmanNode root;
    private Queue<HuffmanNode> pq;

    // Constructs a Huffman Code tree from the given array of int frequencies.
    // The frequency array represents the frequency of occurrence of each character
    // in the character set. Characters with higher frequencies will be represented
    // with fewer bits in the Huffman code.
    // Parameters:
    //      - frequencies: an array of int frequencies
    public HuffmanCode(int[] frequencies) {
        pq = new PriorityQueue<>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                pq.add(new HuffmanNode(frequencies[i], (char) i));
            }
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.remove();
            HuffmanNode right = pq.remove();
            pq.add(new HuffmanNode(left.frequency + right.frequency, left, right));
        }

        root = pq.remove();
    }

    // Constructs a Huffman Code tree from the given input scanner.
    // The input scanner reads from a .code file that contains a character's ASCII value
    // and its corresponding Huffman Code. The file structure is represented as one
    // character's ASCII value per line followed by its Huffman code on the next line.
    // Parameters:
    //      - input: a Scanner that reads from a .code file
    public HuffmanCode(Scanner input) {
        while(input.hasNextLine()) {
            int asciiVal = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            root = addToTree(root, code, asciiVal); // x = change(x);
        }
    }

    // Helper method that adds a node, its character and its Huffman Code to the Huffman tree.
    // The method takes in a HuffmanNode, a String representing the Huffman Code, and an int
    // parameter representing the character's ASCII value.
    // If the HuffmanNode is null, the method creates a new HuffmanNode with the frequency -1.
    // Parameters:
    //      - node: the HuffmanNode to be added to the tree
    //      - code: the Huffman Code of the node
    //      - asciiVal: the ASCII value of the node's character
    private HuffmanNode addToTree(HuffmanNode node, String code, int asciiVal) {
        if (code.length() == 0) {
            return new HuffmanNode(0, (char) asciiVal);
        } else {
            if (node == null) {
                node = new HuffmanNode(-1);
            }

            if (code.charAt(0) == '0') {
                node.left = addToTree(node.left, code.substring(1), asciiVal);
            } else {
                node.right = addToTree(node.right, code.substring(1), asciiVal);
            }

            return node;
        }
    }

    // Reads from the given BitInputStream and translates its Huffman-coded binary
    // to the corresponding characters. The translated characters are written to the
    // provided PrintStream output in a .txt file.
    // Parameters:
    //      - input: a BitInputStream that reads the Huffman-coded binary
    //      - output: a PrintStream that writes to a .txt file
    public void translate(BitInputStream input, PrintStream output) {
        translate(input, output, root);
    }

    // Helper method that recursively translates the bit sequence to characters.
    // The translated characters are written to the provided PrintStream output.
    // Parameters:
    //      - input: the BitInputStream to read from
    //      - output: the PrintStream to write the translated characters to
    //      - node: the current HuffmanNode in the tree
    private void translate(BitInputStream input, PrintStream output, HuffmanNode node) {
        while (input.hasNextBit()) {

            if (input.nextBit() == 0) {
                node = node.left;
            } else {
                node = node.right;
            }

            if (node.isLeaf()) {
                output.write(node.character);
                node = root;
            }
        }
    }

    // Stores the current Huffman Code to the given PrintStream in the standard .code file format
    // The output file is structured as one character's ASCII value per line
    // followed by its Huffman code on the next line.
    // Parameters:
    //      - output: a PrintStream to write to
    public void save(PrintStream output) {
        save(output, root, "");
    }

    // Helper method that recursively saves the current Huffman code to the output.
    // Parameters:
    //      - output: the PrintStream to write to
    //      - node: the current HuffmanNode in the tree
    //      - code: the Huffman code of the current node
    private void save(PrintStream output, HuffmanNode node, String code) {
        if (node != null) {
            if (node.isLeaf()) {
                output.println((int) node.character);
                output.println(code);
            } else {
                save(output, node.left, code + "0");
                save(output, node.right, code + "1");
            }
        }
    }

    // This class represents a node in a Huffman tree.
    // It stores the frequency of the character and can store the character itself.
    // Each node either represents a character (leaf node)
    // or a combination of two nodes (non-leaf node).
    public static class HuffmanNode implements Comparable<HuffmanNode> {
        public int frequency;
        public char character;
        public HuffmanNode left;
        public HuffmanNode right;

        // Constructs a Huffman node with the given frequency.
        public HuffmanNode(int frequency) {
            this.frequency = frequency;
        }

        // Constructs a Huffman node with the given frequency and character.
        public HuffmanNode(int frequency, char character) {
            this.frequency = frequency;
            this.character = character;
        }

        // Constructs a Huffman node with the given frequency and left and right children.
        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        // Returns true if this node is a leaf node (has no children) and false if not
        public boolean isLeaf() {
            return left == null && right == null;
        }

        // Compares two Huffman nodes based on their frequencies.
        // The node with the smaller frequency is considered "less" than the other.
        // Returns a negative number if this node is "less" than the other node,
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }
}
