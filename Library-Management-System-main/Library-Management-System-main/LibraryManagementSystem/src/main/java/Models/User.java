package Models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2489625482462756647L;
    private static int noUsers;

    private int id;
    private String name;
    private String surname;
    private String username;
    private Roles userRole;
    private String password;
    private double salary;
    private String phoneNum;
    private LocalDate birthDate;
    private int accessLevel;
    private Gender gender;

    public User() {

    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public User(String username, String password, Roles role) {
        this.username = username;
        this.password = password;
        this.userRole = role;
        this.id = ++noUsers;
    }

    public User(String name, String surname, String username,
            Roles userRole, String password, double salary,
            String phoneNum, LocalDate date, Gender gender, int accessLevel) {
        this.id = ++noUsers;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.userRole = userRole;
        this.password = password;
        this.salary = salary;
        this.phoneNum = phoneNum;
        this.birthDate = date;
        this.gender = gender;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getId() {
        return this.id;
    }

    public static int getNoUsers() {
        return noUsers;
    }

    public static void setNoUsers(int noUsers) {
        User.noUsers = noUsers;
    }

    @Override
    public String toString() {
        return "Id:" + getId() +
                "Name: " + getName() +
                "\nUsername: " + getUsername() +
                "\nPassword: " + getPassword() +
                "\nRole: " + getUserRole() +
                "\nSalary: " + getSalary() +
                "\nPhone Number: " + getPhoneNum();
    }
}
