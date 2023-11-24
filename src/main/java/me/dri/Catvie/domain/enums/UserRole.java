package me.dri.Catvie.domain.enums;

public enum UserRole {

    ADMIN("admin"),
    USER("user"),

    EMPTY(" ");


    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
