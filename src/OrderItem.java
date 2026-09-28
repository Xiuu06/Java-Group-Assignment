public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem() {
        product = null;
        quantity = 0;
    }

    public OrderItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        this.product = product;
    }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than 0.");
        this.quantity = quantity;
    }
    public double calculateSubtotal() {
        if (product == null) return 0.0;
        return product.getPrice() * quantity;
    }
    public void displayItem() {
        if (product == null) { System.out.println(" (Empty order item)"); return; }
        System.out.printf(" %-20s x %-3d = RM %8.2f%n", product.getProductName(), quantity, calculateSubtotal());
    }
}
