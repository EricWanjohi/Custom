package ke.co.droidsense.custom.models;

public class User {
    //Member Variable.
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    private String fullName;

    //Empty constructor.
    public User() {
    }

    //Constructor.
    public User(String email, String phone, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    //Getters && Setters.
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
