public class OrderItem {
    //Private fields (Encapsulation)
    private Product product;   // has-a relationship with Product
    private int quantity;

    //Default constructor
    public OrderItem() {
        this.product = null;
        this.quantity = 0;
    }

    //Parameterized constructor
    public OrderItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    //Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    //Setters (with validation)
    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        this.product = product;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        this.quantity = quantity;
    }

    //Business methods
    //Calculates subtotal = product price * quantity
    public double calculateSubtotal() {
        if (product == null) {
            return 0.0;
        }
        return product.getPrice() * quantity;
    }

    //Displays the item details to the console
    public void displayItem() {
        if (product == null) {
            System.out.println(" (Empty order item)");
            return;
        }
        System.out.printf(" %-20s x %-3d = RM %8.2f%n",
                product.getProductName(), quantity, calculateSubtotal());
    }

    //Utility
    @Override
    public String toString() {
        if (product == null) return "(empty item)";
        return String.format("%-20s x %-3d = RM %8.2f",
                product.getProductName(), quantity, calculateSubtotal());
    }
}