import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Gui {

    private static JScrollPane scrollPane; // Declare scrollPane as a class-level variable
    private static final int MAX_WIDTH = 550; // Maximum width for the scrollPane
    private static final int MAX_HEIGHT = 300; // Maximum width for the scrollPane

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("String Matching");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        
        // Create a panel to hold components
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Aligns components to the left
        
        JButton displayIndices = new JButton("Display Indices");

        // Create labels
        JLabel textLabel = new JLabel("Enter text:");
        JLabel patternLabel = new JLabel("Search for:");

        // Create text fields for user input
        JTextField textField = new JTextField(); // 20 columns wide
        JTextField patternField = new JTextField(); // 20 columns wide

        // Set preferred size for text fields to ensure they have the same width
        Dimension textFieldSize = new Dimension(450, 30); // Width: 450 pixels, Height: 30 pixels
        textField.setPreferredSize(textFieldSize);
        patternField.setPreferredSize(textFieldSize);

        // Create a label to display the result with word wrap enabled
        JLabel resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(JLabel.LEFT); // Align text to the left

        // Create an instance of KMP class
        KMP kmp = new KMP();
        
        // Add action listener to the button
        displayIndices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the text field
                String text = textField.getText();
                String pattern = patternField.getText();

                List<Integer> result = kmp.KMPSearch(text, pattern);
                
                // Convert the list of indices to a string
                StringBuilder resultString = new StringBuilder();

                if (result.size() == 0) {
                    resultString.append("No occurrences found.");
                } else {
                    resultString.append("<HTML>Locations: ");
                    for (int i = 0; i < result.size(); ++i) {
                        resultString.append(result.get(i));
                        if (i < result.size() - 1) {
                            resultString.append(", ");
                        }
                    }
                    resultString.append("</HTML>");
                }

                // Update the resultLabel with the result string
                resultLabel.setText(resultString.toString());

                // Update scrollPane size based on resultLabel size
                updateScrollPaneSize(resultLabel);
            }
        });

        // Add resultLabel to a JScrollPane for automatic scrolling
        scrollPane = new JScrollPane(resultLabel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(MAX_WIDTH, 200)); // Initial preferred size for scrollPane
        panel.add(scrollPane);
        
        // Add components to the panel
        panel.add(textLabel);
        panel.add(textField);
        panel.add(patternLabel);
        panel.add(patternField);
        panel.add(displayIndices);
        
        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        
        // Make the frame visible
        frame.setVisible(true);
    }

    
    // Method to dynamically adjust scrollPane size based on resultLabel size
    private static void updateScrollPaneSize(JLabel resultLabel) {
        // Check if scrollPane is initialized
        if (scrollPane == null) {
            System.err.println("scrollPane is not initialized!");
            return;
        }

        Dimension labelSize = resultLabel.getPreferredSize();

        // Calculate new width considering the maximum width constraint
        int width = Math.min(labelSize.width, MAX_WIDTH);
        int height = Math.min(labelSize.height + 20, MAX_HEIGHT); // Keep height constraint as before
        // System.out.println(width + " " + height);

        // Set the new preferred size for the scrollPane
        scrollPane.setPreferredSize(new Dimension(width, height));

        // Revalidate and repaint the frame to apply the new size
        scrollPane.revalidate();
        scrollPane.repaint();
    }
}