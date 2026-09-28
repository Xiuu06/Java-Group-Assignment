import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        // Sample products for demonstration and testing
        inventory.addProduct(new Product(101, "Wireless Mouse", 45.90, 3));
        inventory.addProduct(new Product(102, "Keyboard", 89.90, 12));
        inventory.addProduct(new Product(103, "USB Cable", 19.90, 6));

        // Demonstrate inheritance and polymorphism
        User[] users = new User[3];
        users[0] = new Staff(101, "John", "john@gmail.com");
        users[1] = new Admin(201, "Alice", "alice@gmail.com");
        Customer customer = new Customer(301, "David", "david@gmail.com");
        users[2] = customer;

        Order order = new Order(1001, customer);

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    inventory.displayProducts();
                    break;
                case 2:
                    addProduct(inventory);
                    break;
                case 3:
                    searchProduct(inventory);
                    break;
                case 4:
                    updateProduct(inventory);
                    break;
                case 5:
                    removeProduct(inventory);
                    break;
                case 6:
                    inventory.checkLowStock();
                    break;
                case 7:
                    createOrder(order, inventory);
                    break;
                case 8:
                    order.displayOrder();
                    break;
                case 9:
                    updateOrderStatus(order);
                    break;
                case 10:
                    showForecast(inventory);
                    break;
                case 11:
                    displayUsers(users);
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-11.");
            }
        }

        input.close();
    }

    private static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println(" E-COMMERCE ORDER FULFILMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Display Products");
        System.out.println("2. Add Product");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. Remove Product");
        System.out.println("6. Check Low Stock");
        System.out.println("7. Add Item to Order");
        System.out.println("8. Display Order");
        System.out.println("9. Update Order Status");
        System.out.println("10. Forecast Product Demand");
        System.out.println("11. Display Users / Polymorphism");
        System.out.println("0. Exit");
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            if (input.hasNextInt()) {
                int value = input.nextInt();
                input.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a whole number.");
            input.nextLine();
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            if (input.hasNextDouble()) {
                double value = input.nextDouble();
                input.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a number.");
            input.nextLine();
        }
    }

    private static String readString(String message) {
        System.out.print(message);
        return input.nextLine();
    }

    private static void addProduct(Inventory inventory) {
        System.out.println("\n----- ADD PRODUCT -----");
        int id = readInt("Product ID: ");
        String name = readString("Product Name: ");
        double price = readDouble("Price: RM");
        int stock = readInt("Stock Quantity: ");

        if (price < 0 || stock < 0) {
            System.out.println("Price and stock cannot be negative.");
            return;
        }

        inventory.addProduct(new Product(id, name, price, stock));
    }

    private static void searchProduct(Inventory inventory) {
        int id = readInt("Enter Product ID to search: ");
        Product product = inventory.searchProduct(id);
        if (product == null) System.out.println("Product not found.");
        else product.displayProduct();
    }

    private static void updateProduct(Inventory inventory) {
        int id = readInt("Enter Product ID to update: ");
        Product existing = inventory.searchProduct(id);
        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        String name = readString("New Product Name: ");
        double price = readDouble("New Price: RM");
        int stock = readInt("New Stock Quantity: ");
        if (price < 0 || stock < 0) {
            System.out.println("Price and stock cannot be negative.");
            return;
        }

        Product updated = new Product(id, name, price, stock);
        inventory.updateProduct(id, updated);
    }

    private static void removeProduct(Inventory inventory) {
        int id = readInt("Enter Product ID to remove: ");
        inventory.removeProduct(id);
    }

    private static void createOrder(Order order, Inventory inventory) {
        int productId = readInt("Enter Product ID to add to order: ");
        Product product = inventory.searchProduct(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }
        if (quantity > product.getStockQuantity()) {
            System.out.println("Not enough stock available.");
            return;
        }

        OrderItem item = new OrderItem(product, quantity);
        order.addItem(item);
        product.updateStock(-quantity);
        System.out.println("Item added to order successfully.");
        System.out.printf("Current order total: RM %.2f%n", order.calculateTotal());
    }

    private static void updateOrderStatus(Order order) {
        String status = readString("Enter status (PENDING/PAID/SHIPPED/CANCELLED): ");
        order.updateStatus(status);
    }

    private static void showForecast(Inventory inventory) {
        int productId = readInt("Enter Product ID for forecast: ");
        Product product = inventory.searchProduct(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        double[] salesData = {10, 12, 15, 14, 16, 18};
        Forecast forecast = new Forecast(product, salesData);
        forecast.displayForecast();
    }

    private static void displayUsers(User[] users) {
        System.out.println("\n========== USERS / POLYMORPHISM ==========");
        for (int i = 0; i < users.length; i++) {
            System.out.println("User " + (i + 1) + " Role: " + users[i].getRole());
        }
    }
}
