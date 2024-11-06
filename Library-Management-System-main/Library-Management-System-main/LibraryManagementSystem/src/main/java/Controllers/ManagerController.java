package Controllers;

import Models.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import static Controllers.BookController.isSameDay;
import static Controllers.LibrarianController.*;

public class ManagerController implements Modifiable {

    @Override
    public StandardViewResponse<User> editUser(String name, String surname, String username,
            String salary, String phoneNum, int id, Gender gender, int accessLevel, LocalDate localDate,
            String password) {
        double salaryDouble;
        Manager manager = findUserById(id);
        try {

            if (name.isEmpty() || surname.isEmpty() || username.isEmpty()
                    || salary.isEmpty()) {
                return new StandardViewResponse<>(manager, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(manager, "Name can't have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(manager, "Name can't contain numbers!");
            }
            for (char ch : name.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(manager, "Name can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(name.charAt(0))) {
                // Convert the first letter to uppercase
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            }

            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(manager, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(manager, "Surname can't contain numbers!");
            }
            for (char ch : surname.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(manager, "Surname can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(surname.charAt(0))) {
                // Convert the first letter to uppercase
                surname = Character.toUpperCase(surname.charAt(0)) + surname.substring(1);
            }

            if (!phoneNum.matches("^\\+355 6[0-9] [0-9]{3} [0-9]{4}$")) {
                return new StandardViewResponse<>(manager,
                        "Phone number must be of specified format +355 6X XXX XXXX!");
            } else if (!isUniqueUsername(username) && !findUserById(id).getUsername().equals(username)) {
                return new StandardViewResponse<>(manager, "There already exists a user with this username");
            }
            if (!isValidPassword(password).isEmpty()) {
                return new StandardViewResponse<>(manager, isValidPassword(password));
            }
            LocalDate localDateCompare = LocalDate.now();
            if (localDate.isAfter(localDateCompare)) {
                return new StandardViewResponse<>(manager, "BirthDate cannot be after actual date!");
            } else if (localDate.isBefore(localDate.minusYears(100))) {
                return new StandardViewResponse<>(manager, "You cannot be this old!");
            }
            salaryDouble = Double.parseDouble(salary);

            manager.setName(name);
            manager.setSurname(surname);
            manager.setUsername(username);
            manager.setPhoneNum(phoneNum);
            manager.setSalary(salaryDouble);
            manager.setBirthDate(localDate);
            manager.setGender(gender);
            manager.setAccessLevel(accessLevel);
            manager.setPassword(password);

            System.out.println("Manager was successfully edited");
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return new StandardViewResponse<>(manager, "Cannot parse to double!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(manager, e.getMessage());
        }
        return new StandardViewResponse<>(manager, "");
    }

    @Override
    public StandardViewResponse<User> addUser(String name, String surname, String username,
            String password, String salary, String phoneNum, LocalDate localDate,
            Gender gender, int accessLevel, String checkPassword) {
        double salaryDouble;
        User manager = null;
        try {

            if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()
                    || salary.isEmpty()) {
                return new StandardViewResponse<>(null, "Fields are empty!");
            }

            if (name.length() < 3 || name.length() > 20) {
                return new StandardViewResponse<>(null, "Name cannot have this length!");
            } else if (name.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(null, "Name can't contain numbers!");
            }
            for (char ch : name.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(null, "Name can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(name.charAt(0))) {
                // Convert the first letter to uppercase
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            }

            if (surname.length() < 3 || surname.length() > 20) {
                return new StandardViewResponse<>(null, "Surname cannot have this length!");
            } else if (surname.matches(".*\\d+.*")) {
                return new StandardViewResponse<>(null, "Surname can't contain numbers!");
            }
            for (char ch : surname.toCharArray()) {
                if (isSpecialChar(ch)) {
                    return new StandardViewResponse<>(null, "Surname can't contain special characters!");
                }
            }
            if (!Character.isUpperCase(surname.charAt(0))) {
                // Convert the first letter to uppercase
                surname = Character.toUpperCase(surname.charAt(0)) + surname.substring(1);
            }

            if (!isValidPassword(password).isEmpty()) {
                return new StandardViewResponse<>(manager, isValidPassword(password));
            }
            if (!phoneNum.matches("^\\+355 6[0-9] [0-9]{3} [0-9]{4}$")) {
                return new StandardViewResponse<>(manager,
                        "Phone number must be of specified format +355 6X XXX XXXX!");
            } else if (!isUniqueUsername(username)) {
                return new StandardViewResponse<>(manager, "There already exists a user with this username");
            }
            if (!checkPassword.equals(password)) {
                return new StandardViewResponse<>(manager, "The password must match with verify password!");
            }
            salaryDouble = Double.parseDouble(salary);
            LocalDate localDateCompare = LocalDate.now();
            if (localDate.isAfter(localDateCompare)) {
                return new StandardViewResponse<>(null, "BirthDate cannot be after actual date!");
            } else if (localDate.isBefore(localDate.minusYears(100))) {
                return new StandardViewResponse<>(null, "You cannot be this old!");
            }
            manager = new Manager(name, surname, username, Roles.Manager,
                    password, salaryDouble, phoneNum, gender, localDate, accessLevel);
            FileController.users.add(manager);
            System.out.println("Manager was successfully added");
        } catch (NumberFormatException n) {
            System.out.println(n.getMessage());
            return new StandardViewResponse<>(null, "Cannot parse to double!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new StandardViewResponse<>(null, e.getMessage());
        }
        return new StandardViewResponse<>(manager, "");
    }

    @Override
    public Manager findUserById(int id) {
        for (User manager : FileController.users) {
            if (manager instanceof Manager) {
                if (manager.getId() == id) {
                    return (Manager) manager;
                }
            }
        }
        return null;
    }

    public Manager findManagerByIndex(int index) {
        ArrayList<Manager> librarians = new ArrayList<>();
        for (User librarian : FileController.users) {
            if (librarian instanceof Manager) {
                librarians.add((Manager) librarian);
            }
        }
        return librarians.get(index);
    }

    static void addManager(Manager manager) {
        FileController.users.add(manager);
    }

    @Override
    public boolean deleteUserById(int id) {
        var exist = findUserById(id);
        if (exist == null) {
            return false;
        }
        for (User manager : FileController.users) {
            if (manager instanceof Manager) {
                if (manager.getId() == id) {
                    FileController.users.remove(manager);
                    return true;
                }
            }
        }
        return false;
    }

    public int totalNoBooksBoughtByManager(Manager manager) {
        int result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            var quantities = bill.getQuantity();
            if (bill.getType() == BillsType.Bought && bill.getSoldBy() == manager.getId()) {
                for (var quantity : quantities) {
                    result+=quantity;
                }
            }
        }
        return result;
    }

    public int totalNoBillsByManager(Manager manager) {
        int result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getType() == BillsType.Bought  && bill.getSoldBy() == manager.getId()) {
                result++;
            }
        }
        return result;
    }

    public double calculateMoneySpendThisYear(Manager manager) {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(12).toInstant());
        double result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant()) && bill.getSoldBy() == manager.getId() && bill.getType() == BillsType.Bought) {
                result += bill.getTotalPrice();
            }
        }
        return result;
    }

    public double calculateMoneySpendThisMonth(Manager manager) {
        Date beforeMonth = Date.from(ZonedDateTime.now().minusMonths(1).toInstant());
        double result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if (bill.getCreatedDate().toInstant().isAfter(beforeMonth.toInstant()) && bill.getSoldBy() == manager.getId() && bill.getType() == BillsType.Bought) {
                result += bill.getTotalPrice();
            }
        }
        return result;
    }

    public double calculateMoneySpendToday(Manager manager) {
        double result = 0;
        var bills = FileController.transactions;
        for (var bill : bills) {
            if(bill.getType() == BillsType.Bought) {
                if (isSameDay(bill.getCreatedDate(), new Date()) && bill.getSoldBy() == manager.getId()) {
                    result += bill.getTotalPrice();
                }
            }
        }
        return result;
    }

    public void changeAccessLevel(int newAccessLevel)
    {
        var users = FileController.users;
        for(User user : users)
        {
            if(user instanceof Manager)
            {
                user.setAccessLevel(newAccessLevel);
            }
        }
    }

}
