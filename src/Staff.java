public class Staff extends User {

    public Staff(int userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public String getRole() {
        return "Staff";
    }

    public void displayStaff() {
        System.out.println("Staff ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Role: " + getRole());
    }
}