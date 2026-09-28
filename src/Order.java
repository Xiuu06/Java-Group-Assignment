public class Order {
    //Static counter for auto-generated order IDs
    private static int orderCounter = 1000;

    //Private fields (Encapsulation)
    private int orderID;
    private Customer customer;
    private OrderItem[] items;      // array of objects (composition)
    private int itemCount;          // current number of items
    private String status;          // PENDING / PAID / SHIPPED / CANCELLED
    private String orderDate;
    private double totalAmount;

    //Constants
    private static final int MAX_ITEMS = 20;
    private static final String[] VALID_STATUSES = {"PENDING", "PAID", "SHIPPED", "CANCELLED"};

    //Default constructor
    public Order() {
        this.orderID = ++orderCounter;
        this.customer = null;
        this.items = new OrderItem[MAX_ITEMS];
        this.itemCount = 0;
        this.status = "PENDING";
        this.orderDate = java.time.LocalDate.now().toString();
        this.totalAmount = 0.0;
    }

    //Parameterized constructor
    public Order(int orderID, Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.orderID = orderID;
        this.customer = customer;
        this.items = new OrderItem[MAX_ITEMS];
        this.itemCount = 0;
        this.status = "PENDING";
        this.orderDate = java.time.LocalDate.now().toString();
        this.totalAmount = 0.0;
    }

    //Core Operations

    //Adds an OrderItem to the order.
    //UML: addItem(OrderItem) : void
    public void addItem(OrderItem item) {
        if (item == null) {
            System.out.println(">> Error: OrderItem cannot be null.");
            return;
        }
        if (itemCount >= items.length) {
            System.out.println(">> Order is full. Cannot add more items.");
            return;
        }
        items[itemCount] = item;
        itemCount++;
        calculateTotal();
    }

    //Removes an item by product ID.
    //UML: removeItem(int) : boolean
    public boolean removeItem(int productId) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getProduct().getProductId() == productId) {
                // shift remaining elements left
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

    //Recalculates the total by summing all item subtotals.
    //UML: calculateTotal() : double
    public double calculateTotal() {
        double total = 0.0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].calculateSubtotal();
        }
        this.totalAmount = total;
        return total;
    }
    //Updates order status with validation
    //UML: updateStatus(String) : void
    public void updateStatus(String newStatus) {
        if (newStatus == null) {
            System.out.println(">> Invalid status: null");
            return;
        }
        String upper = newStatus.toUpperCase();
        for (String s : VALID_STATUSES) {
            if (s.equals(upper)) {
                if (this.status.equals("SHIPPED") && !upper.equals("SHIPPED")) {
                    System.out.println(">> Cannot change status after SHIPPED.");
                    return;
                }
                this.status = upper;
                System.out.println(">> Status updated to " + upper);
                return;
            }
        }
        System.out.println(">> Invalid status: " + newStatus);
    }
    //Displays full order details
    //UML: displayOrder() : void
    public void displayOrder() {
        System.out.println("\n========================================");
        System.out.println(" Order ID   : " + orderID);
        System.out.println(" Customer   : " + (customer != null ? customer.getName() : "N/A"));
        System.out.println(" Status     : " + status);
        System.out.println(" Order Date : " + orderDate);
        System.out.println("----------------------------------------");
        if (itemCount == 0) {
            System.out.println(" (No items in this order)");
        } else {
            for (int i = 0; i < itemCount; i++) {
                System.out.print(" " + (i + 1) + ". ");
                items[i].displayItem();
            }
        }
        System.out.println("----------------------------------------");
        System.out.printf (" TOTAL      : RM %.2f%n", totalAmount);
        System.out.println("========================================");
    }

    //Getters
    public int getOrderID()          { return orderID; }
    public Customer getCustomer()    { return customer; }
    public OrderItem[] getItems()    { return items; }
    public int getItemCount()        { return itemCount; }
    public String getStatus()        { return status; }
    public String getOrderDate()     { return orderDate; }
    public double getTotalAmount()   { return totalAmount; }

    //Setters
    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.customer = customer;
    }
}