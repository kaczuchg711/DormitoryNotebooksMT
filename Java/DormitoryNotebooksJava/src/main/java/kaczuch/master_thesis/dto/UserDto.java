package kaczuch.master_thesis.dto;

public class UserDto {

    private String email;
    private String password;
    private String role;
    private String first_name;
    private String last_name;

    public UserDto(String email, String password, String role, String first_name, String last_name) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
