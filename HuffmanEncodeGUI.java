
import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HuffmanEncodeGUI
{

    private JFrame frame;
    private JTextArea originalTextArea;
    private JTextArea compressedTextArea;
    private JLabel originalBitsLabel;
    private JLabel compressedBitsLabel;
    private JLabel differenceLabel;

    public HuffmanEncodeGUI()
    {
        frame = new JFrame("Huffman Encoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        originalTextArea = new JTextArea(10, 40);
        compressedTextArea = new JTextArea(10, 40);
        originalBitsLabel = new JLabel("Original bits: ");
        compressedBitsLabel = new JLabel("Compressed bits: ");
        differenceLabel = new JLabel("Difference: ");
        JButton uploadButton = new JButton("Upload File");

        uploadButton.addActionListener(e ->
        {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String fileContent = readFile(filePath);
                    originalTextArea.setText(fileContent);

                    HuffmanEncode huffmanEncoder = new HuffmanEncode();
                    huffmanEncoder.huffmanEncode(fileContent);

                    String compressedString = huffmanEncoder.getCompressedString(fileContent);
                    compressedTextArea.setText(compressedString);

                    int originalBits = fileContent.length() * 8;
                    int compressedBits = compressedString.length();
                    int difference = originalBits - compressedBits;
                    originalBitsLabel.setText("Original bits: " + originalBits);
                    compressedBitsLabel.setText("Compressed bits: " + compressedBits);
                    differenceLabel.setText("Difference: " + difference);

                    
                    saveCompressedStringToFile(compressedString);
                } catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(uploadButton);
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(new JScrollPane(originalTextArea));
        centerPanel.add(new JScrollPane(compressedTextArea));
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        bottomPanel.add(originalBitsLabel);
        bottomPanel.add(compressedBitsLabel);
        bottomPanel.add(differenceLabel);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private String readFile(String filePath) throws IOException
    {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(filePath))
        {
            int c;
            while ((c = reader.read()) != -1)
            {
                content.append((char) c);
            }
        }
        return content.toString();
    }

    private void saveCompressedStringToFile(String compressedString)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Compressed File");
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile()))
            {
                writer.write(compressedString);
                JOptionPane.showMessageDialog(frame, "File saved successfully.");
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args)
    {
        new HuffmanEncodeGUI();
    }
}
