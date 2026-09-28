public class Forecast {
    private Product product;
    private double[] salesData;

    public Forecast() {
        product = null;
        salesData = new double[0];
    }

    public Forecast(Product product, double[] salesData) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null.");
        if (salesData == null) throw new IllegalArgumentException("Sales data cannot be null.");
        this.product = product;
        this.salesData = salesData;
    }

    public double calculateAverageSales() {
        if (salesData.length == 0) return 0.0;
        double total = 0.0;
        for (int i = 0; i < salesData.length; i++) total += salesData[i];
        return total / salesData.length;
    }

    public double calculateForecast() { return calculateAverageSales() * 1.1; }

    public void displayForecast() {
        System.out.println("\n========================================");
        System.out.println("       DEMAND FORECAST REPORT");
        System.out.println("========================================");
        if (product == null) { System.out.println(" No product assigned."); return; }
        System.out.println(" Product    : " + product.getProductName());
        System.out.println(" Product ID : " + product.getProductId());
        System.out.println("----------------------------------------");
        if (salesData.length == 0) { System.out.println(" No sales data available."); return; }
        for (int i = 0; i < salesData.length; i++) System.out.printf(" Period %d : %.2f units%n", i + 1, salesData[i]);
        System.out.println("----------------------------------------");
        System.out.printf(" Average Sales      : %.2f units%n", calculateAverageSales());
        System.out.printf(" Predicted Forecast : %.2f units%n", calculateForecast());
        System.out.println("========================================");
    }

    public Product getProduct() { return product; }
    public double[] getSalesData() { return salesData; }
}
