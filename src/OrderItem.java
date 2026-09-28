public class OrderItem {
    //Private fields (Encapsulation)
    private Product product;   // has-a relationship with Product
    private int quantity;
    private double subtotal;   // computed = product price * quantity

    //Constructor
    public OrderItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    //Getters
    public Product getProduct() { return product; }
    public int getQuantity()    { return quantity; }
    public double getSubtotal() { return subtotal; }

    //Setters (with validation)
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    //Utility
    @Override
    public String toString() {
        return String.format("%-20s x %-3d = RM %8.2f",
                product.getProductName(), quantity, subtotal);   
    }
}