package server.earlycart.model;

public class User {
    String id;
    String pw;
    String name;
    String birth;
    String phone;
    String role;
    String session;

    public User() {
    }

    public User(String id, String pw, String name, String birth, String phone, String role, String session) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.role = role;
        this.session = session;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
