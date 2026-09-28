public class Order {
    //Static counter for auto-generated order IDs
    private static int orderCounter = 1000;

    //Private fields (Encapsulation)
    private String orderId;
    private Customer customer;       // has-a Customer
    private OrderItem[] items;       // array of objects (composition)
    private int itemCount;           // current number of items stored
    private String status;           // PENDING / PAID / SHIPPED / CANCELLED
    private double totalAmount;

    //Valid statuses
    private static final String[] VALID_STATUSES = {"PENDING", "PAID", "SHIPPED", "CANCELLED"};

    //Constructor
    public Order(Customer customer, int maxItems) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (maxItems <= 0) {
            throw new IllegalArgumentException("Max items must be greater than 0.");
        }
        this.orderId = "ORD" + (++orderCounter);
        this.customer = customer;
        this.items = new OrderItem[maxItems];
        this.itemCount = 0;
        this.status = "PENDING";
        this.totalAmount = 0.0;
    }

    //Core Operations
    public boolean addItem(Product product, int quantity) {
        if (itemCount >= items.length) {
            System.out.println(">> Order is full. Cannot add more items.");
            return false;
        }
        try {
            items[itemCount] = new OrderItem(product, quantity);
            itemCount++;
            calculateTotal();
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(">> Error: " + e.getMessage());
            return false;
        }
    }

    public boolean removeItem(int productId) {                       
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getProduct().getProductId() == productId) { 
                // shift elements left
                for (int j = i; j < itemCount - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[itemCount - 1] = null;
                itemCount--;
                calculateTotal();
                System.out.println(">> Item removed successfully.");
                return true;
            }
        }
        System.out.println(">> Item not found: " + productId);
        return false;
    }

    public OrderItem findItem(int productId) {                       
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getProduct().getProductId() == productId) { 
                return items[i];
            }
        }
        return null;
    }

    public void calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getSubtotal();
        }
        this.totalAmount = total;
    }

    public boolean updateStatus(String newStatus) {
        String upper = newStatus.toUpperCase();
        for (String s : VALID_STATUSES) {
            if (s.equals(upper)) {
                if (this.status.equals("SHIPPED") && !upper.equals("SHIPPED")) {
                    System.out.println(">> Cannot change status after SHIPPED.");
                    return false;
                }
                this.status = upper;
                System.out.println(">> Status updated to " + upper);
                return true;
            }
        }
        System.out.println(">> Invalid status: " + newStatus);
        return false;
    }

    public void displayOrder() {
        System.out.println("\n========================================");
        System.out.println(" Order ID : " + orderId);
        System.out.println(" Customer : " + customer.getName());
        System.out.println(" Status   : " + status);
        System.out.println("----------------------------------------");
        if (itemCount == 0) {
            System.out.println(" (No items in this order)");
        } else {
            for (int i = 0; i < itemCount; i++) {
                System.out.println(" " + (i + 1) + ". " + items[i]);
            }
        }
        System.out.println("----------------------------------------");
        System.out.printf(" TOTAL    : RM %.2f%n", totalAmount);
        System.out.println("========================================");
    }

    //Getters
    public String getOrderId()      { return orderId; }
    public Customer getCustomer()   { return customer; }
    public OrderItem[] getItems()   { return items; }
    public int getItemCount()       { return itemCount; }
    public String getStatus()       { return status; }
    public double getTotalAmount()  { return totalAmount; }
}