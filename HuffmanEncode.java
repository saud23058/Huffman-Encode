
public class HuffmanEncode
{
    class Node
    {

        int frequency;
        char character;
        Node left;
        Node right;
        String code = "";

        Node(int frequency, char character)
        {
            this.frequency = frequency;
            this.character = character;
        }

        Node(int frequency, Node left, Node right)
        {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    private int[] frequencies = new int[256];
    private Node root;
    private String[] codes = new String[256];

    public void calculateFrequencies(String str)
    {
        for (char c : str.toCharArray())
        {
            frequencies[c]++;
        }
    }

    public void printFrequencies()
    {
        for (int i = 0; i < frequencies.length; i++)
        {
            if (frequencies[i] > 0)
            {
                char c = (char) i;
                System.out.println("'" + c + "': " + frequencies[i]);
            }
        }
    }

    public static void bubbleSortNodes(Node[] nodes, int count)
    {
        for (int i = 0; i < count; i++)
        {
            boolean flag = true;
            for (int j = 1; j < count - i; j++)
            {
                if (nodes[j].frequency < nodes[j - 1].frequency)
                {
                    Node temp = nodes[j];
                    nodes[j] = nodes[j - 1];
                    nodes[j - 1] = temp;
                    flag = false;
                }
            }
            if (flag)
            {
                break;
            }
        }
    }

    void buildTree()
    {
        Node[] nodes = new Node[256];
        int count = 0;

        for (char i = 0; i < frequencies.length; i++)
        {
            if (frequencies[i] > 0)
            {
                nodes[count++] = new Node(frequencies[i], i);
            }
        }

        while (count > 1)
        {

            bubbleSortNodes(nodes, count);

            Node left = nodes[0];
            Node right = nodes[1];
            Node newNode = new Node(left.frequency + right.frequency, left, right);

            for (int i = 2; i < count; i++)
            {
                nodes[i - 2] = nodes[i];
            }
            nodes[count - 2] = newNode;
            count--;
        }

        root = nodes[0];
    }

    void generateCodes(Node node, String code)
    {
        if (node == null)
        {
            return;
        }

        if (node.character != 0)
        {
            node.code = code;
            codes[node.character] = code;
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public String getHuffmanCode(char character)
    {
        return codes[character];
    }

    public String getCompressedString(String str)
    {
        StringBuilder compressed = new StringBuilder();
        for (char c : str.toCharArray())
        {
            compressed.append(codes[c]);
        }
        return compressed.toString();
    }

    public void huffmanEncode(String str)
    {
        calculateFrequencies(str);
        buildTree();
        generateCodes(root, "");

        // Display results for each character
        System.out.println("Original string: " + str);
        System.out.println("Character\t| Frequency\t| Original Bits\t| Huffman Code\t| Compressed Bits");
        System.out.println("---------------------------------------------------------------------------");
        for (int i = 0; i < 256; i++)
        {
            if (frequencies[i] > 0)
            {
                char c = (char) i;
                if (c != 0)
                {
                    String huffmanCode = getHuffmanCode(c);
                    int originalBits = frequencies[i] * 8;
                    int compressedBits = huffmanCode.length() * frequencies[i];
                    System.out.println("'" + c + "'\t\t| " + frequencies[i] + "\t\t| " + originalBits + "\t\t| " + huffmanCode + "\t\t| " + compressedBits);
                }
            }
        }
        System.out.println("----------------------------------------------------------------------------");

        int totalOriginalBits = str.length() * 8;
        byte[] originalBytes = str.getBytes();
        System.out.print("Original bits: ");
        for (byte b : originalBytes)
        {
            String bits = String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
            System.out.print(bits + " ");
        }
        System.out.println();
        String compressedString = getCompressedString(str);
        int totalCompressedBits = compressedString.length();
        System.out.println("Original String :" + str);
        System.out.println("Total original bits: " + totalOriginalBits);
        System.out.println("Compressed String :" + compressedString);
        System.out.println("Total compressed bits: " + totalCompressedBits);
        System.out.println("Difference in bits: " + (totalOriginalBits - totalCompressedBits));
    }

    public static void main(String[] args)
    {
        HuffmanEncode huffmanEncode = new HuffmanEncode();
        huffmanEncode.huffmanEncode("ABRACADABRA");
    }
}
