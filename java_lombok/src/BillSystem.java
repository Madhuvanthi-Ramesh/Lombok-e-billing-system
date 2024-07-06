import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillSystem extends JFrame {
    private final JTextField customerNameField;
    private final JTextField rollNumberField;
    private final JTextField totalItemsField;
    private final JPanel foodItemsPanel;
    private final JButton nextButton;
    private final JLabel dateLabel; // New label for displaying date and day

    @Getter
    @Setter
    @AllArgsConstructor
    static class FoodItem {
        private String name;
        private int quantity;
        private int price;
    }

    private final String[] foodItems = {"Veg Biriyani","Parotta","Chapathi","Noodles","Veg Sandwich","Paneer Sandwich","Tea","Coffee","Choco Ice cream", "Vannila Ice cone","Veg Puff","Poori","Masal Dosai","Idli","Ulundhu vadai","Meals","Choco Milkshake","Mango Milkshake","Cake"};
    private final int[] foodPrices = {50, 40, 40, 55, 30,40,10,15,20,25,10,30,50,15,10,70,35,35,20};

    public BillSystem() {
        setTitle("College Canteen Billing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(3, 1)); // Increased the rows to accommodate the date label
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(Color.lightGray); // Set background color for the first form

        JLabel titleLabel = new JLabel("PSG TECH CANTEEN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);

        dateLabel = new JLabel(); // Initialize the date label
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(dateLabel); // Add date label to header panel

        updateDateLabel(); // Call to update the date label initially

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.lightGray); // Set background color for the first form

        JLabel nameLabel = new JLabel("Name:");
        customerNameField = new JTextField();
        nameLabel.setLabelFor(customerNameField);

        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField();
        rollNumberLabel.setLabelFor(rollNumberField);

        JLabel totalItemsLabel = new JLabel("Number of Items :");
        totalItemsField = new JTextField();
        totalItemsLabel.setLabelFor(totalItemsField);

        formPanel.add(nameLabel);
        formPanel.add(customerNameField);
        formPanel.add(rollNumberLabel);
        formPanel.add(rollNumberField);
        formPanel.add(totalItemsLabel);
        formPanel.add(totalItemsField);

        foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new BoxLayout(foodItemsPanel, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> showFoodItemsFrame());
        buttonsPanel.add(nextButton);

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateDateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy"); // Format for day and date
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        dateLabel.setText(currentDate);
    }

    private void showFoodItemsPanel() {
        int totalItems = Integer.parseInt(totalItemsField.getText());
        foodItemsPanel.removeAll();

        for (int i = 0; i < totalItems; i++) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new GridLayout(2, 2)); // Increased rows to accommodate dropdown and quantity

            JLabel nameLabel = new JLabel("Food Item " + (i + 1) + ":");
            JComboBox<String> foodComboBox = new JComboBox<>(foodItems);
            foodComboBox.setPreferredSize(new Dimension(200, 30));

            JLabel quantityLabel = new JLabel("Quantity:");
            JTextField quantityField = new JTextField();
            quantityField.setPreferredSize(new Dimension(50, 30));

            itemPanel.add(nameLabel);
            itemPanel.add(foodComboBox);
            itemPanel.add(quantityLabel);
            itemPanel.add(quantityField);

            foodItemsPanel.add(itemPanel);
        }

        validate();
        repaint();
    }

    private void showFoodItemsFrame() {
        JFrame itemsFrame = new JFrame("Selected Food Items");
        itemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        itemsFrame.setSize(400, 300);
        itemsFrame.setLayout(new BorderLayout());
        itemsFrame.getContentPane().setBackground(new Color(255, 127, 80)); // Set background color for the second form (coral)

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(new Color(255, 127, 80)); // Set background color for the second form (coral)

        showFoodItemsPanel();

        itemsPanel.add(foodItemsPanel);
        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        itemsFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton generateButton = new JButton("Generate Bill");
        generateButton.addActionListener(e -> generateBill());
        buttonPanel.add(generateButton);

        itemsFrame.add(buttonPanel, BorderLayout.SOUTH);

        itemsFrame.pack();
        itemsFrame.setLocationRelativeTo(this);
        itemsFrame.setVisible(true);
    }

    private void generateBill() {
        int totalAmount = 0;
        StringBuilder billDetails = new StringBuilder();
        billDetails.append("<html><body>");

        billDetails.append("<h2>Bill Details</h2>");
        billDetails.append("<p><strong>PSG TECH CANTEEN</strong></p>");

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        billDetails.append("<p>").append(currentDate).append("</p>");

        billDetails.append("<table border='1' cellpadding='5' bgcolor='#FFFFFF'>"); // Set background color for the bill table (white)
        billDetails.append("<tr><th>Item Name</th><th>Quantity</th><th>Price</th><th>Amount</th></tr>");

        for (Component component : foodItemsPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                JComboBox<String> foodComboBox = (JComboBox<String>) itemPanel.getComponent(1);
                JTextField quantityField = (JTextField) itemPanel.getComponent(3);

                String foodName = (String) foodComboBox.getSelectedItem();
                int quantity = Integer.parseInt(quantityField.getText());

                int price = 0;
                for (int i = 0; i < foodItems.length; i++) {
                    if (foodItems[i].equals(foodName)) {
                        price = foodPrices[i];
                        break;
                    }
                }

                int amount = price * quantity;
                totalAmount += amount;

                billDetails.append("<tr><td>").append(foodName).append("</td><td>").append(quantity).append("</td><td>").append(price).append("</td><td>").append(amount).append("</td></tr>");
            }
        }

        billDetails.append("<tr><td colspan='3'><strong>Total Amount</strong></td><td>").append(totalAmount).append("</td></tr>");
        billDetails.append("</table></body></html>");

        int option = JOptionPane.showConfirmDialog(this, billDetails.toString(), "Bill Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Reset the first form fields
            customerNameField.setText("");
            rollNumberField.setText("");
            totalItemsField.setText("");
            foodItemsPanel.removeAll();
            validate();
            repaint();

            // Hide the bill window and the second form
            Container billWindow = SwingUtilities.getWindowAncestor((Component) foodItemsPanel.getParent());
            if (billWindow != null) {
                ((Window) billWindow).dispose();
            }
            JFrame secondForm = (JFrame) SwingUtilities.getWindowAncestor((Component) nextButton.getParent());
            if (secondForm != null) {
                secondForm.dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillSystem::new);
    }
}
