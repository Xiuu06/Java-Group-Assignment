public class Main {

    public static void main(String[] args) {

        User[] users = new User[3];

        users[0] = new Staff(101, "John", "john@gmail.com");
        users[1] = new Admin(201, "Alice", "alice@gmail.com");
        users[2] = new Customer(301, "David", "david@gmail.com");

        for (int i = 0; i < users.length; i++) {
            System.out.println("User " + (i + 1) + " Role: " + users[i].getRole());
        }
    }
}