public class Product {

    // Private attributes - Encapsulation
    private int productId;
    private String productName;
    private double price;
    private int stockQuantity;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(int productId, String productName, double price, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getter for productId
    public int getProductId() {
        return productId;
    }

    // Setter for productId
    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter for productName
    public String getProductName() {
        return productName;
    }

    // Setter for productName
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Price cannot be negative.");
        }
    }

    // Getter for stockQuantity
    public int getStockQuantity() {
        return stockQuantity;
    }

    // Setter for stockQuantity
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        } else {
            System.out.println("Stock quantity cannot be negative.");
        }
    }

    // Update stock quantity
    public void updateStock(int quantity) {
        if (stockQuantity + quantity >= 0) {
            stockQuantity += quantity;
        } else {
            System.out.println("Insufficient stock.");
        }
    }

    // Check whether product has low stock
    public boolean isLowStock(int threshold) {
        return stockQuantity <= threshold;
    }

    // Display product information
    public void displayProduct() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: RM" + price);
        System.out.println("Stock Quantity: " + stockQuantity);
    }
}