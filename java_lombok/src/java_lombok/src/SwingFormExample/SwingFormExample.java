package java_lombok.src.SwingFormExample;

import lombok.*;
import javax.swing.*;
import java.awt.*;

public class SwingFormExample extends JFrame {
    public SwingFormExample() {
        setTitle("Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        nameLabel.setLabelFor(nameField);

        JLabel rollNumberLabel = new JLabel("Roll Number:");
        JTextField rollNumberField = new JTextField();
        rollNumberLabel.setLabelFor(rollNumberField);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(rollNumberLabel);
        formPanel.add(rollNumberField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String rollNumber = rollNumberField.getText();

            FormData formData = new FormData(name, rollNumber);
            displayFormData(formData);
        });

        add(formPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void displayFormData(FormData formData) {
        JFrame displayFrame = new JFrame("Form Details");
        displayFrame.setSize(300, 200);
        displayFrame.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        JLabel nameLabel = new JLabel("Name: " + formData.getName());
        JLabel rollNumberLabel = new JLabel("Roll Number: " + formData.getRollNumber());

        detailsPanel.add(nameLabel);
        detailsPanel.add(rollNumberLabel);

        displayFrame.add(detailsPanel, BorderLayout.CENTER);
        displayFrame.setVisible(true);
    }

    @Data
    static class FormData {
        private final String name;
        private final String rollNumber;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingFormExample::new);
    }
}
