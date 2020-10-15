package com.example.sisonkebankapp;

public class UserDetails {
    //These are the attributes of the UserDetails class, objects can be created within other classes to obtain these attributes with the use of getters and setters
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private int currentBalance;
    private int savingsBalance;

    //Create a constructor that stores all the information of the user
    public UserDetails(int id, String firstName, String lastName, String email, String password, String phoneNumber, String gender, int currentBalance, int savingsBalance) {
        this.id = id;
        this.firstName = "" + firstName;
        this.lastName = "" + lastName;
        this.email = "" + email;
        this.password = "" + password;
        this.phoneNumber = "" + phoneNumber;
        this.gender = "" + gender;
        this.currentBalance = currentBalance;
        this.savingsBalance = savingsBalance;
    }

    //Create an open constructor
    public UserDetails() {

    }

    //Create all getter and setter methods for the attributes of the userDetails class
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsBalance(int savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    //Create a toString method for this class so that all the information stores within it can be displayed
    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", currentBalance='" + currentBalance + '\'' +
                ", savingsBalance='" + savingsBalance + '\'' +
                '}';
    }
}
