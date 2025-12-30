package model;
public class Customer {
    private final String email;       // unique identifier
    private final String firstName;
    private final String lastName;
    public Customer(String firstName, String lastName, String email) {
        if (!email.matches("^(.+)@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ")";
    }
    //  equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) return false;
        Customer other = (Customer) obj;
        return email.equals(other.email);
    }
    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
