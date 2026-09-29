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

        Order order = null;

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
                    order = createNewOrder(customer);
                    break;
                case 8:
                    if (order == null) {
                        System.out.println("Please create an order first.");
                        break;
                    }
                    createOrder(order, inventory);
                    break;
                case 9:
                    if (order == null) {
                        System.out.println("Please create an order first.");
                        break;
                    }
                    removeItemFromOrder(order);
                    break;
                case 10:
                    if (order == null) {
                        System.out.println("Please create an order first.");
                        break;
                    }

                    order.displayOrder();
                    break;
                case 11:
                    if (order == null) {
                        System.out.println("Please create an order first.");
                        break;
                    }
                    updateOrderStatus(order);
                    break;
                case 12:
                    showForecast(inventory);
                    break;
                case 13:
                    displayUsers(users);
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-13.");
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
        System.out.println("7. Create New Order");
        System.out.println("8. Add Item to Order");
        System.out.println("9. Remove Item from Order");
        System.out.println("10. Display Order");
        System.out.println("11. Update Order Status");
        System.out.println("12. Forecast Product Demand");
        System.out.println("13. Display Users / Polymorphism");
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

    private static Order createNewOrder(Customer customer) {
    int orderId = readInt("Enter Order ID: ");

    String orderDate = readString("Enter Order Date (DD/MM/YYYY): ");

    Order newOrder = new Order(orderId, customer);
    newOrder.setOrderDate(orderDate);

    System.out.println("New order created successfully.");
    System.out.println("Order ID: " + orderId);
    System.out.println("Order Date: " + orderDate);

    return newOrder;
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
        inventory.checkLowStock();
        System.out.println("Item added to order successfully.");
        System.out.printf("Current order total: RM %.2f%n", order.calculateTotal());
    }

    private static void removeItemFromOrder(Order order) {
        int productId = readInt("Enter Product ID to remove from order: ");

        boolean removed = order.removeItem(productId);

        if (removed) {
            System.out.println("Item removed from order successfully.");
            System.out.println("Stock has been restored.");
            System.out.printf("Current order total: RM %.2f%n", order.calculateTotal());
        } else {
            System.out.println("Product not found in the order.");
        }
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

       double[] salesData;

        if (productId == 101) {
            salesData = new double[]{10, 12, 15, 14, 16, 18};
        } else if (productId == 102) {
            salesData = new double[]{20, 22, 19, 24, 25, 27};
        } else if (productId == 103) {
            salesData = new double[]{8, 10, 9, 11, 13, 12};
        } else {
            salesData = new double[]{5, 6, 7, 6, 8, 9};
        }
        
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
