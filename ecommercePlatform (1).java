
package ecommercePlatform;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
public class ecommercePlatform {
 public static void main(String[] args) {
 ArrayList<Product> sharedProducts = new ArrayList<>();
 new LoginFrame(sharedProducts).setVisible(true);
 }
 static class Product {
 private String name;
 private String price;
 private String imagePath;
 public Product(String name, String price, String imagePath) { this.name = name;
 this.price = price;
 this.imagePath = imagePath;
 }
 public String getName() {
 return name;
 }
 public String getPrice() {
 return price;
 }
 public String getImagePath() {
 return imagePath;
 }
}
 static class LoginFrame extends JFrame {
 private JTextField usernameField;
 private JPasswordField passwordField;
 private JComboBox<String> roleComboBox;
 public LoginFrame(ArrayList<Product> products) {
 setTitle("Login");
 setSize(400, 250);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 JPanel panel = new JPanel(new GridLayout(5, 2));
 panel.add(new JLabel("Username:"));
 usernameField = new JTextField();
 panel.add(usernameField);
 panel.add(new JLabel("Password:"));
 passwordField = new JPasswordField();
 panel.add(passwordField);
 panel.add(new JLabel("Login as:"));
 roleComboBox = new JComboBox<>(new String[]{"Admin", "Customer"});
 panel.add(roleComboBox);
 JButton loginButton = new JButton("Login");
 JButton exitButton = new JButton("Exit");
 panel.add(loginButton);
 panel.add(exitButton);
 add(panel);
 loginButton.addActionListener(e -> {
 String username = usernameField.getText();
String password = new String(passwordField.getPassword());
 String role = (String) roleComboBox.getSelectedItem();
 if (role.equals("Admin")) {
 if (username.equals("admin") && password.equals("admin123")) {
 new AdminPanel(products).setVisible(true);
 dispose();
 } else {
 JOptionPane.showMessageDialog(this, "Invalid admin credentials."); }
 } else if (role.equals("Customer")) {
 if (username.equals("customer") && password.equals("password")) {
 new ProductCatalog("Customer", products).setVisible(true);
 dispose();
 } else {
 JOptionPane.showMessageDialog(this, "Invalid customer credentials.");
 }
 }
 });
 exitButton.addActionListener(e -> System.exit(0));
 }
 }
 static class AdminPanel extends JFrame {
 private DefaultListModel<String> productListModel;
 private JList<String> productList;
 public AdminPanel(ArrayList<Product> products) {
 setTitle("Admin Panel");
 setSize(700, 500);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 JPanel panel = new JPanel(new BorderLayout());
 productListModel = new DefaultListModel<>();
 for (Product product : products) {
 productListModel.addElement(product.getName() + " - " + 
product.getPrice());
 }
 productList = new JList<>(productListModel);
 JScrollPane scrollPane = new JScrollPane(productList);
 JPanel buttonPanel = new JPanel(new FlowLayout());
 JButton addButton = new JButton("Add Product");
 JButton removeButton = new JButton("Remove Product");
 JButton logoutButton = new JButton("Logout");
 buttonPanel.add(addButton);
 buttonPanel.add(removeButton);
 buttonPanel.add(logoutButton);
 panel.add(scrollPane, BorderLayout.CENTER);
 panel.add(buttonPanel, BorderLayout.SOUTH);
 add(panel);
 addButton.addActionListener(e -> new AddProductDialog(this, products, productListModel).setVisible(true));
 removeButton.addActionListener(e -> {
 int selectedIndex = productList.getSelectedIndex();
 if (selectedIndex != -1) {
 products.remove(selectedIndex);
 productListModel.remove(selectedIndex);
 JOptionPane.showMessageDialog(this, "Product removed successfully!");
 } else {
 JOptionPane.showMessageDialog(this, "Please select a product to remove.");
 }
 });
 logoutButton.addActionListener(e -> {
 new LoginFrame(products).setVisible(true);
 dispose();
 });
 }
 }
 static class AddProductDialog extends JDialog {
 private JTextField nameField;
 private JTextField priceField;
 private JLabel imageLabel;
 private File selectedImage;
 public AddProductDialog(Frame parent, ArrayList<Product> products, 
DefaultListModel<String> productListModel) {
 super(parent, "Add Product", true);
 setSize(400, 300);
 setLocationRelativeTo(parent);
 nameField = new JTextField();
 priceField = new JTextField();
 imageLabel = new JLabel("No Image Selected");
 JButton chooseImageButton = new JButton("Choose Image");
 JButton addButton = new JButton("Add Product");
 JButton cancelButton = new JButton("Cancel");
chooseImageButton.addActionListener(e -> {
 JFileChooser fileChooser = new JFileChooser();
 int result = fileChooser.showOpenDialog(this);
 if (result == JFileChooser.APPROVE_OPTION) {
 selectedImage = fileChooser.getSelectedFile();
 imageLabel.setText(selectedImage.getName());
 }
 });
 addButton.addActionListener(e -> {
 String name = nameField.getText();
 String price = priceField.getText();
 if (!name.isEmpty() && !price.isEmpty() && selectedImage != null) {
 Product product = new Product(name, price, 
selectedImage.getAbsolutePath());
 products.add(product);
 productListModel.addElement(name + " - " + price);
 JOptionPane.showMessageDialog(this, "Product added successfully!"); dispose();
 } else {
 JOptionPane.showMessageDialog(this, "Please fill in all fields.");
 }
 });
 cancelButton.addActionListener(e -> dispose());
 setLayout(new GridLayout(5, 2));
 add(new JLabel("Product Name:"));
 add(nameField);
 add(new JLabel("Product Price:"));
 add(priceField);
add(chooseImageButton);
 add(imageLabel);
 add(addButton);
 add(cancelButton);
 }
 }
 static class ProductCatalog extends JFrame {
 private DefaultListModel<String> productListModel;
 private JList<String> productList;
 private JTextField searchField;
 private ArrayList<Product> cart;
 public ProductCatalog(String username, ArrayList<Product> products) { setTitle("Product Catalog - Welcome, " + username);
 setSize(700, 500);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLocationRelativeTo(null);
 this.cart = new ArrayList<>();
 JPanel panel = new JPanel(new BorderLayout());
 productListModel = new DefaultListModel<>();
 for (Product product : products) {
 productListModel.addElement(product.getName() + " - " + 
product.getPrice());
 }
 productList = new JList<>(productListModel);
 JScrollPane scrollPane = new JScrollPane(productList);
 JPanel topPanel = new JPanel(new FlowLayout());
 searchField = new JTextField(20);
 JButton searchButton = new JButton("Search");
topPanel.add(new JLabel("Search:"));
 topPanel.add(searchField);
 topPanel.add(searchButton);
 JPanel buttonPanel = new JPanel(new FlowLayout());
 JButton addToCartButton = new JButton("Add to Cart");
 JButton checkoutButton = new JButton("Checkout");
 JButton backButton = new JButton("Back");
 buttonPanel.add(addToCartButton);
 buttonPanel.add(checkoutButton);
 buttonPanel.add(backButton);
 panel.add(topPanel, BorderLayout.NORTH);
 panel.add(scrollPane, BorderLayout.CENTER);
 panel.add(buttonPanel, BorderLayout.SOUTH);
 add(panel);
 searchButton.addActionListener(e -> {
 String query = searchField.getText().toLowerCase();
 productListModel.clear();
 for (Product product : products) {
 if (product.getName().toLowerCase().contains(query)) {
 productListModel.addElement(product.getName() + " - " + product.getPrice());
 }
 }
 });
 addToCartButton.addActionListener(e -> {
 int selectedIndex = productList.getSelectedIndex();
 if (selectedIndex != -1) {
 Product selectedProduct = products.get(selectedIndex);
cart.add(selectedProduct);
 JOptionPane.showMessageDialog(this, selectedProduct.getName() + " added to cart!");
 } else {
 JOptionPane.showMessageDialog(this, "Please select a product to add to cart.");
 }
 });
 checkoutButton.addActionListener(e -> {
 if (cart.isEmpty()) {
 JOptionPane.showMessageDialog(this, "Your cart is empty!");
 return;
 }
 new PaymentDetailForm(this).setVisible(true);
 cart.clear();
 });
 backButton.addActionListener(e -> {
 new LoginFrame(products).setVisible(true);
 dispose();
 });
 }
 }
 static class PaymentDetailForm extends JDialog {
 public PaymentDetailForm(Frame parent) {
 super(parent, "Payment Details", true);
 setSize(400, 300);
 setLocationRelativeTo(parent);
 JPanel panel = new JPanel(new GridLayout(4, 2));
panel.add(new JLabel("Card Number:"));
 JTextField cardField = new JTextField();
 panel.add(cardField);
 panel.add(new JLabel("Expiration Date:"));
 JTextField expirationField = new JTextField();
 panel.add(expirationField);
 panel.add(new JLabel("CVV:"));
 JTextField cvvField = new JTextField();
 panel.add(cvvField);
 JButton submitButton = new JButton("Submit");
 JButton cancelButton = new JButton("Cancel");
 panel.add(submitButton);
 panel.add(cancelButton);
 add(panel);
 submitButton.addActionListener(e -> {
 JOptionPane.showMessageDialog(this, "Payment Successful!"); dispose();
 });
 cancelButton.addActionListener(e -> dispose());
 }
 }
}
