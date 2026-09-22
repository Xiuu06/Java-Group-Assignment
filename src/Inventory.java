public class Inventory {

    private Product[] products;
    private int productCount;
    private int lowStockThreshold;

    // Default constructor
    public Inventory() {
        products = new Product[50];
        productCount = 0;
        lowStockThreshold = 5;
    }

    // Add a product
public void addProduct(Product product) {

    if (product == null) {
        System.out.println("Product cannot be null.");
        return;
    }

    if (productCount >= products.length) {
        System.out.println("Inventory is full.");
        return;
    }

    if (searchProduct(product.getProductId()) != null) {
        System.out.println("Product ID already exists.");
        return;
    }

    products[productCount] = product;
    productCount++;

    System.out.println("Product added successfully.");
}

   // Update an existing product
public boolean updateProduct(int productId, Product updatedProduct) {

    if (updatedProduct == null) {
        System.out.println("Updated product cannot be null.");
        return false;
    }

    for (int i = 0; i < productCount; i++) {

        if (products[i].getProductId() == productId) {

            // Prevent changing to an existing product ID
            if (updatedProduct.getProductId() != productId
                    && searchProduct(updatedProduct.getProductId()) != null) {

                System.out.println("New Product ID already exists.");
                return false;
            }

            products[i] = updatedProduct;

            System.out.println("Product updated successfully.");
            return true;
        }
    }

    System.out.println("Product not found.");
    return false;
}

   // Remove a product
public boolean removeProduct(int productId) {

    for (int i = 0; i < productCount; i++) {

        if (products[i].getProductId() == productId) {

            // Shift products to the left
            for (int j = i; j < productCount - 1; j++) {
                products[j] = products[j + 1];
            }

            // Clear the last used position
            products[productCount - 1] = null;

            productCount--;

            System.out.println("Product removed successfully.");
            return true;
        }
    }

     System.out.println("Product not found.");
     return false;
}

    // Search for a product by ID
    public Product searchProduct(int productId) {

        for (int i = 0; i < productCount; i++) {

            if (products[i].getProductId() == productId) {
                return products[i];
            }
        }

        return null;
    }

    // Display all products
    public void displayProducts() {

        if (productCount == 0) {
            System.out.println("No products in inventory.");
            return;
        }

        System.out.println("\n========== INVENTORY ==========");

        for (int i = 0; i < productCount; i++) {

            System.out.println("\nProduct " + (i + 1));
            products[i].displayProduct();
        }
    }

    // Check low-stock products
    public void checkLowStock() {

        boolean foundLowStock = false;

        System.out.println("\n========== LOW STOCK ALERT ==========");

        for (int i = 0; i < productCount; i++) {

            if (products[i].isLowStock(lowStockThreshold)) {

                System.out.println(
                    products[i].getProductName()
                    + " - Stock: "
                    + products[i].getStockQuantity()
                );

                foundLowStock = true;
            }
        }

        if (!foundLowStock) {
            System.out.println("No products are currently low in stock.");
        }
    }
}