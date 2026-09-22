public class Main {

    public static void main(String[] args) {

        Staff staff = new Staff(101, "John", "john@gmail.com");
        Admin admin = new Admin(201, "Alice", "alice@gmail.com");
        Customer customer = new Customer(301, "David", "david@gmail.com");

        staff.displayStaff();

        System.out.println();

        admin.displayAdmin();

        System.out.println();

        customer.displayCustomer();
    }
}