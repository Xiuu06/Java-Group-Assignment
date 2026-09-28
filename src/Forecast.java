public class Forecast {
    //Private fields
    private Product product;        // has-a Product
    private double[] salesData;     // e.g. past 6 months of sales

    //Default constructor
    public Forecast() {
        this.product = null;
        this.salesData = new double[0];
    }

    //Parameterized constructor
    public Forecast(Product product, double[] salesData) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (salesData == null) {
            throw new IllegalArgumentException("Sales data cannot be null.");
        }
        this.product = product;
        this.salesData = salesData;
    }

    //Core Methods

    //Calculates the average sales across all recorded periods.
    //UML: calculateAverageSales() : double
    public double calculateAverageSales() {
        if (salesData == null || salesData.length == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (int i = 0; i < salesData.length; i++) {
            total += salesData[i];
        }
        return total / salesData.length;
    }

    //Predicts next period's demand.
    //Simple model: average sales × 1.1 (10% growth assumption).
    //UML: calculateForecast() : double
    public double calculateForecast() {
        double average = calculateAverageSales();
        return average * 1.1;
    }

    //Displays a formatted forecast report.
    //UML: displayForecast() : void
    public void displayForecast() {
        System.out.println("\n========================================");
        System.out.println("       DEMAND FORECAST REPORT");
        System.out.println("========================================");

        if (product == null) {
            System.out.println(" No product assigned.");
            System.out.println("========================================");
            return;
        }

        System.out.println(" Product       : " + product.getProductName());
        System.out.println(" Product ID    : " + product.getProductId());
        System.out.printf (" Current Price : RM %.2f%n", product.getPrice());
        System.out.println("----------------------------------------");

        if (salesData == null || salesData.length == 0) {
            System.out.println(" No sales data available.");
            System.out.println("========================================");
            return;
        }

        System.out.println(" Historical Sales Data:");
        for (int i = 0; i < salesData.length; i++) {
            System.out.printf("   Period %d : %.2f units%n", (i + 1), salesData[i]);
        }

        System.out.println("----------------------------------------");
        System.out.printf (" Average Sales       : %.2f units%n", calculateAverageSales());
        System.out.printf (" Predicted Forecast  : %.2f units (next period)%n", calculateForecast());
        System.out.println("========================================");
    }

    //Getters
    public Product getProduct()      { return product; }
    public double[] getSalesData()   { return salesData; }

    //Setters
    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        this.product = product;
    }

    public void setSalesData(double[] salesData) {
        if (salesData == null) {
            throw new IllegalArgumentException("Sales data cannot be null.");
        }
        this.salesData = salesData;
    }
}