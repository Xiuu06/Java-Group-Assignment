public class Forecast {

    //Fields
    private Order[] orders;     // aggregation (references to existing orders)
    private int orderCount;
    private double growthFactor; // e.g. 1.1 = 10% growth assumption

    //Constructor
    public Forecast(Order[] orders, int orderCount, double growthFactor) {
        if (orders == null) {
            throw new IllegalArgumentException("Orders array cannot be null.");
        }
        this.orders = orders;
        this.orderCount = orderCount;
        this.growthFactor = growthFactor;
    }

    // Overloaded constructor with default growth factor
    public Forecast(Order[] orders, int orderCount) {
        this(orders, orderCount, 1.1);
    }

    //Core Analysis Methods
    public int getTotalSold(int productId) {                       
        int total = 0;
        for (int i = 0; i < orderCount; i++) {
            OrderItem[] items = orders[i].getItems();
            int count = orders[i].getItemCount();
            for (int j = 0; j < count; j++) {
                if (items[j].getProduct().getProductId() == productId) { 
                    total += items[j].getQuantity();
                }
            }
        }
        return total;
    }

    public double getAverageOrderValue() {
        if (orderCount == 0) return 0.0;
        double total = 0.0;
        for (int i = 0; i < orderCount; i++) {
            total += orders[i].getTotalAmount();
        }
        return total / orderCount;
    }

    public int getTopSellingProductId() {                           
        if (orderCount == 0) return -1;

        int topId = -1;
        int topQty = 0;

        for (int i = 0; i < orderCount; i++) {
            OrderItem[] items = orders[i].getItems();
            int count = orders[i].getItemCount();
            for (int j = 0; j < count; j++) {
                int pid = items[j].getProduct().getProductId();     
                int qty = getTotalSold(pid);
                if (qty > topQty) {
                    topQty = qty;
                    topId = pid;
                }
            }
        }
        return topId;
    }

    public int predictNextMonthDemand(int productId) {             
        if (orderCount == 0) return 0;
        int totalSold = getTotalSold(productId);
        double avgPerOrder = (double) totalSold / orderCount;
        return (int) Math.ceil(avgPerOrder * growthFactor);
    }

    public void generateReport() {
        System.out.println("\n========================================");
        System.out.println("       DEMAND FORECAST REPORT");
        System.out.println("========================================");

        if (orderCount == 0) {
            System.out.println(" No order data available.");
            System.out.println("========================================");
            return;
        }

        System.out.println(" Total Orders Analysed : " + orderCount);
        System.out.printf (" Average Order Value   : RM %.2f%n", getAverageOrderValue());
        System.out.println(" Growth Factor Applied : " + growthFactor + "x");

        int topId = getTopSellingProductId();
        System.out.println(" Top Selling Product   : P" + topId
                + " (" + getTotalSold(topId) + " units)");

        System.out.println("----------------------------------------");
        System.out.println(" Predicted Demand (Next Month):");

        // Collect unique product IDs from all orders
        int[] seenIds = new int[100];
        int seenCount = 0;

        for (int i = 0; i < orderCount; i++) {
            OrderItem[] items = orders[i].getItems();
            int count = orders[i].getItemCount();
            for (int j = 0; j < count; j++) {
                int pid = items[j].getProduct().getProductId();     
                boolean alreadySeen = false;
                for (int k = 0; k < seenCount; k++) {
                    if (seenIds[k] == pid) { alreadySeen = true; break; } 
                }
                if (!alreadySeen) {
                    seenIds[seenCount++] = pid;
                    System.out.printf("  P%-9d -> %d units%n",
                            pid, predictNextMonthDemand(pid));
                }
            }
        }
        System.out.println("========================================");
    }

    //Getters / Setters
    public double getGrowthFactor() { return growthFactor; }
    public void setGrowthFactor(double growthFactor) {
        if (growthFactor <= 0) {
            throw new IllegalArgumentException("Growth factor must be positive.");
        }
        this.growthFactor = growthFactor;
    }
}