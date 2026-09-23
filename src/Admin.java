public class Admin extends Staff {

    public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void displayAdmin() {
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Role: " + getRole());
    }
}