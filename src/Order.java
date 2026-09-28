public class Order {
    private static int orderCounter = 1000;
    private int orderId;
    private Customer customer;
    private OrderItem[] items;
    private int itemCount;
    private String status;
    private String orderDate;
    private double totalAmount;

    private static final int MAX_ITEMS = 20;
    private static final String[] VALID_STATUSES = {"PENDING", "PAID", "SHIPPED", "CANCELLED"};

    public Order() {
        this.orderId = ++orderCounter;
        this.customer = null;
        this.items = new OrderItem[MAX_ITEMS];
        this.itemCount = 0;
        this.status = "PENDING";
        this.orderDate = "Not specified";
        this.totalAmount = 0.0;
    }

    public Order(int orderId, Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null.");
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[MAX_ITEMS];
        this.itemCount = 0;
        this.status = "PENDING";
        this.orderDate = "Not specified";
        this.totalAmount = 0.0;
    }

    public void addItem(OrderItem item) {
        if (item == null) { System.out.println(">> Error: OrderItem cannot be null."); return; }
        if (itemCount >= items.length) { System.out.println(">> Order is full. Cannot add more items."); return; }
        items[itemCount++] = item;
        calculateTotal();
    }

    public boolean removeItem(int productId) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getProduct().getProductId() == productId) {
                for (int j = i; j < itemCount - 1; j++) items[j] = items[j + 1];
                items[--itemCount] = null;
                calculateTotal();
                System.out.println(">> Item removed successfully.");
                return true;
            }
        }
        System.out.println(">> Item not found: " + productId);
        return false;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) total += items[i].calculateSubtotal();
        totalAmount = total;
        return total;
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null) { System.out.println(">> Invalid status: null"); return; }
        String upper = newStatus.toUpperCase();
        for (String valid : VALID_STATUSES) {
            if (valid.equals(upper)) {
                if (status.equals("SHIPPED") && !upper.equals("SHIPPED")) {
                    System.out.println(">> Cannot change status after SHIPPED.");
                    return;
                }
                status = upper;
                System.out.println(">> Status updated to " + upper);
                return;
            }
        }
        System.out.println(">> Invalid status: " + newStatus);
    }

    public String getStatus() { return status; }
    public void displayOrder() {
        System.out.println("\n========================================");
        System.out.println(" Order ID   : " + orderId);
        System.out.println(" Customer   : " + (customer != null ? customer.getName() : "N/A"));
        System.out.println(" Status     : " + status);
        System.out.println(" Order Date : " + orderDate);
        System.out.println("----------------------------------------");
        if (itemCount == 0) System.out.println(" (No items in this order)");
        else for (int i = 0; i < itemCount; i++) { System.out.print(" " + (i + 1) + "."); items[i].displayItem(); }
        System.out.println("----------------------------------------");
        System.out.printf(" TOTAL      : RM %.2f%n", totalAmount);
        System.out.println("========================================");
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public OrderItem[] getItems() { return items; }
    public int getItemCount() { return itemCount; }
    public String getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
}
