package net.smallacademy.authenticatorapp;

public class Profile {
    private String level;
    private String userId;

    public Profile() {
    }

    public Profile(String level, String no) {
        this.level = level;
        this.userId = no;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getuserId() {
        return userId;
    }

    public void setUserId(String no) {
        this.userId = no;
    }
}
