package com.example.storeqrscanner;

public class User {
    public String name, email;
    public UserRole userRoles;
    public User(){

    }
    public User(String name, String email, Boolean isManager, Boolean isCustomer){
        this.name = name;
        this.email = email;
        this.userRoles = new UserRole(isManager, isCustomer);

    }
}
